package com.sunmeat.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepo studentRepository;

	// получение всех студентов или одного по ID
	@GetMapping
	public ResponseEntity<Object> getStudentOrAll(@RequestParam(name = "id", required = false) Long id) {
		if (id != null) {
			Student student = studentRepository.findById(id).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент с ID " + id + " не найден"));
			return ResponseEntity.ok(student); // возврат объекта студента в формате JSON
		}
		return ResponseEntity.ok(studentRepository.findAll()); // возврат всех студентов в формате JSON
	}

	// удаление студента по ID
	@PostMapping("/delete")
	public ResponseEntity<String> deleteStudent(@RequestParam(name = "id") Long id) {
		try {
			studentService.deleteStudent(id);
			return ResponseEntity.ok("Студент успешно удален.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.ok("Студент с ID " + id + " не найден.");
		} catch (OptimisticLockException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Конфликт версии при удалении студента с ID " + id);
		} catch (Exception e) {
			System.out.println("Ошибка: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ошибка при удалении студента: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<String> saveStudent(@RequestParam(required = false, name = "_method") String _method,
			@RequestParam(required = false, name = "id") Long id, @RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email) {
		if ("PUT".equalsIgnoreCase(_method) && id != null) { // обновление существующего студента
			return studentRepository.findById(id).map(student -> {
				student.setName(name);
				student.setEmail(email);
				studentRepository.save(student);
				return ResponseEntity.ok("Студент обновлен: " + student.getName());
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден"));
		} else { // создание нового студента
			var newStudent = new Student();
			newStudent.setName(name);
			newStudent.setEmail(email);
			studentRepository.save(newStudent);
			return ResponseEntity.status(HttpStatus.CREATED).body("Студент создан: " + newStudent.getName());
		}
	}
}