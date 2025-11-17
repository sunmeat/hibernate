package com.sunmeat.hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	@Version
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;

	public Student() {} // має бути обов'язково присутній! це вимога гібернейта!

	public Student(String name, String email) {
		setName(name);
		setEmail(email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
