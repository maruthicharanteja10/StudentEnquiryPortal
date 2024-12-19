package com.springboot.projects.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.projects.binding.LoginForm;
import com.springboot.projects.binding.SignUpForm;
import com.springboot.projects.binding.UnlockForm;
import com.springboot.projects.entity.UserDetails;
import com.springboot.projects.repository.UserDetailsRepository;
import com.springboot.projects.utility.EmailUtils;
import com.springboot.projects.utility.PasswordUtils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String login(LoginForm form) {
		return null;
	}

	@Override
	public boolean signup(SignUpForm form) throws Exception {
		// copy data from binding object to endtity object
		UserDetails entity = new UserDetails();
		BeanUtils.copyProperties(form, entity);
		// To generate random password
		String tempPswd = PasswordUtils.generateRandomPswd();
		entity.setPassword(tempPswd);
		entity.setAccountStatus("LOCKED");
		userDetailsRepository.save(entity);
//To send Email
		String recipient = form.getEmail();
		String subject = "Unlock Your Account";
		String emailBody = "<h1>Use below temporary password to unlock account</h1>" + "Temporary Pswd : " + tempPswd
				+ "<br/>" + "<a href=\"http://localhost:8080/unlock?email=" + recipient
				+ "\">Click Here to Unlock Account</a>";
		emailUtils.sendEmail(recipient, subject, emailBody);

		return true;
	}

	@Override
	public String unlockaccount(UnlockForm form) {
		return null;
	}

	@Override
	public String forgotpswd(String email) {
		return null;
	}

}
