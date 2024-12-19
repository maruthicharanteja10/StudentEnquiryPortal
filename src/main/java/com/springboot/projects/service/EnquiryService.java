package com.springboot.projects.service;

import java.util.List;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;

public interface EnquiryService {
	public List<String> getCourseName();

	public List<String> getEnqStatus();

	public DashboardResponse getDashboardData(Integer Userid);

	public String addEnquiry(EnquiryForm form);

	public List<EnquiryForm> getEnquires(Integer UserId, EnquirySearchCriteria criteria);
}
