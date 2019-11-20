package mapGeist.controller;

import mapGeist.model.*;

import java.util.Collection;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModeratorController
{	
	@RequestMapping(value="/moderator/home")
	public ModelAndView moderatorHome(@RequestParam(value="message", required=false) Optional<String> message, HttpServletRequest request)
	{
		Moderator mod = (Moderator)request.getSession().getAttribute("loggedInUser");
		if(mod != null)
		{
			if(message.isPresent())
			{
				return new ModelAndView("/moderator/home", "message", message);
			}
			else
			{
				return new ModelAndView("/moderator/home");
			}
		}
		else
		{
			return new ModelAndView("login", "error", "Get the fuck out, hacker boi!");
		}
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(
			@RequestParam(value="username", required=false) Optional<String>  username, 
			@RequestParam(value="password", required=false) Optional<String> password,
			HttpServletRequest request)
	{
		if(username.isPresent() && password.isPresent())
		{
			Moderator toAuth = ModeratorDAO.attemptLogin(username.get(), password.get());
			if(toAuth != null)
			{
				request.getSession().setAttribute("loggedInUser", toAuth);
				
				// TODO: Create authentication token
				return new ModelAndView("redirect: moderator/home", "message", "Welcome, " + toAuth.getFullName());
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
