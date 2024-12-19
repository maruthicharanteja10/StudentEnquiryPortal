package com.springboot.projects.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EnquiryStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer StatusID;
	private String StatusName;
}
