package model;

import java.util.ArrayList;
import java.util.HashMap;

import utils.EnumClass.Role;


public class Employee 
{
	
	//basic info fields
	private int employeeID;
	private int reportingToID;
	private Role role;
	private String employeeTeamName;
	private String employeeName;
	private String dateOfJoining;
	private String reportingTo;
	private String employeeWorkLocation;
	private String companyMailId;
	private String gender;
	
	//personal info fields
	private String mobileNumber;
	private String emailID;
	private String address;
	private String workExperience;
	private String education;
	
	
	//list for storing  inbox messages 
	private ArrayList<String> inbox = new ArrayList<>();
	
	//for storing Notification messages
	private HashMap<Integer, String> notification = new HashMap<>();

	//for verify the duplicate request for team change.
	private boolean  isTeamChanged = false;
	
	//for display Notification Count.
	private boolean isNotificationSeen = false;
	
	
	public Employee(int id, String name, Role role, String teamName ,  String reportingTo, int reportingToID, String workLocation, String doj, String mail, String gender)
	{
		
		employeeID = id;
		employeeName = name;
		this.role = role;
		employeeTeamName = teamName;
		this.reportingTo = reportingTo;
		this.reportingToID = reportingToID;
		employeeWorkLocation = workLocation;
		dateOfJoining = doj;
		companyMailId = mail;
		this.gender = gender;
		
	}
	
	
	
	
	public void setemployeeID(int id)
	{
		employeeID = id;
	}
	
	public int getemployeeID()
	{
		return employeeID;
	}
	
	public void setemployeeName(String name)
	{
		employeeName = name;
	}
	
	public String getemployeeName()
	{
		return employeeName;
	}
	
	public void setemployeeRole(Role role)
	{
		this.role = role;
	}
	
	public Role getemployeeRole()
	{
		return role;
	}
	
	public void setEmployeeTeamName(String teamName)
	{
		employeeTeamName = teamName;
	}
	
	public String getEmployeeTeamName()
	{
		return employeeTeamName;
	}
	
	public void setReportingTo(String id)
	{
		reportingTo = id;
	}
	
	public String getReportingTo()
	{
		return reportingTo;
	}
	
	public void setReportingToID(int id)
	{
		reportingToID = id;
	}
	
	public int getReportingToID()
	{
		return reportingToID;
	}
	
	public void setEmployeeWorkLocation(String location)
	{
		employeeWorkLocation = location;
	}
	
	public String getEmployeeWorkLocation()
	{
		return employeeWorkLocation;
	}
	
	public void setDateOfJoining(String doj)
	{
		dateOfJoining = doj;
	}
	
	public String getDateOfJoining()
	{
		return dateOfJoining;
	}
	
	public void setCompanyMailId(String mailID)
	{
		companyMailId = mailID;
	}
	
	public String getCompanyMailId()
	{
		return companyMailId;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public void setMobileNum(String number)
	{
		mobileNumber = number;
	}
	
	public String getMobileNum()
	{
		return mobileNumber;
	}
	
	public void setEmailID(String mail)
	{
		emailID = mail;
	}
	
	public String getEmailID()
	{
		return emailID;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setWorkExperience(String work)
	{
		workExperience = work;
	}
	
	public String getWorkExperience()
	{
		return workExperience;
	}
	
	public void setEducation(String education)
	{
		this.education = education;
	}
	
	public String getEducation()
	{
		return education;
	}
	
	public void setInbox(ArrayList<String> inbox) 
	{
		this.inbox = inbox;
	}
	
	public ArrayList<String> getInbox() 
	{
		return inbox;
	}
	
	public void setTeamChanged(boolean userInput)
	{
		isTeamChanged = userInput;
	}
	
	public boolean isTeamChanged()
	{
		return isTeamChanged;
	}
	
	public void setNotificationSeen(boolean userInput)
	{
		isNotificationSeen = userInput;
	}
	
	public boolean getNotificationSeen()
	{
		return isNotificationSeen;
	}

	public void setNotification(HashMap<Integer, String> notification) 
	{
		this.notification = notification;
	}
	
	public HashMap<Integer, String> getNotification() 
	{
		return notification;
		
	}

	
	
	
}   //class ends
