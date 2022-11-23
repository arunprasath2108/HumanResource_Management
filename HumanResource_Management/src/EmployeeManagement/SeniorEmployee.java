package EmployeeManagement;

import java.util.InputMismatchException;
import java.util.Map.Entry;

import Model.Employee;
import Utils.Resource;
import Utils.Utils;
import Utils.EnumClass.Role;
import Validation.EmployeeValidation;


public class SeniorEmployee extends EmployeeManager implements EmployeeFeatures 
{
	
	
	private static final int VIEW_REPORTEE = 5;
	private static final int APPROVE_LEAVE = 6;
	private static final int REQUEST_TEAM_CHANGE = 7;
	private static final int APPROVE_REJECT_TEAM_CHANGE = 8;
	private static final int NOTIFICATION = 9;
	private static final int LOG_OUT = 10;
	
	private static final int REPLY = 1;
	private static final int BACK = 2;   //used for all back options.
	
	private static final int PROCEED_TO_HR = 1;
	private static final int REJECT = 2;
	
	private static final int CHANGE_TEAM = 1;

	//for View Reportees 
	boolean isReporteePresent = false;
	
	
	
	
	public void listEmployeeMenu(Employee employee)
	{
		
		EmployeeValidation.checkProfileCompleted(employee);
		
		super.listEmployeeMenu(employee);
		
		listSeniorEmployeeMenu(employee);
		
		chooseOptionFromList(employee);
		
	} 
	

	public  void requestMessages(Employee employee)
	{
		
		int senderID = 0;
		
		//condition to check MTS cannot Accpt/reject team change request.
		if(employee.getemployeeRole().getValue() == Role.MTS.getValue())
		{
			Utils.printSpace();
			System.out.println(" You don't have access to Approve / Reject Team Change.");
			Utils.printSpace();
			return;
		}
		
		if(employee.getRequests().isEmpty() == true)
		{
			Utils.printSpace();
			System.out.println("  ~ No Requests !");
			Utils.printSpace();
			return;
		}
		
		Utils.printSpace();
		System.out.println("  Team Change Request : ");
		Utils.printLine();
		
		for(int messageCount = 0; messageCount < employee.getRequests().size(); messageCount++)
		{
			
			String[] splitMessage = employee.getRequests().get(messageCount).split("-");
			
			senderID = Integer.parseInt(splitMessage[0]);  
			String senderName = Utils.getEmployeeName(senderID); 
			String senderRequest = splitMessage[1];		
			String teamName = splitMessage[3];
			String  time = splitMessage[4];
			

			messageCount++;
			System.out.println("  "+messageCount+" - "+senderName+"            "+time);
			messageCount--;
		
			Utils.printSpace();
			System.out.println("      ~ "+senderRequest+" ["+teamName+"]");
			Utils.printSpace();
			
			
		}
		
		Utils.printSpace();
		replyInboxMessages(employee);
	
	}
	
	
	private void printNotification(Employee employee)
	{
		
		if(employee.getNotification().isEmpty())
		{
			System.out.println("    No Messages..!");
			Utils.printSpace();
		}
		
		employee.setNotificationSeen(true);
		for( Entry<Integer, String> messages : employee.getNotification().entrySet())
		{
			System.out.println(" "+messages.getValue());
			Utils.printSpace();
		}
		
	}
	
	
	private void chooseOptionFromList(Employee employee)
	{
		
		try
		{
			System.out.println(" Choose an Option :");
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
				
				case VIEW_REPORTEE : 
					viewReportees(employee);
					listEmployeeMenu(employee);
					break;
				
				case APPROVE_LEAVE :  
					System.out.println("apply leave");
					listEmployeeMenu(employee);
					break;
					
				case REQUEST_TEAM_CHANGE :	
					teamChangeRequest(employee);
					listEmployeeMenu(employee);
					break;
					
				case APPROVE_REJECT_TEAM_CHANGE :
					requestMessages(employee);
					listEmployeeMenu(employee);
					break;
				
				case NOTIFICATION :
					printNotification(employee);
					listEmployeeMenu(employee);
					break;
					
				case LOG_OUT :
			 		Utils.printLogOutMessage();
					return;
					
				default :
				 	getInputFromEmployee(userInput, employee);
				 	listEmployeeMenu(employee);
				 	break;
			}
			
		}
		catch(InputMismatchException e)
		{
				Utils.printInvalidInputMessage();
				Utils.printSpace();
				Utils.scanner.nextLine();
				chooseOptionFromList(employee);
		}
		
	}
	

	private  void replyInboxMessages(Employee employee) 
	{
		
		Utils.printSpace();
		
		try
		{
			
			System.out.println(" 1. Reply.");
			System.out.println(" 2. Back.");
			Utils.printSpace();
			System.out.println(" Choose a option.");
			Utils.printSpace();
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
				
				case REPLY :
					processMessage(employee);
					break;
					
				case BACK :
					break;
					
				default :
					Utils.printInvalidInputMessage();
					replyInboxMessages(employee);
					 return;
			}
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			replyInboxMessages(employee);
			return;
		}
		
		
	}
	
	
	private  void processMessage(Employee employee)
	{
		
		Utils.printSpace();
		System.out.println(" Choose Index number to Reply.");
		Utils.printSpace();
		
		try
		{
			int userInput = Utils.getIntInput();
			userInput--; //matches userInput with index of ArrayList index
			
			if(userInput < 0)
			{
				processMessage(employee);
				return;
			}
			Utils.printSpace();
			
			for ( String message : employee.getRequests())
			{
					if( message.indexOf(message) == userInput)
					{
						confirmMessageBeforeReply(message, employee, userInput);	
						break;
					}
					else
					{
						Utils.printInvalidInputMessage();
						processMessage(employee);
						return;
					}
				
			}
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			processMessage(employee);
			return;
		}
		
		
	}


	private  void confirmMessageBeforeReply( String message, Employee employee, int userInput)
	{
		
		String[] splitMessage = message.split("-");
		int senderID = Integer.parseInt(splitMessage[0]);  
		String senderName = Utils.getEmployeeName(senderID);  
		String requestMessage = splitMessage[1];	
		String teamName = splitMessage[3];
		
		
		Utils.printLine();
		System.out.println("  From : "+senderName);
		Utils.printSpace();
		System.out.println("      "+requestMessage+" ["+teamName+"] ");
		
		Utils.printSpace();
		Utils.printLine();
		Utils.printSpace();
		Utils.printSpace();
		
		System.out.println(" 1. Proceed to HR.");
		System.out.println(" 2. Reject.");
		Utils.printSpace();
		
		try
		{
			int input = Utils.getIntInput();
			
			switch( input )
			{
				
				case PROCEED_TO_HR :
					forwardRequestToHR(message, employee);
					employee.getRequests().remove(userInput);
					break;
					
				
				case REJECT :
					rejectRequest(employee, message);
					employee.getRequests().remove(userInput);
					break;
					
				default :
					Utils.printInvalidInputMessage();
					processMessage(employee);
					return;
					
			}
			
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			processMessage(employee);
			return;
		}
		
	}

	
	private  void rejectRequest(Employee employee, String message) 
	{
		
		String[] splitMessage = message.split("-");
		int receiverID = Integer.parseInt(splitMessage[0]);  
		
		for( Employee employeee : Resource.employees)
		{
			
			if(employeee.getemployeeID() == receiverID)
			{
				String notification = " ~ Contact your Team Head for Further Reference about Team Change    "+Utils.getCurrentDateTime();
				employeee.getNotification().replace(receiverID, notification);
				Utils.printSpace();
				employeee.setTeamChanged(false);
				employeee.setNotificationSeen(false);
				System.out.println("      ~ Message Sent !!");
				Utils.printSpace();
				break;
			}
			
		}

		
	}


	private void forwardRequestToHR(String message, Employee  sender) 
	{
		
		int senderID = sender.getemployeeID();
		String[] splitMessage = message.split("-");
		int requestedPerson = Integer.parseInt(splitMessage[2]);
		String requestPersonName = Utils.getEmployeeName(requestedPerson);
		
		//changing sender ID. 
		splitMessage[0] = Integer.toString(senderID);
		
		StringBuilder requestMessage = new StringBuilder();
		
		for( String msg : splitMessage)
		{
			requestMessage.append(msg);
			requestMessage.append("-");
		}
		requestMessage.append(Utils.getCurrentDateTime());
		
		//forward message to HR
		for( Employee employee : Resource.employees)
		{
			if(employee.getemployeeRole() == Role.HR)
			{
				employee.getRequests().add(requestMessage.toString());
				Utils.printSpace();
				System.out.println("     ~ Request sent to HR");
				Utils.printSpace();
				
				//notify the sender about this request.
				String notification = " ~ Waiting for HR Acceptance for \""+requestPersonName.toUpperCase()+"\" Team Change      "+Utils.getCurrentDateTime();
				sender.getNotification().put(requestedPerson, notification);
				sender.setNotificationSeen(false);
				break;
			}
		}
		
		
		//send notification to requested employee about the process.
		for( Employee senderEmployee : Resource.employees)
		{
			if(senderEmployee.getemployeeID() == requestedPerson)
			{
				String notification = "  ~ Your Team Change Request waiting for HR Approval      "+Utils.getCurrentDateTime();
				senderEmployee.getNotification().replace(requestedPerson, notification);
				senderEmployee.setNotificationSeen(false);
				Utils.printSpace();
				break;
			}
		}
		
	}
	

	private void teamChangeRequest(Employee employee) 
	{
		
			if(employee.isTeamChanged() == true)
			{
				Utils.printSpace();
				System.out.println("   Request you have already sent is still pending....");
				Utils.printSpace();
				return;
			}
			if( Resource.teamMap.size() <= 1)
			{
				Utils.printSpace();
				System.out.println(" Sorry!! only "+ employee.getEmployeeTeamName()+" Team is Available ");
				Utils.printSpace();
				return;
			}
		
			Utils.printSpace();
	    	System.out.println(" 1. Choose Team.");
	    	System.out.println(" 2. Back");
	    	
	    	try
	    	{
	    		
	    		int userInput = Utils.getIntInput();
	    		
	    		switch(userInput)
	    		{
		    		case CHANGE_TEAM :
		    			proceedTeamChange(employee);
		    			break;
		    			
		    		case BACK :
		    			break;
		    			
	    			default :
	    				Utils.printInvalidInputMessage();
	    				teamChangeRequest(employee);
	    	    		return;
	    				
	    		}
	    	}
	    	catch(InputMismatchException e)
	    	{
	    		Utils.printInvalidInputMessage();
	    		Utils.scanner.nextLine();
	    		teamChangeRequest(employee);
	    		return;
	    	}
	}
	
	
	private void proceedTeamChange(Employee employee)
	{
		
	    	try
			{
	    		
				if(EmployeeManager.listTeamName(employee.getEmployeeTeamName()) == true)
				{
				
					Utils.printSpace();
					int teamID = Utils.getIntInput();
					Utils.printSpace();
					boolean isTeamPresent = EmployeeValidation.isTeamIDAlreadyExists(teamID);
					
					if(isTeamPresent == true)
					{
						String teamName = Utils.getTeamName(teamID);
						
						if( teamName.equals(employee.getEmployeeTeamName()))
						{
							Utils.printSpace();
							System.out.println(" You are Already in "+employee.getEmployeeTeamName()+" Team");
							Utils.printSpace();
							teamChangeRequest(employee);
							return;
						}
						
						if(EmployeeValidation.isTeamAlreadyExists(teamName) == true)
						{
							
							Utils.printSpace();
							System.out.println(" We're processing your Team Change Request...");
							Utils.printSpace();
							int receiverID = employee.getReportingToID();
							
							for( Employee receiver : Resource.employees)
							{
									if(receiver.getemployeeID() == receiverID)
									{
										receiver.getRequests().add(employee.getemployeeID()+"-Requested for Team change  -"+employee.getemployeeID()+"-"+teamName.toUpperCase()+"-"+Utils.getCurrentDateTime());
										break;
										
									}
							}
							
							Utils.printSpace();
							System.out.println("  	   ~ Message sent !!  ");
							
							employee.setTeamChanged(true);
							String notification = " ~ Team Change Request sent    " + Utils.getCurrentDateTime();
							employee.getNotification().put(employee.getemployeeID(), notification);   
							
							Utils.printSpace();
							Utils.printSpace();
							
						}
						else
						{
							Utils.printInvalidInputMessage();
							teamChangeRequest(employee);
							return;
						}
						
		     		}
					else
					{
						Utils.printSpace();
						System.out.println(" Sorry! please enter only Available Team ID.");
						Utils.printSpace();
						teamChangeRequest(employee);
						return;
					}
					
					}
		}
		catch(InputMismatchException e)
		{
				Utils.printSpace();
				System.out.println("  Enter Team ID only..!");
				Utils.printSpace();
				Utils.scanner.nextLine();
				teamChangeRequest(employee);
		}
}
		

	private void listSeniorEmployeeMenu(Employee employee)
	{
		
		System.out.println(" 5. View Reportees.");
		System.out.println(" 6. Approve Leave Request. ");
		System.out.println(" 7. Request Team Change.");
		System.out.print(" 8. Manage Team Change Request. ");
		
		if(EmployeeValidation.isRequestsEmpty(employee) == false)
		 {
			 int messageCount = Utils.printRequestCount(employee);
			 System.out.println(" ~ ["+messageCount+"] Unread Messages");
		 }
		
		Utils.printSpace();
		Utils.printSpace();
		
		System.out.print(" 9. Notification ");
		
		if(employee.getNotificationSeen() == false)
		{
			if(EmployeeValidation.isNotificationEmpty(employee) == false )
			{
				 int messageCount = printNotificationCount(employee);
				 System.out.println(" ~ ["+messageCount+"]");
			}
		}
		
		Utils.printSpace();
		Utils.printSpace();
		
		System.out.println(" 10. Logout");
		Utils.printLine();
		
		
	}
	
	
	private void viewReportees(Employee employee)
	{
		
		System.out.println(" Reportee List : ");
		Utils.printSpace();
		System.out.println(" Employee ID	  Name	     Role");
		Utils.printLine();
		
		for( Employee employeee : Resource.employees)
		{
			
			if(employeee.getEmployeeTeamName().equals(employee.getEmployeeTeamName()) && employeee.getemployeeRole().getValue() > employee.getemployeeRole().getValue() && employeee.getReportingTo().equalsIgnoreCase(employee.getemployeeName()))
			{
				isReporteePresent = true;
				System.out.printf("       %-10s %-10s %-10s\n",employeee.getemployeeID(),employeee.getemployeeName(),employeee.getemployeeRole());
				
			}
			
		}
		
		if( isReporteePresent == false)
		{
			Utils.printSpace();
			System.out.println(" 	  No Reportees !!!");
			Utils.printSpace();
		}
		
	}
	
	
	public static void viewTeamList()
	{
		
		int chceker = 0;
		if(!EmployeeValidation.isTeamsAvailable())
		{
			Utils.printSpace();
			System.out.println("  No Team is Available. ");
			Utils.printSpace();
			return;
		}
		
		try
		{
			
			System.out.println(" Enter Team ID : ");
			EmployeeManager.listTeamName();
			int teamID = Utils.getIntInput();
			Utils.printSpace();
			boolean isTeamPresent = EmployeeValidation.isTeamIDAlreadyExists(teamID);
			
			if(isTeamPresent == true)
			{
				String teamName = Utils.getTeamName(teamID);
				
				System.out.println("  "+teamName+"  Team Info :");
				Utils.printLine();
				System.out.println("   Employee ID       Name         Role");
				Utils.printLine();
				
				for (Employee employee : Resource.employees) 
				{
					if (employee.getEmployeeTeamName().equals(teamName))
					{
						chceker = 1;
						System.out.printf("        %-10s   %-10s   %-10s\n", employee.getemployeeID(), employee.getemployeeName(), employee.getemployeeRole());
						
					}
				}
				
				if(chceker == 0)
				{
					Utils.printSpace();
					System.out.println("       * * *   No Employees   * * *");
				}
				
				Utils.printSpace();
				Utils.printSpace();
				
			}
			else
			{
				Utils.printSpace();
				System.out.println("   Please, enter  a valid Team ID only..!");
				Utils.printSpace();
				viewTeamList();
				return;
			}
		
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			viewTeamList();
			return;
		}
	}
	
	

	


} // class ends
