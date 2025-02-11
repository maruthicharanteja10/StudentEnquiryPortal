package com.springboot.projects.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.projects.binding.DashboardResponse;
import com.springboot.projects.binding.EnquiryForm;
import com.springboot.projects.binding.EnquirySearchCriteria;
import com.springboot.projects.constants.AppConstants;
import com.springboot.projects.entity.Course;
import com.springboot.projects.entity.EnquiryStatus;
import com.springboot.projects.entity.StudentEnquiry;
import com.springboot.projects.entity.UserDetails;
import com.springboot.projects.repository.CourseRepository;
import com.springboot.projects.repository.EnquiryStatusRepository;
import com.springboot.projects.repository.StudentEnquiryRepository;
import com.springboot.projects.repository.UserDetailsRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private EnquiryStatusRepository enquiryStatusRepository;
	@Autowired
	private StudentEnquiryRepository studentEnquiryRepository;

	public EnquiryServiceImpl(UserDetailsRepository userDetailsRepository) {
		super();
		this.userDetailsRepository = userDetailsRepository;
	}

	@Override
	public DashboardResponse getDashboardData(Integer Userid) {
		DashboardResponse response = new DashboardResponse();
		Optional<UserDetails> findbyId = userDetailsRepository.findById(Userid);
		if (findbyId.isPresent()) {
			UserDetails userEntity = findbyId.get();
			List<StudentEnquiry> enquiries = userEntity.getEnquiries();
			Integer totalCnt = enquiries.size();
			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnquiryStatus().equals(AppConstants.STR_ENROLLED))
					.collect(Collectors.toList()).size();
			Integer lostCnt = enquiries.stream().filter(e -> e.getEnquiryStatus().equals(AppConstants.STR_LOST))
					.collect(Collectors.toList()).size();
			response.setEnrolledCnt(enrolledCnt);
			response.setTotalEnquiryCnt(totalCnt);
			response.setLostCnt(lostCnt);
		}
		return response;
	}

	@Override
	public List<String> getCourses() {
		List<Course> findall = courseRepository.findAll();
		List<String> names = new ArrayList<>();
		for (Course entity : findall) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnquiryStatus> findall = enquiryStatusRepository.findAll();
		List<String> statuslist = new ArrayList<>();
		for (EnquiryStatus entity : findall) {
			statuslist.add(entity.getStatusName());
		}
		return statuslist;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		StudentEnquiry enqEntity = new StudentEnquiry();
		BeanUtils.copyProperties(form, enqEntity);
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		UserDetails userEntity = userDetailsRepository.findById(userId).get();
		enqEntity.setUser(userEntity);
		enqEntity.setCreatedDate(LocalDate.now());
		enqEntity.setUpdatedDate(LocalDate.now());

		studentEnquiryRepository.save(enqEntity);
		return true;
	}

	@Override
	public List<StudentEnquiry> getEnquiriesByUser(Integer userId) {
		Optional<UserDetails> user = userDetailsRepository.findById(userId);
		return user.map(UserDetails::getEnquiries).orElseThrow(() -> new RuntimeException(AppConstants.STR_USER_NOT_FOUND));
	}

	// Add a new enquiry
	 @Override
	    public StudentEnquiry getEnquiryById(Integer enquiryId) {
	        return studentEnquiryRepository.findById(enquiryId).orElse(null);
	    }

	  
	    @Override
	    public boolean updateEnquiry(Integer enquiryId, EnquiryForm enquiryForm) {
	        Optional<StudentEnquiry> optionalEnquiry = studentEnquiryRepository.findById(enquiryId);

	        if (optionalEnquiry.isEmpty()) {
	            return false; // Enquiry not found
	        }

	        StudentEnquiry enquiry = optionalEnquiry.get();

	        // Update fields
	        enquiry.setStudentName(enquiryForm.getStudentName());
	        enquiry.setStudentPhno(enquiryForm.getStudentPhno());
	        enquiry.setClassMode(enquiryForm.getClassMode());
	        enquiry.setCourseName(enquiryForm.getCourseName());
	        enquiry.setEnquiryStatus(enquiryForm.getEnquiryStatus());
	        enquiry.setUpdatedDate(LocalDate.now());

	        studentEnquiryRepository.save(enquiry);
	        return true;
	    }

	@Override
	public List<StudentEnquiry> getEnquiries() {
		Integer userId = (Integer) session.getAttribute(AppConstants.STR_USER_ID);
		Optional<UserDetails> findById = userDetailsRepository.findById(userId);
		if (findById.isPresent()) {
			UserDetails userDetEntity = findById.get();
			List<StudentEnquiry> enquiries = userDetEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnquiry> getFilteredEnquiries(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDetails> findById = userDetailsRepository.findById(userId);
		if (findById.isPresent()) {
			UserDetails userDetEntity = findById.get();
			List<StudentEnquiry> enquiries = userDetEntity.getEnquiries();
			if (null != criteria.getCourseName() & !"".equals(criteria.getCourseName())) {
				enquiries = enquiries.stream().filter(e -> e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getEnquiryStatus() & !"".equals(criteria.getEnquiryStatus())) {
				enquiries = enquiries.stream().filter(e -> e.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}

			return enquiries;
		}
		return null;
	}



}
