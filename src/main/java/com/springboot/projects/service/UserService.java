package com.springboot.projects.service;

import com.springboot.projects.binding.LoginForm;
import com.springboot.projects.binding.SignUpForm;
import com.springboot.projects.binding.UnlockForm;

public interface UserService {
	public String login(LoginForm form);

	public boolean signup(SignUpForm form) throws Exception ;

	public boolean unlockaccount(UnlockForm form);

	public boolean forgotpswd(String email) throws Exception;

}
