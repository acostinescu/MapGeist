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
	public ModelAndView moderatorHome(HttpServletRequest request)
	{
		Moderator mod = (Moderator)request.getSession().getAttribute("loggedInUser");
		if(mod != null)
		{
			return new ModelAndView("/moderator/home", "message", "Welcome, " + mod.getFullName());
		}
		else
		{
			return new ModelAndView("redirect: ../login", "error", "Get the fuck out, hacker boi!");
		}
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(
			@RequestParam(value="username", required=false) Optional<String>  username, 
			@RequestParam(value="password", required=false) Optional<String> password,
			@RequestParam(value="logout", required=false) Optional<String> logout,
			@RequestParam(value="error", required=false) Optional<String> error,
			HttpServletRequest request)
	{
		if(error.isPresent())
		{
			return new ModelAndView("redirect: login", "error", error.get());
		}
		else if(logout.isPresent())
		{
			request.getSession().setAttribute("loggedInUser", null);
			return new ModelAndView("login");
		}
		else if(username.isPresent() && password.isPresent())
		{
			// Attempt to log in the moderator with the given credentials
			Moderator toAuth = ModeratorDAO.attemptLogin(username.get(), password.get());
			
			if(toAuth != null)
			{
				// Add the Moderator to the session
				request.getSession().setAttribute("loggedInUser", toAuth);

				// Redirect to the moderator homepage
				return new ModelAndView("redirect: moderator/home");
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
