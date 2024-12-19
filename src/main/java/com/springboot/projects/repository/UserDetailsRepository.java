package com.springboot.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.projects.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

}
