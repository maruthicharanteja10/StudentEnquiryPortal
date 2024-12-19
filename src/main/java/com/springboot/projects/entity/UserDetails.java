package com.springboot.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String Name;
	private String email;
	private long phoneno;
	private String password;
	private String accountStatus;

}
