package com.springboot.projects.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;
import com.springboot.projects.entity.StudentEnquiry;
import com.springboot.projects.entity.UserDetails;
import com.springboot.projects.repository.StudentEnquiryRepository;
import com.springboot.projects.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService enquiryService;
	private StudentEnquiryRepository enquiryRepository;

	@GetMapping("/dashboard")
	public String dashboardpage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse data = enquiryService.getDashboardData(userId);
		System.out.println(data);
		model.addAttribute("dashboarddata", data);
		return "dashboard";
	}

	private void initForm(Model model) {
		List<String> courses = enquiryService.getCourses();
		List<String> enqstatus = enquiryService.getEnqStatus();
		EnquiryForm form = new EnquiryForm();
		model.addAttribute("courseNames", courses);
		model.addAttribute("enqstatusNames", enqstatus);
		model.addAttribute("formObj", form);
	}

	@GetMapping("/addenquiry")
	public String addEnquiryPage(Model model) {
		initForm(model);
		return "addEnquiry";
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


	 @GetMapping("/editenquiry/{enquiryId}")
	    public String editEnquiry(@PathVariable Integer enquiryId, Model model) {
	        initForm(model);

	        StudentEnquiry enquiry = enquiryService.getEnquiryById(enquiryId);
	        if (enquiry == null) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enquiry not found");
	        }

	        model.addAttribute("enquiry", enquiry);
	        return "editEnquiry"; 
	    }

	    @PostMapping("/editenquiry/{enquiryId}")
	    public String updateEnquiry(@PathVariable Integer enquiryId, 
	                                @ModelAttribute("enquiry") EnquiryForm formObj, 
	                                Model model) {
	        boolean updated = enquiryService.updateEnquiry(enquiryId, formObj);

	        if (updated) {
	            model.addAttribute("succMsg", "Enquiry Updated Successfully");
	        } else {
	            model.addAttribute("errMsg", "Failed to Update Enquiry");
	        }

	        return "redirect:/viewenquiry";
	    }


	@GetMapping("/viewenquiry")
	public String viewEnquiryPage(EnquirySearchCriteria searchCriteria, Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnquiry> enquiries = enquiryService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "viewEnquiry";
	}

	@GetMapping("/filterEnquiries")
	public String geFilterEnquiries(@RequestParam("cname") String cname, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnquiry> filteredEnq = enquiryService.getFilteredEnquiries(criteria, userId);
		model.addAttribute("enquiries", filteredEnq);
		return "viewfilterEnquiries";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

}
