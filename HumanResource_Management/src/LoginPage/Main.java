package LoginPage;

import java.util.InputMismatchException;

import EmployeeManagement.EmployeeManager;
import EmployeeManagement.HumanResource;
import EmployeeManagement.SeniorEmployee;
import Model.Employee;
import Utils.Resource;
import Utils.Utils;
import Utils.EnumClass.Role;
import Validation.EmployeeValidation;


public class Main 
{
	
	private static final int LOGIN = 1;
	private static final int EXIT = 2;
	
	public static void main(String[] args) 
	{
		
									//ID, Name, role, team name, ReportingTo Name, ReportingTo ID, Location, DOJ, Official Mail, Gender
		
		Employee ceo = new Employee(1, "Jack_CEO", Role.CEO, "HEAD", "DEFAULT", 0, "CHENNAI", "1-11-2022", "ceo@zoho.in", "MALE");
		Resource.employees.add(ceo);
		
		Employee hr = new Employee(2, "HR_Team", Role.HR, "HR", "JACK_CEO", 1, "Chennai", "2-11-2022", "hr@zoho.in", "MALE");
		Resource.employees.add(hr);
		
		//adding CEO and HR Official mail
		Resource.officialMail.add("ceo@zoho.in");
		Resource.officialMail.add("hr@zoho.in");
		
		Boolean condition = true;
		
		while(condition)
		{
			
			 System.out.println(" Enter an Option : ");
			 Utils.printLine();
			 Utils.printSpace();
			 System.out.println(" 1. Login.");
			 System.out.println(" 2. Exit. ");
			 Utils.printSpace();
			 
			 try
			 {
				 int userInput = Utils.getIntInput();
				 Utils.printSpace();
			 
				 switch(userInput)
				 	{
				 
					 	case LOGIN :
					 		Utils.printSpace();
				 			employeeLogin();
				 			break;
				 			
				 		case EXIT :
				 			System.out.println("  ~ Thanks for your Patience ~ ");
				 			return;
				 			
				 		default :
				 			Utils.printInvalidInputMessage();
				 	}
			 }
			 
			 catch(InputMismatchException e)
			 {
				 System.out.println(" Plaese, enter a Integer value.");
				 Utils.printSpace();
				 Utils.scanner.nextLine();
			 }
				
		} 
		

     }
	
	private static  void employeeLogin() 
	{
		
		 System.out.println("  * HR - login ID : 2 ");
		 Utils.printSpace();
		
		try
		{
			System.out.println(" Enter User ID :");
			int userID = Utils.getIntInput();
			Utils.printSpace();
	
			if (EmployeeValidation.isEmployeePresent(userID))
			{
				loginAsUser(userID);
			}
			else 
			{
				System.out.println(" User ID is Not Available.");
				Utils.printSpace();
			}
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			employeeLogin();
		}
	
	}
	
	
	private static void loginAsUser(int userID) 
	{

			for (Employee employeee : Resource.employees) 
			{

				if (employeee.getemployeeID() == userID) 
				{ 
					Role userRole = employeee.getemployeeRole();
					
					if (userRole.equals(Role.HR)) 
					{

						HumanResource hr = new HumanResource();
						Utils.printWelcomeMessage(employeee.getemployeeName());
						hr.listEmployeeMenu(employeee);
						break;
						
					}

					else if (userRole.getValue() < Role.PT.getValue() && userRole.getValue() > Role.HR.getValue())
					{
						
						SeniorEmployee seniorEmployee = new SeniorEmployee();
						Utils.printWelcomeMessage(employeee.getemployeeName());
						seniorEmployee.listEmployeeMenu(employeee);
						break;
						
					}

					else if (userRole.equals(Role.PT))
					{
						
						EmployeeManager pt = new EmployeeManager();
						Utils.printWelcomeMessage(employeee.getemployeeName());
						pt.ptMenu(employeee);
						break;
						
					}

				} 
				
			} 
	}


}

		
			 			
				 
			

