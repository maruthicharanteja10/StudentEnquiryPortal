package com.springboot.projects.binding;

import lombok.Data;

@Data
public class DashboardResponse {

	private Integer totalEnquiryCnt;
	private Integer enrolledCnt;
	private Integer lostCnt;
}
