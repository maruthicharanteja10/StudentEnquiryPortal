package com.springboot.projects.binding;

import lombok.Data;

@Data
public class SignUpForm {
	private String name;
	private String email;
	private Long phoneno;
}
