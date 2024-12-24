package com.springboot.projects.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentEnquiry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer EnquiryId;
	private String studentName;
	private Long studentPhno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	private LocalDate createdDate;
	private LocalDate updatedDate;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetails user;

}
