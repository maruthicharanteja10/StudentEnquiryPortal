package com.springboot.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;
import com.springboot.projects.entity.StudentEnquiry;
import com.springboot.projects.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService enquiryService;

	@GetMapping("/dashboard")
	public String dashboardpage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse data = enquiryService.getDashboardData(userId);
		System.out.println(data);
		model.addAttribute("dashboarddata", data);
		return "dashboard";
	}

	@GetMapping("/addenquiry")
	public String addEnquiryPage(Model model) {
		initForm(model);
		return "addEnquiry";
	}

	private void initForm(Model model) {
		List<String> courses = enquiryService.getCourses();
		List<String> enqstatus = enquiryService.getEnqStatus();
		EnquiryForm form = new EnquiryForm();
		model.addAttribute("courseNames", courses);
		model.addAttribute("enqstatusNames", enqstatus);
		model.addAttribute("formObj", form);
	}

	@PostMapping("/addenquiry")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		boolean status = enquiryService.saveEnquiry(formObj);
		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		return "addEnquiry";

	}

	@GetMapping("/viewenquiry")
	public String viewEnquiryPage(EnquirySearchCriteria searchCriteria, Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnquiry> enquiries = enquiryService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "viewEnquiry";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/filterEnquiries")
	public String geFilterEnquiries(@RequestParam("cname") String cname, @RequestParam("status") String status, @RequestParam("mode") String mode,Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnquiry> filteredEnq = enquiryService.getFilteredEnquiries(criteria, userId);
		model.addAttribute("enquiries", filteredEnq);
		return "viewfilterEnquiries";
	}
}
