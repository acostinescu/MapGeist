package mapGeist.controller;

import mapGeist.model.*;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModeratorController
{	
	@RequestMapping(value="/moderator/home")
	public ModelAndView moderatorHome(@RequestParam("message") String message)
	{
		return new ModelAndView("/moderator/home", "message", message);
	}
	
	
	@RequestMapping(value="/login")
	public ModelAndView login(
			@RequestParam(value="username", required=false) Optional<String>  username, 
			@RequestParam(value="password", required=false) Optional<String> password)
	{
		if(!(username.isEmpty() || password.isEmpty()))
		{
			Moderator toAuth = ModeratorDAO.attemptLogin(username.get(), password.get());
			if(toAuth != null)
			{
				// TODO: Create authentication token
				return new ModelAndView("redirect: moderator/home", "message", toAuth.getFullName());
			}
			else
			{
				return new ModelAndView("login", "error", "Oops! Incorrect username or password.");
			}
		}
		else
		{
			return new ModelAndView("login");
		}
	}
}
