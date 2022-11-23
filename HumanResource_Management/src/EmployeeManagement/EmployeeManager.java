package EmployeeManagement;

import java.util.InputMismatchException;
import Model.Employee;
import Utils.Resource;
import Utils.Utils;
import Validation.EmployeeValidation;


public class EmployeeManager 
{
	
	private static final int VIEW_PROFILE = 1;
	private static final int EDIT_PERSONAL_INFO = 2;
	private static final int APPLY_LEAVE = 3;
	private static final int VIEW_LEAVE_REQUEST = 4;
	private static final int LOG_OUT = 6;
	
	
	private static final int EDIT_MOBILE_NUM = 1;
	private static final int EDIT_EMAIL = 2;
	private static final int EDIT_ADDRESS = 3;
	private static final int EDIT_WORK = 4;
	private static final int EDIT_STUDIES = 5;
	private static final int BACK_MENU = 6;
	
	
	private static final int BE_BTECH = 1;
	private static final int ME_MTECH = 2;
	private static final int ARTS = 3;
	
	
	
	

	public void listEmployeeMenu(Employee employee)
	{
		
		System.out.println(" Employee Features :");
		Utils.printLine();
		System.out.println(" 1. My Profile");
		System.out.println(" 2. Edit Personal Info");
		System.out.println(" 3. Apply Leave");
		System.out.println(" 4. View Leave Request Report");
		Utils.printSpace();
		
	}
	
	
	public void listPTMenu(Employee employee)
	{
		
		EmployeeValidation.checkProfileCompleted(employee);
		
		listEmployeeMenu(employee);
		System.out.println(" 5. LogOut.");
		Utils.printSpace();
		System.out.println(" Choose an Option :");
		
		try 
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			if(getInputFromEmployee(userInput, employee))
			{
				Utils.printLogOutMessage();
				return;
			}
			else
			{
				listPTMenu(employee);
			}
		}
		catch(InputMismatchException e)
		{
			Utils.printSpace();
			Utils.printInvalidInputMessage();
			Utils.printSpace();
			Utils.scanner.nextLine();
			listPTMenu(employee);
		}
	}
	
	
	public static boolean getInputFromEmployee(int userInput, Employee employeee)
	{
		
		switch (userInput)
		{
		
			case VIEW_PROFILE:
				displayProfile(employeee);
				displayPersonalInfo(employeee);   
				return false;
				
			case EDIT_PERSONAL_INFO:
				editPersonalInfo(employeee);    
				return false;
				
			case APPLY_LEAVE:
				//for apply leave
				return false;
				
			case VIEW_LEAVE_REQUEST:
				//view leave request
				return false;
				
			case LOG_OUT :
				return true;
				
			default:
				Utils.printInvalidInputMessage();
				return false;
				
		}
		
	} 

	
	private static void displayPersonalInfo(Employee employeee)
	{
		
		System.out.println(" PERSONAL INFORMATION :");
		Utils.printLine();
		System.out.println(" Mobile		  : "+employeee.getMobileNum());
		System.out.println(" Email ID	  : "+employeee.getEmailID());
		System.out.println(" Address	  : "+employeee.getAddress());
		System.out.println(" Work Experience  : "+employeee.getWorkExperience());
		System.out.println(" Education	  : "+employeee.getEducation());
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	
	public static void displayProfile(Employee employeee) 
	{
		
		Utils.printSpace();
		System.out.println(" EMPLOYEE DETAILS");
		Utils.printLine();
		System.out.println("  Team Name	  : " + employeee.getEmployeeTeamName());
		System.out.println("  Employee ID     : " + employeee.getemployeeID());
		System.out.println("  Name		  : " + employeee.getemployeeName());
		System.out.println("  Role		  : " + employeee.getemployeeRole());
		System.out.println("  Reporting to	  : " + employeee.getReportingToID()+" - "+employeee.getReportingTo());
		System.out.println("  Official Mail   : " + employeee.getCompanyMailId());
		System.out.println("  Date of Joining : " + employeee.getDateOfJoining());
		System.out.println("  Work Location	  : " + employeee.getEmployeeWorkLocation());
		Utils.printLine();
		Utils.printSpace();
		
	}
	
	
	public static void editPersonalInfo(Employee employee)
	{
		
		Utils.printSpace();
		displayPersonalInfo(employee);
		
		System.out.println(" 1.Add Mobile.");
		System.out.println(" 2.Change Email ID.");
		System.out.println(" 3.Edit Address.");
		System.out.println(" 4.Add Work Experience.");
		System.out.println(" 5.Educational Qualification.");
		Utils.printSpace();
		System.out.println(" 6. Back to Menu");
		Utils.printSpace();
		
		getInputForEdit(employee);
		
	}
	
	
	private static void getInputForEdit(Employee employee)
	{
		
		System.out.println(" Select an Option :");
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
					case EDIT_MOBILE_NUM :
						editMobileNum(employee); 
						break;
						
					case EDIT_EMAIL :
						editMailID(employee);   
						break;
						
					case EDIT_ADDRESS :    
						editAddress(employee);
						break;
						
					case EDIT_WORK :   
						editWorkExperience(employee);
						break;
						
					case EDIT_STUDIES :   
						editHighestQualification(employee);
						break;
						
					case BACK_MENU :
						break;
						
					default :
							Utils.printInvalidInputMessage();
							editPersonalInfo(employee);
							break;
							
			}
		
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();	
			editPersonalInfo(employee);
			return;
		}
		
	}

	
	private static void editMailID(Employee employee)
	{
		
		Utils.printSpace();
		System.out.println(" Enter Mail ID.");
		String mailID = Utils.getStringInput();
		Utils.printSpace();
		
		if(EmployeeValidation.isEmailValid(mailID))
		{
			employee.setEmailID(mailID);
			System.out.println("   ~ Mail ID added successful");
			editPersonalInfo(employee);
		}
		else
		{
			Utils.printSpace();
			System.out.println(" * Invalid Mail ID *");
			editPersonalInfo(employee);
		}
	}
	
	
	private static void editMobileNum(Employee employee)
	{
		
			Utils.printSpace();	
			System.out.println(" Enter 10 Digit Number.");
			String number = Utils.getStringInput();
			Utils.printSpace();
					
			if( EmployeeValidation.isMobileNumberValid(number))
			{
					Utils.printSpace();
					System.out.println("  * INVALID MOBILE NUMBER *");
					editPersonalInfo(employee);
			}
			else
			{
					employee.setMobileNum(number);
					System.out.println("   ~ Mobile Number added successful");
					editPersonalInfo(employee);
			}
	}

		
	private static void editAddress(Employee employee)
	{
		
		System.out.println(" Enter your Address in the below format.");
		Utils.printSpace();
		System.out.println(" Home Address, Street, City");
		Utils.printSpace();
		System.out.println(" sample address ->  1/12, Northcut Road, Coimbatore");
		Utils.printSpace();
		
		String address = Utils.getStringInput();
		Utils.printSpace();
		employee.setAddress(address);
		
		System.out.println("   ~ Address added successful");
		editPersonalInfo(employee);
		
	}
	
	
	private static void editWorkExperience(Employee employee)
	{
		
		System.out.println(" Enter Company Name : ");
		String companyName = Utils.getStringInput();
		Utils.printSpace();
		
		System.out.println(" Name of the Role : ");
		String role = Utils.getStringInput();
		Utils.printSpace();
		
		String timePeriod = getYearsOfExperience();
		Utils.printSpace();
		
		String WorkExperience = companyName+" - "+role+" - "+timePeriod+" years";
		employee.setWorkExperience(WorkExperience);
		
		System.out.println("   ~ Work Experience added Successful");
		editPersonalInfo(employee);
		

	}
	
	
	private static String getYearsOfExperience()
	{
		
		Utils.printSpace();
		System.out.println("  * Number of years you have worked : ");
		System.out.println("  * Minimum ~ 1 year || Maximum ~ 20 year ");
		Utils.printSpace();
		System.out.println("  NOTE : Experience less than 1 Year, Enter as -> 0");
		Utils.printSpace();
		
		String userInput = Utils.getStringInput();
		Utils.printSpace();
		
		if( EmployeeValidation.isExperienceYearValid(userInput))
		{
			if( userInput.equalsIgnoreCase("0"))
			{
				return "less than 1";
			}
			return userInput;
		}
		else
		{
			return getYearsOfExperience();
		}
		
	}
	
	private static void editHighestQualification(Employee employee)
	{
		
		System.out.println(" Enter your Qualification");
		Utils.printSpace();
		System.out.println(" 1. B.E / B.Tech ");
		System.out.println(" 2. M.E / M.Tech ");
		System.out.println(" 3. Arts Stream (Bsc / Msc / BA)");
		
		try
		{
			Utils.printSpace();
			System.out.println(" Choose a option.");
			Utils.printSpace();
			String degree = getDegreeInput();
			Utils.printSpace();
			
			if( degree == null)
			{
				EmployeeManager.editPersonalInfo(employee);
				return;
			}
			
			String passedOut = getPassedOutYear();
			Utils.printSpace();

			String studiesInfo = degree+" - "+passedOut+" passed out";
			employee.setEducation(studiesInfo);
			System.out.println("   ~ Educational Qualifications added successful");
			EmployeeManager.editPersonalInfo(employee);
			return;
				
		}
			
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();	
			editHighestQualification(employee);
			return;
			
		}
		
	}
	
	public static String getDegreeInput()
	{
		
		String degree = null;
		int userInput = Utils.getIntInput();
		Utils.printSpace();
		
		switch(userInput)
		{
		
			case BE_BTECH :
				degree = "B.E/B.Tech";
				return degree;
				
			case ME_MTECH :
				degree = "M.E/M.Tech";
				return degree;
				
			case ARTS :
				degree = "Arts&Science";
				return degree;
				
			default :
				Utils.printInvalidInputMessage();
				return degree = getDegreeInput();
			
		}
		
	}
	
	public static String getPassedOutYear( )
	{
		
		System.out.println(" Passed Out year     Format  -> [ yyyy ]");
		String passedOut = Utils.getStringInput();
		Utils.printSpace();
		
		if(EmployeeValidation.isPassedOutYearValid(passedOut))
		{
			return passedOut;
		}
		else
		{
			
			System.out.println(" Year must be greater than 1985 or equals to Present Year.");
			Utils.printSpace();
			return getPassedOutYear();
		}
		
	}
	
	
	public static void listTeamName() 
	{
	
		Utils.printLine();
		System.out.println(" List of Teams : ");
		Utils.printLine();
		
		for (int teamName : Resource.teamMap.keySet()) 
		{
			int key = teamName;
			String value = Resource.teamMap.get(teamName);
			System.out.println("  "+key+" ~ " + value);
		}
		
		Utils.printSpace();
	}
	
		//overloaded method for edit teamName
		public static boolean listTeamName(String team) 
		{
			
			Utils.printLine();

			if (Resource.teamMap.isEmpty()) 
			{
				return false;
			} 
			else
			{
				System.out.println(" List of Teams : ");
				Utils.printLine();
				
				for (int teamName : Resource.teamMap.keySet()) 
				{
				
					int key = teamName;
					String value = Resource.teamMap.get(teamName);
					if(value.equals(team)){}
					else
					{
						System.out.println("  "+key+" ~ " + value);
					}
				}
				
				Utils.printSpace();
				return true;
			}
		}
	
		public int printNotificationCount(Employee employee) 
		{
			
			return employee.getNotification().size();
			
		}
	

	
} //end of class
