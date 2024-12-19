package com.springboot.projects.service;

import com.springboot.projects.binding.LoginForm;
import com.springboot.projects.binding.SignUpForm;
import com.springboot.projects.binding.UnlockForm;

public interface UserService {
	public String login(LoginForm form);

	public String signup(SignUpForm form);

	public String unlockaccount(UnlockForm form);

	public String forgotpswd(String email);

}
