package app
import java.util.Map;


public class Campaign {
	List<Team> teams
}

class Team{
	Utc utc
	String description
	
	List<Personal> personal
	List<Equipment> equipment
}

class Personal{
	Utc utc
	
	Boolean available
	String location
	
	String fullName
	List<Credential> credentials
	Integer score
}

class Equipment{
	Utc utc
	
	Boolean available
	String location	
	Integer score	
}

class Credential{
	String name
	String description
	Boolean active
	Date issued
	Date expired	
}

class Utc{
	String code
	String name
}