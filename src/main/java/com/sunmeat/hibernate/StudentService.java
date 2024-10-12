package com.sunmeat.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public Student findById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Студент с ID " + id + " не найден"));
	}

	@Transactional(readOnly = true)
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Transactional
	public void saveStudent(String name, String email) {
		Student newStudent = new Student();
		newStudent.setName(name);
		newStudent.setEmail(email);
		studentRepository.save(newStudent);
	}

	@Transactional
	public void updateStudent(Long id, String name, String email) {
		Student student = findById(id);
		student.setName(name);
		student.setEmail(email);
		studentRepository.save(student);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteStudent(Long id) {
		if (!studentRepository.existsById(id)) {
			throw new EntityNotFoundException("Студент с ID " + id + " не найден");
		}
		studentRepository.deleteById(id);
	}
}