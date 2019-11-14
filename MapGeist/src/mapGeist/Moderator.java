package mapGeist;

import java.util.UUID;


public class Moderator
{
	private UUID id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	
	public Moderator()
	{
		
	}
	public Moderator(String username, String password, String firstName, String lastName)
	{
		// Generate random UUID
		this.id = UUID.randomUUID();
		
		this.username = username;
		this.password = Maptilities.encryptPassword(password, this.getSalt());
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UUID getID()
	{
		return this.id;
	}
	public void setID(String id)
	{
		this.id = UUID.fromString(id);
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
