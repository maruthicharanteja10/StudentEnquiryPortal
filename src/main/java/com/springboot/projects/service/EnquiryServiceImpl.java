package com.springboot.projects.service;

import java.util.List;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;

public class EnquiryServiceImpl implements EnquiryService {

	@Override
	public List<String> getCourseName() {
		return null;
	}

	@Override
	public List<String> getEnqStatus() {
		return null;
	}

	@Override
	public DashboardResponse getDashboardData(Integer Userid) {
		return null;
	}

	@Override
	public String addEnquiry(EnquiryForm form) {
		return null;
	}

	@Override
	public List<EnquiryForm> getEnquires(Integer UserId, EnquirySearchCriteria criteria) {
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		return null;
	}

}
