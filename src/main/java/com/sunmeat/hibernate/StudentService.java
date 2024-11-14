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

    /* в Hibernate кэш первого уровня (или L1-кэш) — это встроенный механизм
     * для кэширования сущностей на уровне сессии EntityManager. Он всегда включён 
     * по умолчанию и работает на уровне текущей сессии (транзакции). смысл его работы
     * заключается в том, что при первом запросе на загрузку объекта он сохраняется
     * в L1-кэше и не запрашивается повторно из базы данных, если он уже находится в кэше.
     * 
     * когда вы запрашиваете объект с помощью метода find(), он сначала ищется в кэше
     * первого уровня. если объекта в кэше нет, Hibernate отправляет SQL-запрос в БД,
     * загружает объект и сохраняет его в L1-кэше.
     * 
     * если в рамках одной и той же сессии вы снова запрашиваете тот же объект (например,
     * студента с тем же ID), Hibernate получает его из L1-кэша, а не из базы данных.
     * это уменьшает количество SQL-запросов и ускоряет работу приложения.
     * 
     * если объект был изменен в рамках текущей сессии (например, при вызове updateStudent),
     * то при вызове метода flush() (происходит автоматически при завершении транзакции)
     * изменения будут синхронизированы с базой данных.
     * 
     * когда сессия EntityManager закрывается, кэш первого уровня очищается, и его данные
     * больше не доступны. поэтому, если тот же объект требуется в новой сессии, его
     * снова нужно будет загрузить из базы данных.
     * 
     * в классе StudentService, при вызове метода findById() или метода updateStudent(),
     * если несколько раз вызвать findById() с одним и тем же ID, то при первом вызове
     * findById() Hibernate выполнит SQL-запрос к базе и сохранит объект в L1-кэше.
     * при последующих вызовах findById() для того же ID объект будет извлечен из L1-кэша,
     * и SQL-запрос больше не выполняется.
     * */
    // поиск студента по ID
    @Transactional(readOnly = true)
    public Student findById(Long id) {
    	
    	// Student student1 = findById(id); // первый вызов: загрузка из базы и сохранение в L1-кэш
        // Student student2 = findById(id); // второй вызов: получение из L1-кэша, без SQL-запроса
        
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент с ID " + id + " не найден"));
    }

    // получение всех студентов
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // создание нового студента
    @Transactional
    public void saveStudent(String name, String email) {
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setEmail(email);
        studentRepository.save(newStudent);
    }

    // обновление существующего студента
    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = findById(id);
        student.setName(name);
        student.setEmail(email);
        studentRepository.save(student);
    }

    // удаление студента
    @Transactional(isolation = Isolation.READ_COMMITTED)
    /* READ_COMMITTED - "Прочитанные данные должны быть зафиксированы":
     * транзакция видит только те данные, которые были зафиксированы другими транзакциями.
     * это исключает "грязные" чтения (ситуацию, когда одна транзакция видит незавершенные
     * изменения другой транзакции) */
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Студент с ID " + id + " не найден");
        }
        studentRepository.deleteById(id);
    }
}
