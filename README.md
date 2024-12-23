# Проект: Hibernate - Студент

🔥 **Цей проект** — потужне демонстраційне застосування фреймворку Hibernate, що дозволяє легко працювати з **об'єктно-реляційним мапінгом** в Java. За допомогою класу `Student` ми розглядаємо базову модель студента, що включає основні дані, такі як ім'я, електронна пошта та ідентифікатор. Клас також надає методи для маніпулювання цими даними, що робить його ідеальним для тестування та практичного застосування ORM технології.

## 🚀 Опис проекту

Проект містить клас `Student`, який **представляє студента** з наступними полями:

- **Ідентифікатор студента** (ID)
- **Ім'я студента**  
- **Електронна пошта студента**  

Цей клас має всі необхідні методи для:
- Додавання, змінення та отримання даних студента
- Виведення детальної інформації про студента в консоль

### ✨ Основні функції:
- **Збереження та отримання даних** про студента в базі даних.
- **Виведення інформації про студента** в консоль у зручному для користувача форматі.
- Підтримка **взаємодії з базою даних** через Hibernate, що дозволяє ефективно працювати з об'єктами та їх відповідними записами в базі.

## ⚙️ Системні вимоги

Для успішного запуску проекту переконайтеся, що у вас є наступні вимоги:

- **Java 23** або новіша версія.
- **Hibernate 6.x**.
- Підключена база даних (наприклад, **MySQL** або **PostgreSQL**).

## 🛠 Встановлення

Щоб швидко налаштувати проект, виконайте наступні кроки:

### Крок 1: Клонування репозиторію

Спочатку клонуванні репозиторій на вашу локальну машину:

```bash
git clone https://github.com/your-username/hibernate.git
```

### Крок 2: Налаштування середовища

1. Переконайтеся, що у вас встановлена **Java 23** або новіша версія.
2. Налаштуйте базу даних (наприклад, **MySQL** або **PostgreSQL**) і створіть таблицю для збереження даних студентів.
3. Налаштуйте підключення до бази даних у файлі `hibernate.cfg.xml`. Ось приклад налаштувань для MySQL:

```xml
<hibernate-configuration>
    <session-factory>
        <!-- JDBC налаштування -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/hibernate_db</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>

        <!-- Додаткові налаштування Hibernate -->
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>
    </session-factory>
</hibernate-configuration>
```

### Крок 3: Запуск

1. Використовуйте **IntelliJ IDEA** або **Eclipse** для відкриття та запуску проекту.
2. Переконайтеся, що всі залежності (наприклад, Hibernate) правильно налаштовані. Ви можете додати їх через **Maven** або **Gradle**.

## 🏆 Використання

### Клас `Student`

Клас `Student` містить наступні конструкції та методи:

#### Конструктори
- **`Student()`**  
  Конструктор без параметрів, ініціалізує всі поля значеннями за замовчуванням.

- **`Student(String name, String email)`**  
  Конструктор, що ініціалізує ім'я та електронну пошту студента.

#### Методи
- **`getId()`**  
  Отримує ідентифікатор студента.

- **`setId(Long id)`**  
  Встановлює ідентифікатор студента.

- **`getName()`**  
  Отримує ім'я студента.

- **`setName(String name)`**  
  Встановлює ім'я студента.

- **`getEmail()`**  
  Отримує електронну пошту студента.

- **`setEmail(String email)`**  
  Встановлює електронну пошту студента.

- **`toString()`**  
  Повертає строкове подання студента в форматі JSON.

- **`print()`**  
  Виводить всю інформацію про студента на екран.

## ❓ Часто задавані питання (FAQ)

1. **Як додати нового студента?**  
   Для додавання нового студента створіть екземпляр класу `Student` та використовуйте методи `setName()` та `setEmail()` для заповнення його даними:

   ```java
   Student student = new Student("Іван", "ivan@example.com");
   student.setId(1L);
   ```

2. **Як зберегти дані студента в базі даних?**  
   Для збереження студента використовуйте Hibernate. Ось приклад:

   ```java
   Session session = sessionFactory.openSession();
   session.beginTransaction();
   session.save(student);
   session.getTransaction().commit();
   session.close();
   ```

## 📝 Ліцензія
Проект розповсюджується під ліцензією MIT. Для деталей перегляньте файл LICENSE.

## 👫 Контрибуція
1. Форкніть репозиторій.
2. Створіть свою гілку (git checkout -b feature-name).
3. Зробіть зміни та комітте їх (git commit -am 'Add new feature').
4. Відправте гілку на GitHub (git push origin feature-name).
5. Створіть пулл-запит.

## 👨‍💻 Автор
**Олександр Загоруйко**  
Версія: 1.0.0.1  
Дата: 11.11.2024

## 🔗 Посилання
- [Документація Javadoc](https://sunmeat.github.io/hibernate/com/sunmeat/hibernate/package-summary.html)
- [GitHub репозиторій](https://github.com/sunmeat/hibernate)
- [Написати розробнику](https://t.me/sunmeat)
