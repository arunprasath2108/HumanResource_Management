package Utils;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import Model.Employee;
import Utils.EnumClass.Role;

public class Utils 
{
	
	public static Scanner scanner = new Scanner(System.in);
	
	
     public static String getStringInput()
     {
		return scanner.nextLine();
     }
	
	
	 public static int getIntInput()
	 {
		int input = scanner.nextInt();
		scanner.nextLine();
		return input;
	 }
	
	
	 public static void printLoginFailMessage()
	 {
		 System.out.println(" Incorrect User name or Login ID.");
		 printSpace();
		 printLine();
	 }
	 
	 
	 public static void printSpace()
	 {
		 System.out.print("\n");
	 }
	 
	 
	 public static void printLine()
	 {
		 System.out.println(" ----------------------------------------");
	 }	 
	 
	 
	 public static void printInvalidInputMessage()
	 {
		 	printSpace();
			System.out.println(" Please Enter a Valid Input.");
			printSpace();
	 }
	 
	 
	 public static void printWelcomeMessage(String name)
	 {
			System.out.println("       Welcome "+name+" !!");
			Utils.printLine();
	 }
	 
	 
	 public static void printHeader()
	 {
		 	Utils.printSpace();
			System.out.println(" FEATURES :");
	 }


	 public static void printLogOutMessage()
	 {
	     printSpace();
		 System.out.println("	~ Logged Out ~	");	
		 printSpace();
	 }
	
	
	 public static void printTeamAddedSuccessful()
	 {
	 	 printSpace();
	  	 System.out.println(" Successfully added a Team. ");
		 printSpace();
	 }
	 
	
	public static int printmessageCount(Employee employee) 
	{
		
		return employee.getInbox().size();
		
	}
	
	public static int printNotificationCount(Employee employee) 
	{
		
		return employee.getNotification().size();
		
	}
	
	
	public static String getTeamName(int id)
	{
		
		for( Entry<Integer, String> entries : Resource.teamMap.entrySet())
		{
			if(entries.getKey() == (Integer)id)
			{
				
				return (String) entries.getValue();
			}
		}
		
		return null;
		
	}
	
	
	public static Employee getEmployeeObject( int id)
	{
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getemployeeID() == id)
			{
				return employee;
			}
		}
		return null;
		
	}
	
	
	public static String getEmployeeName(int id)
	{
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getemployeeID() == id)
			{
				return employee.getemployeeName();
			}
		}
		
		return null;
		
	}
	
	
	public static Role getRole( int id)
	{
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getemployeeID() == id)
			{
				return employee.getemployeeRole();
			}
		}
		return null;
	}
	
	
	public static int getEmployeeID( String name)
	{
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getemployeeName().equalsIgnoreCase(name))
			{
				return employee.getemployeeID();
			}
		}
		
		return 0;
		
	}
	
	public static String getCurrentDateTime()
	{
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm");
		String todayDate = simpleDateFormat.format(date);
		return  todayDate;
	}

	public static String getTodayDate()
	{
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String todayDate = simpleDateFormat.format(date);
		
		return todayDate;
	}
	 
	 
}  // class ends.
