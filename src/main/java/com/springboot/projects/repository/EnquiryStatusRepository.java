package com.springboot.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.projects.entity.Course;
import com.springboot.projects.entity.EnquiryStatus;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatus, Integer> {

}
