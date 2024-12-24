package com.springboot.projects.service;

import java.util.List;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;
import com.springboot.projects.entity.StudentEnquiry;

public interface EnquiryService {
	public List<String> getCourses();

	public List<String> getEnqStatus();

	public DashboardResponse getDashboardData(Integer Userid);


	public List<StudentEnquiry> getEnquiries();

	public boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnquiry> getFilteredEnquiries(EnquirySearchCriteria criteria, Integer userId);

}
