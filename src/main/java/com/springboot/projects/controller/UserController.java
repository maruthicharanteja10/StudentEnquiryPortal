package com.springboot.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.projects.binding.LoginForm;
import com.springboot.projects.binding.SignUpForm;
import com.springboot.projects.binding.UnlockForm;
import com.springboot.projects.entity.UserDetails;
import com.springboot.projects.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String LoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String Login(@ModelAttribute("form") LoginForm form, Model model) {
		String status = userService.login(form);
		if (status == "success") {
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) throws Exception {
		boolean status = userService.signup(form);
		if (status) {
			model.addAttribute("succMsg", "Account created Check Your Email");
		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam("email") String email, Model model) {
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlock", unlockForm);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String UnlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);
		if (unlock.getNewPswd().equals(unlock.getConfirmPswd())) {
			boolean status = userService.unlockaccount(unlock);
			if (status) {
				model.addAttribute("successMsg", "Your Account is unlocked");
			} else {
				model.addAttribute("errMsg", "Given Temporary pswd is incorrect, check your email");
			}
		} else {
			model.addAttribute("errMsg", "New pswd and Confirm pswd should be same");
		}
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotpswdPage() {
		return "forgotpswd";
	}

	@PostMapping("/forgotpswd")
	public String forgotpswd(@RequestParam("email") String email, Model model) throws Exception {
		boolean status=userService.forgotpswd(email);
		if(status) {
			model.addAttribute("successMsg", "password is sent to your mail");
		} else {
			model.addAttribute("errMsg", "Invalid email");
		}
		return "forgotpswd";
	}

}
