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

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;

	public UserServiceImpl(UserDetailsRepository userDetailsRepository) {
		super();
		this.userDetailsRepository = userDetailsRepository;
	}

	@Override
	public String login(LoginForm form) {
		UserDetails entity = userDetailsRepository.findByEmailAndPassword(form.getEmail(), form.getPswd());
		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity.getAccountStatus().equals("LOCKED")) {
			return "Your account is Locked";
		}
		// create session and store user data in session
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean signup(SignUpForm form) throws Exception {
		UserDetails user = userDetailsRepository.findByEmail(form.getEmail());
		if (user != null) {
			return false;
		}
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
	public boolean unlockaccount(UnlockForm form) {
		UserDetails entity = userDetailsRepository.findByEmail(form.getEmail());
		if (entity.getPassword().equals(form.getTempPswd())) {
			entity.setPassword(form.getNewPswd());
			entity.setAccountStatus("UNLOCKED");
			userDetailsRepository.save(entity);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean forgotpswd(String email) throws Exception {
		UserDetails entity = userDetailsRepository.findByEmail(email);
		if (entity == null) {
			return false;
		}
		String subject = "Recover Password";
		String body = "Your Pswd :" + entity.getPassword();
		emailUtils.sendEmail(email, subject, body);
		return true;
	}

}
