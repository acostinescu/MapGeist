package mapGeist.model;

import java.util.UUID;

import mapGeist.Maptilities;


public class Moderator
{
	private String id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	
	public Moderator()
	{
		// Generate random UUID
		this.id = UUID.randomUUID().toString();
	}
	public Moderator(String username, String password, String firstName, String lastName)
	{
		// Generate random UUID
		this.id = UUID.randomUUID().toString();
		
		this.username = username;
		this.password = Maptilities.encryptPassword(password, this.getSalt());
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Moderator(String id, String username, String encryptedPassword, String firstName, String lastName)
	{
		this.id = id;
		this.username = username;
		this.password = encryptedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getID()
	{
		return this.id;
	}
	public void setID(String id)
	{
		this.id = id;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		this.password = Maptilities.encryptPassword(password, this.getSalt());
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return this.lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getFullName()
	{
		return this.firstName + " " + this.lastName;
	}
	
	public String getSalt()
	{
		// The salt is the first 4 characters of the UUID
		return this.id.toString().substring(0, 3);
	}
	
	
}
