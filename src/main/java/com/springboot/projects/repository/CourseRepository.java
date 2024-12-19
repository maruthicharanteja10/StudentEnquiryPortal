package com.springboot.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.projects.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
