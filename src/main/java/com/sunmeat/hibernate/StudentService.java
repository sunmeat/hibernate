package com.sunmeat.hibernate;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /* Hibernate кеш першого рівня (або L1-кеш) - це вбудований механізм 
* для кешування сутностей на рівні сесії EntityManager. він завжди включений 
* за замовчуванням та працює на рівні поточної сесії (транзакції). сенс його роботи 
* полягає в тому, що при першому запиті на завантаження об'єкта він зберігається 
* в L1-кеші і не вимагає повторного завантаження з бази даних, якщо він вже знаходиться в кеші. 
* 
* коли запитується об'єкт за допомоги методу find(), він спочатку шукається в кеші 
* першого рівня. якщо об'єкта в кеші немає, Hibernate відправляє SQL-запит до БД, 
* завантажує об'єкт і зберігає його в L1-кеші. 
* 
* якщо в рамках однієї і тієї ж сесії ви знову запитуєте той самий об'єкт (наприклад, 
* студент з тим же ID), Hibernate отримує його з L1-кешу, а не з бази даних. 
* це зменшує кількість SQL-запитів та прискорює роботу програми. 
* 
* якщо об'єкт було змінено в рамках поточної сесії (наприклад, під час виклику updateStudent), 
* то при виклику методу flush() (відбувається автоматично при завершенні транзакції) 
* зміни будуть синхронізовані з базою даних. 
* 
* коли сесія EntityManager закривається, кеш першого рівня очищається, та його дані 
* більше не доступні. тому, якщо той самий об'єкт потрібен у новій сесії, його 
* знову потрібно буде завантажити з бази даних. 
* 
* у класі StudentService, при виклику методу findById() або методу updateStudent(), 
* якщо кілька разів викликати findById() з одним і тим самим ID, то при першому виклику 
* findById() Hibernate виконає SQL-запит до бази та збереже об'єкт у L1-кеші. 
* при наступних викликах findById() для того ж ID об'єкт буде вилучений з L1-кешу, 
* та SQL-запит більше не виконується. 
* */ 
// пошук студента за ID
    @Transactional(readOnly = true)
    public Student findById(Long id) {
    	
    	// Student student1 = findById(id); // перший виклик: завантаження з бази та збереження в L1-кеш
        // Student student2 = findById(id); // другий виклик: отримання з L1-кешу, без SQL-запиту
        
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студента з ID " + id + " не знайдено"));
    }

    // отримання всіх студентів
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // створення нового студента
    @Transactional
    public void saveStudent(String name, String email) {
        var newStudent = new Student();
        newStudent.setName(name);
        newStudent.setEmail(email);
        studentRepository.save(newStudent);
    }

    // оновлення існуючого студента
    @Transactional
    public void updateStudent(Long id, String name, String email) {
        var student = findById(id);
        student.setName(name);
        student.setEmail(email);
        studentRepository.save(student);
    }

    // видалення студента
    @Transactional(isolation = Isolation.READ_COMMITTED)
   /* READ_COMMITTED - "Прочитані дані мають бути зафіксовані": 
* транзакція бачить лише дані, які були зафіксовані іншими транзакціями. 
* це виключає "брудні" читання (ситуацію, коли одна транзакція бачить незавершені 
* зміни іншої транзакції) */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Студент с ID " + id + " не найден");
        }
        studentRepository.deleteById(id);
    }
}
