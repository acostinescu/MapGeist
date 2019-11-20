package mapGeist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mapGeist.model.Moderator;
import mapGeist.model.ModeratorDAO;
 
@Controller
public class PublicController {
 
	@RequestMapping("/welcome")
	public ModelAndView helloWorld()
	{
		String message = "<br><div style='text-align:center;'><h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping(value="/welcome", params= {"message"})
	public ModelAndView helloWorld(@RequestParam("message") String message)
	{
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping("/CreateUser")
	public ModelAndView createUser()
	{
		Moderator mod = new Moderator("ttesterson2", "1234", "Test", "Testerson");
		
		ModeratorDAO.insertModerator(mod);
		
		String message = "<h1>Created user: " + mod.getFullName() + "</h1>";
		return new ModelAndView("welcome", "message", message);
	}
	
	@RequestMapping("/AddEvent")
	public String addEvent(@RequestParam("EventName") String eventName)
	{
		System.out.println(eventName);
		return "index";
	}
}