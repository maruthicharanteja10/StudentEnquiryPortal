package com.springboot.projects.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.springboot.projects.entity.Course;
import com.springboot.projects.entity.EnquiryStatus;
import com.springboot.projects.repository.CourseRepository;
import com.springboot.projects.repository.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnquiryStatusRepository enquiryStatusRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepository.deleteAll();
		Course c1 = new Course();
		Course c2 = new Course();
		Course c3 = new Course();
		Course c4 = new Course();
		c1.setCourseName("Java");
		c2.setCourseName("Python");
		c3.setCourseName("AWS");
		c4.setCourseName("Devops");
		courseRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		
		enquiryStatusRepository.deleteAll();
		EnquiryStatus s1 = new EnquiryStatus();
		EnquiryStatus s2 = new EnquiryStatus();
		EnquiryStatus s3 = new EnquiryStatus();

		s1.setStatusName("Enrolled");
		s2.setStatusName("Lost");
		s3.setStatusName("New");
		enquiryStatusRepository.saveAll(Arrays.asList(s1, s2, s3));

	}
}
