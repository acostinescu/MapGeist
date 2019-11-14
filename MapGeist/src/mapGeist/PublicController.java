package mapGeist;
 
import java.sql.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class PublicController {
 
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
 
		Connection dbConn = Connector.getConnection();
		
		int test = 0;
		
		try
		{
			Statement stmt = dbConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Test");
		
			if(rs.next())
			{
				test = rs.getInt("Test");
			}
		}
		catch (SQLException ex)
		{
			throw new RuntimeException("Whoops.", ex);
		}
		
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial" + test + "</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}
}