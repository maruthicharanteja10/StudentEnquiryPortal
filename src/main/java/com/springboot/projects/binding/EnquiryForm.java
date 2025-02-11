package com.springboot.projects.binding;

import lombok.Data;

@Data
public class EnquiryForm {
	private Integer EnquiryId;
	private String studentName;
	private Long studentPhno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
}
