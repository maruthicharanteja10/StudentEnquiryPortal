package com.springboot.projects.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.projects.entity.StudentEnquiry;

public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {
	 
}
