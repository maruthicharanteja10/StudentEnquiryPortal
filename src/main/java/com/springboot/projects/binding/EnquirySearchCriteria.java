package com.springboot.projects.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnquirySearchCriteria {
	private String studentName;
	private String courseName;
	private Long studentPhno;
	private LocalDate updatedDate;
	private String enquiryStatus;
	private String classMode;

}
