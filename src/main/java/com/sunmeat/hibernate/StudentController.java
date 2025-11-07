package com.sunmeat.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.*;

@RestController
@RequestMapping("/students")
public class StudentController {
	// кешування Redis:
	// https://gist.github.com/sunmeat/62fa29183c71fe69ee9d35a9d2d9a218
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepo studentRepository;

	// отримання всіх студентів або одного за ID
	@GetMapping
	public ResponseEntity<Object> getStudentOrAll(@RequestParam(name = "id", required = false) Long id) {
		if (id != null) {
			Student student = studentRepository.findById(id).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студента з ID " + id + " не знайдено"));
			return ResponseEntity.ok(student); // повернення об'єкта студента у форматі JSON
		}
		return ResponseEntity.ok(studentRepository.findAll()); // повернення всіх студентів у форматі JSON
	}

	// видалення студента по ID
	@PostMapping("/delete")
	public ResponseEntity<String> deleteStudent(@RequestParam(name = "id") Long id) {
		try {
			studentService.deleteStudent(id);
			return ResponseEntity.ok("Студента успішно видалено.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.ok("Студента з ID " + id + " не знайдено.");
		} catch (OptimisticLockException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Конфлікт версії при видаленні студента з ID" + id);
		} catch (Exception e) {
			System.out.println("Помилка: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Помилка при видаленні студента: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<String> saveStudent(@RequestParam(required = false, name = "_method") String _method,
			@RequestParam(required = false, name = "id") Long id, @RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email) {
		if ("PUT".equalsIgnoreCase(_method) && id != null) { // оновлення існуючого студента
			return studentRepository.findById(id).map(student -> {
				student.setName(name);
				student.setEmail(email);
				studentRepository.save(student);
				return ResponseEntity.ok("Студента оновлено: " + student.getName());
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студента не знайдено"));
		} else { // створення нового студента
			var newStudent = new Student();
			newStudent.setName(name);
			newStudent.setEmail(email);
			studentRepository.save(newStudent);
			return ResponseEntity.status(HttpStatus.CREATED).body("Студента створено: " + newStudent.getName());
		}
	}
}
