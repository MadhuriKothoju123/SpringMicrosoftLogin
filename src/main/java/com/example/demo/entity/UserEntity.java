package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user_entity")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;

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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public RegistrationSource getSource() {
		return source;
	}

	public void setSource(RegistrationSource source) {
		this.source = source;
	}

	public UserEntity() {
	
		
	}

	public UserEntity(Long id, String name, String email, UserRole role, RegistrationSource source) {
	
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.source = source;
	}
	
    
    
}
