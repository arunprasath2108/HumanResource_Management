package employee_Management;


import java.util.InputMismatchException;
import model.Employee;
import utils.Resource;
import utils.Utils;
import utils.EnumClass.PreferedLocation;
import utils.EnumClass.Role;
import validation.EmployeeValidation;

public class HumanResource implements EmployeeFeatures
{
	private static final int ADD_NEW_EMPLOYEE = 1;
	private static final int EDIT_EMPLOYEE_DETAILS = 2;
	private static final int ADD_NEW_TEAM = 3;
	private static final int VIEW_TEAM_INFO = 4;
	private static final int INBOX = 5;
	private static final int LOG_OUT = 6;
	
	private static final int CONFIRM = 1;
	private static final int BACK = 2;
	
	private static final int EDIT_TEAM_NAME = 1;
	private static final int EDIT_ROLE = 2;
	private static final int EDIT_REPORTING_ID = 3;
	private static final int EDIT_LOCATION = 4;
	private static final int BACK_MENU = 5;
	
	private static final int PROCESS_REQUEST = 1;
	private static final int IGNORE_MESSAGE = 2;

	//gender input
	private static final int MALE = 1;
	private static final int FEMALE = 2;
	private static final int OTHERS = 3;
	
	
	static int teamID;
	//Employee *loggedinHr;
	
	public  void listEmployeeMenu(Employee employeee)
	{
		
			displayHrMenu(employeee);
			
			boolean isAnyMethodCalled = getInputFromHR(employeee);
			
			if( isAnyMethodCalled == true)
			{
				return;
			}
			else
			{
				
				listEmployeeMenu(employeee);
			}
			 
	}
	
	
	public void requestMessages(Employee employee) 
	{
		
		int senderID = 0, requestedID = 0;
		String msg = "";
		String requestByName;
		
		if(employee.getInbox().isEmpty() == true)
		{
			Utils.printSpace();
			System.out.println("  ~ No Requests.");
			Utils.printSpace();
			return;
		}
		
		Utils.printSpace();
		System.out.println("  EMPLOYEE REQUESTS : ");
		Utils.printLine();
		
		//printing message received from others.
		for( int messageCount = 0; messageCount<employee.getInbox().size(); messageCount++)
		{
			
			String[] splitMessage = employee.getInbox().get(messageCount).split("-");
			 senderID = Integer.parseInt(splitMessage[0]); 
			 requestedID = Integer.parseInt(splitMessage[2]);
			String senderName = Utils.getEmployeeName(senderID);  
			requestByName = Utils.getEmployeeName(requestedID);  
			msg = splitMessage[1];		
			String newTeam = splitMessage[3];
			String dateTime = splitMessage[4];
		
			messageCount++;
			System.out.println("  "+messageCount+" - "+senderName+"           "+dateTime);
			messageCount--;
			
			Utils.printSpace();
			System.out.println("      ~ "+msg+"  Employee ID : "+requestByName+"   TEAM : "+newTeam);
			Utils.printSpace();
			
		}
		
		Utils.printSpace();
		processInbox(employee);
		
	}
	
	
	private void editEmployeeDetails()
	{
		
		Employee getLastIndex = Resource.employees.get(Resource.employees.size()-1);
		
		if(getLastIndex.getemployeeID() <= 2)
		{
			System.out.println("  ~ No Employee Available to Edit.");
			Utils.printSpace();
			return;
		}
		
		System.out.println("Enter User ID : ");
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			if(userInput == 1)
			{
				System.out.println(" ~ Can't edit CEO Details.");
				Utils.printSpace();
				return;
			}
			
			if(userInput == 2)
			{
				System.out.println(" ~ You can't edit your details without permissions.");
				Utils.printSpace();
				return;
			}
	
				
			if(EmployeeValidation.isEmployeePresent(userInput) == true)
			{
					for(Employee employee : Resource.employees)
					{
						if(employee.getemployeeID() == userInput)
						{
							processEdit(employee);
							break;
						}
					
					}
				
			}
			else
			{
				Utils.printSpace();
				System.out.println(" Invalid Employee ID");
				return;
			}
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.printSpace();
			Utils.scanner.nextLine();
			return;
			
		}
		
		
	}

	
	private void processEdit(Employee employee)
	{
		
		EmployeeManager.displayProfile(employee);
		System.out.println(" 1. Confirm User profile before Edit");
		System.out.println(" 2. Back");
		Utils.printSpace();
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
			
				case CONFIRM :
					displayEditOption(employee);
					break;
					
				case BACK :
					break;
					
				default :
					Utils.printInvalidInputMessage();
					Utils.printSpace();
					processEdit(employee);
					return;
			}
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.printSpace();
			Utils.scanner.nextLine();
			processEdit(employee);
			return;
			
		}
		
		
	}


	private void displayEditOption(Employee employee) 
	{
		
		Utils.printSpace();
		System.out.println(" Choose an Option : ");
		Utils.printLine();
		System.out.println(" 1. Change Team.");
		System.out.println(" 2. Change Role.");
		System.out.println(" 3. Edit Reporting to");
		System.out.println(" 4. Edit Work Location");
		System.out.println(" 5. Back to Menu");
		Utils.printSpace();
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			switch(userInput)
			{
			
			case EDIT_TEAM_NAME :
				editTeamName(employee);		
				break;
				
			case EDIT_ROLE :
				editRole(employee);			
				break;
				
			case EDIT_REPORTING_ID :
				editReportingID(employee);	
				EmployeeManager.displayProfile(employee);
				displayEditOption(employee);
				break;
				
			case EDIT_LOCATION :
				editLocation(employee);     
				break;
				
			case BACK_MENU :
				break;
				
			}
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.printSpace();
			Utils.scanner.nextLine();
			displayEditOption(employee);
			return;
			
		}

		
	}


	private void editReportingID(Employee employee)
	{
		
		String reportingTo = displayReportingToEmployees(employee.getEmployeeTeamName(), employee.getemployeeRole());
		Utils.printSpace();
		
		if(reportingTo.equalsIgnoreCase(null))
		{
			
				Utils.printLine();
				System.out.println(" Currently No Employee above your role are Available.");
		}
		else
		{
			employee.setReportingTo(reportingTo);
			employee.setReportingToID(Utils.getEmployeeID(reportingTo));
			
			if(reportingTo.equals(Resource.employees.get(0).getemployeeName()))
			{
				System.out.println("  ~ \"Manager\" Reporting to automatically set as - CEO. ");
	
			}
			else
			{
				System.out.println("  ~ Reporting to changed Successful. ");
			}
		}
		Utils.printSpace();
		
	}


	private void editLocation(Employee employee) 
	{
		
		String location = displayPreferedLocation(employee.getEmployeeWorkLocation());
		Utils.printSpace();
		employee.setEmployeeWorkLocation(location);
		System.out.println("  ~ Work Location Changed Successful.");
		Utils.printSpace();
		EmployeeManager.displayProfile(employee);
		displayEditOption(employee);
		return;
		
	}


	private void editRole(Employee employee)
	{
		
		
		Role role = displayEmployeeRoles(employee.getemployeeRole());

		if( role.equals(Role.MANAGER))
		{
			employee.setemployeeRole(role);
			employee.setReportingTo(Resource.employees.get(0).getemployeeName());
			employee.setReportingToID(Resource.employees.get(0).getemployeeID()); 
			EmployeeManager.displayProfile(employee);
			displayEditOption(employee);
			return;
		}
		
		if( role.getValue() == employee.getemployeeRole().getValue())
		{
			displayEditOption(employee);
			return;
		}
		
		if( employee.getemployeeRole().getValue() > role.getValue())
		{
			employee.setemployeeRole(role); 
			editReportingID(employee);
			System.out.println("   ~ Role changed Successful. ");
			Utils.printSpace();
			EmployeeManager.displayProfile(employee);
			displayEditOption(employee);
			return;
		}
		else
		{
			System.out.println(" please, Choose Role above the previous position");
			Utils.printSpace();
			displayEmployeeRoles(employee.getemployeeRole());
			return;
		}
		
		
	}


	private void editTeamName(Employee employee)
	{
		int teamID = 0;
		if(employee.getemployeeRole().name().equals(Role.MANAGER.name()))
		{
			
			Utils.printSpace();
			System.out.println(" Without CEO Permissions, you can't change Team ..!");
			Utils.printSpace();
			Utils.printSpace();
			displayEditOption(employee);
			return;
		}
		try
		{
			if(EmployeeManager.listTeamName(employee.getEmployeeTeamName()) == true)
			{
				System.out.println(" Enter Team ID from the List : ");
				teamID = Utils.getIntInput();
				Utils.printSpace();
				
				if( EmployeeValidation.isTeamIDAlreadyExists(teamID))
				{
					String teamName = Utils.getTeamName(teamID);
					
					if(teamName.equalsIgnoreCase(employee.getEmployeeTeamName()))
					{
						Utils.printSpace();
						System.out.println(" You are already in "+teamName+" team.");
						Utils.printSpace();
						editTeamName(employee);
						return;
					}
					else
					{
						employee.setEmployeeTeamName(teamName);
						editReportingID(employee);
						System.out.println("  ~ Team Name changed Successful");
						Utils.printSpace();
						EmployeeManager.displayProfile(employee);
						displayEditOption(employee);
						return;
					}
				}
				else
				{
					Utils.printSpace();
					System.out.println(" ~ No such team is Available");
					Utils.printSpace();
					displayEditOption(employee);
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
				editTeamName(employee);
		}
		
		
	}
	
	
	//overloaded method for editing team name from request.
	private void editTeamName(int senderID, String newTeam, Employee hr)
	{
		
		Utils.printSpace();
		Employee employee = Utils.getEmployeeObject(senderID);
		employee.setEmployeeTeamName(newTeam);
		Utils.printSpace();
		System.out.println("   	 Reporting to ~ must be in the same team ");
		Utils.printSpace();
		editReportingID(employee);
		Utils.printSpace();
	}
	

	
	private  void addEmployee()
	{
		
			int teamID = 0;
			try
			{		

					if (EmployeeValidation.isTeamsAvailable())
					{
						EmployeeManager.listTeamName();
						System.out.println(" Enter Team ID from the List : ");
						teamID = Utils.getIntInput();
						Utils.printSpace();
						boolean isTeamPresent = EmployeeValidation.isTeamIDAlreadyExists(teamID);
						
							if (isTeamPresent == true)
							{
								String teamName = Utils.getTeamName(teamID);
								getEmployeeDetails(teamName);
							}
							else 
							{
								Utils.printSpace();
								System.out.println(" Sorry! No such team is Available.");
								Utils.printSpace();
								addEmployee();
								Utils.printSpace();
								return;
							}	
				
			    	}
					else
					{
						System.out.println(" Team List is Empty.");
						Utils.printLine();
						Utils.printSpace();
					}
			}
			catch(InputMismatchException e)
			{
				Utils.printSpace();
				System.out.println(" Enter team ID only.");
				Utils.printSpace();
				Utils.scanner.nextLine();
				addEmployee();
				return;
			}
		
	}
	
	
	private void getEmployeeDetails(String teamName) 
	{
		
		
		Role role = displayEmployeeRoles();
		Utils.printSpace();
		
		String name = getNameInput();
		Utils.printSpace();
		
		String gender = getGenderInput();
		Utils.printSpace();
		
		String reportingToName = displayReportingToEmployees(teamName, role);
		
		int reportingToID = displayReportingToID(teamName, reportingToName);
		Utils.printSpace();
		
		String doj = getDateInput();
		Utils.printSpace();
		
		String location = displayPreferedLocation();
		Utils.printSpace();
		
		Employee latestEmployee = Resource.employees.get(Resource.employees.size()-1);
		int employeeID = latestEmployee.getemployeeID();
		
		boolean isPresent = EmployeeValidation.isEmployeePresent(++employeeID);
		
		String nameAfterTrim = getNameWithoutSpace(name);
		
		String newMail = nameAfterTrim.toLowerCase()+"@zoho.in";
		
		boolean isMailExist = EmployeeValidation.isOfficialMailExists(newMail);
		Utils.printSpace();
		
		if(isMailExist == true)
		{
			newMail = "";
			System.out.println("  Mail Id Already Exists  -->  " +name+"@zoho.in");
			Utils.printSpace();

			newMail = getEmailID();
		}
		
		if (isPresent == true)
		{
			System.out.println(" User ID " + employeeID + " Already Exists.");
			Utils.printSpace();
		} 
		else 
		{
			Employee employee = new Employee(employeeID, name, role, teamName, reportingToName, reportingToID, location, doj, newMail, gender);
			Resource.employees.add(employee);
			
			Resource.officialMail.add(newMail);
			
			Utils.printSpace();
			System.out.println("   ~ Employee added Successful ~ ");
			Utils.printSpace();
			EmployeeManager.displayProfile(employee);
		} 
		
	}


	private String getNameWithoutSpace(String name) 
	{
		
		String[] names = name.split("\s");
		
		StringBuilder userName = new StringBuilder();
		
		for( String nameSplit : names)
		{
			userName.append(nameSplit);
		}
		
		char[] trimName = userName.toString().toCharArray();
		int lastIndex = trimName.length-1;
		
		if(trimName[lastIndex] == '.')
		{
			userName.deleteCharAt(lastIndex);
		}
		return userName.toString();
	}


	private  Role displayEmployeeRoles() 
	{
		
		Role[] roles = Role.values();
		
		System.out.println(" Select User Role : ");
		Utils.printLine();
		int toCheck = 0;

		for (Role role : roles)
		{
			if(Role.CEO != role && Role.HR != role)
			{
				System.out.println(" "+role.getValue() + " - " + role);
				
			}
		}

		Utils.printSpace();
		System.out.println(" Choose a Role :");
		
		try 
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			for (Role role : roles)
			{
				if (userInput == role.getValue() && userInput != Role.CEO.getValue() && userInput != Role.HR.getValue()) 
				{
					toCheck = 1;
					return role;
				}
			} 
		} 
		catch (InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return displayEmployeeRoles();
		}
		
		if (toCheck == 0) 
		{
			System.out.println(" Choose in this Options.");
			Utils.printSpace();
			return displayEmployeeRoles();
		}
		
		return null;

	}
	
	
	private Role displayEmployeeRoles(Role r) 
	{
		
		Role[] roles = Role.values();
		System.out.println(" Select role : ");
		Utils.printLine();
		int toCheck = 0, noAboveRole = 0;

		for (Role role : roles)
		{
			if(Role.CEO != role && Role.HR != role && r.getValue() > role.getValue())
			{
				noAboveRole = 1;
				System.out.println(role.getValue() + " - " + role);
			}
		}

		Utils.printSpace();
		Utils.printSpace();
		
		if(noAboveRole == 0)
		{
			System.out.println("   ~ \"MANAGER\" - role is the highest position.");
			Utils.printSpace();
			return r;
		}
		
		System.out.println(" Choose the Role :");
		
		try 
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			
			for (Role role : roles)
			{
				if (userInput == role.getValue() && r.getValue() > role.getValue() && userInput != Role.HR.getValue() && userInput != Role.HR.getValue())   //&& r.getValue() > role.getValue()
				{
					toCheck = 1;
					return role;
				}
			} 
		} 
		catch (InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return displayEmployeeRoles(r);
		}
		
		if (toCheck == 0) 
		{
			System.out.println(" Choose in this Options.");
			Utils.printSpace();
			return displayEmployeeRoles(r);
		}
		return null;

	}
	
	
	private String displayPreferedLocation()
	{
		
		int checkLocationChanged = 0;
		System.out.println(" Enter User Work location : ");
		Utils.printSpace();

		PreferedLocation[]  location = PreferedLocation.values();
		
		for( PreferedLocation  places : location )
		{
			System.out.println(" "+places.getValue()+" - "+places);
			
		}
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			for( PreferedLocation  places : location )
			{
				checkLocationChanged = 1;
				if( places.getValue() == userInput)
				{
					
					return places.toString();
				}
			}
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return displayPreferedLocation();
		}
		
		if( checkLocationChanged == 1)
		{
			Utils.printInvalidInputMessage();
			return displayPreferedLocation();
		}
		return null;
	}
	
	
	//overloaded method for changing the work location
	private String displayPreferedLocation(String currentLocation)
	{
		int checkLocationChanged = 0;
		System.out.println(" Enter User Work locationnn : ");
		Utils.printSpace();
		
		PreferedLocation[]  location = PreferedLocation.values();

		for( PreferedLocation  places : location )
		{
			
			if( places.name().equalsIgnoreCase(currentLocation))
			{
				continue;
			}
			
			else
			{
				System.out.println(" "+places.getValue()+" - "+places);
			}
			
		}
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			for( PreferedLocation  places : location )
			{
				checkLocationChanged = 1;
				if( places.getValue() == userInput && places.name().equalsIgnoreCase(currentLocation) == false)
				{
					
					return places.toString();
				}
			}
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			return displayPreferedLocation(currentLocation);
		}
		
		if( checkLocationChanged == 1)
		{
			Utils.printInvalidInputMessage();
			return displayPreferedLocation(currentLocation);
		}
		return null;
	}
	
	
	private String displayReportingToEmployees(String teamName, Role role)
	{
		
		int ReportingTochecker = 0;
		boolean noEmployeeInTeam = false;
		
		
		if(role.name().equalsIgnoreCase(Role.MANAGER.name()))
		{
			return Resource.employees.get(0).getemployeeName();
		}
		
		System.out.println("Choose Reporting to : ");
		Utils.printSpace();
		System.out.println(" Employee ID	Name	  Role	");
		Utils.printLine();
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getEmployeeTeamName().equals(teamName))
			{

				if(employee.getemployeeRole().getValue() < role.getValue())
				{
					noEmployeeInTeam = true;
					System.out.println("      "+employee.getemployeeID()+"  	"+employee.getemployeeName()+"       "+employee.getemployeeRole());
					
				}
				
			}
		}
		
		if(noEmployeeInTeam == false)
		{
			Utils.printSpace();
			System.out.println("    *  No Employee above the Role prefered are not available * ");
			Utils.printSpace();
			//System.out.println( "  * No Employee above the ROLE prefered are available.");
			System.out.println( "  * Temporary Reporting to, set as ~ CEO");
			Utils.printSpace();
			Utils.printSpace();
			return Resource.employees.get(0).getemployeeName();
		}
		
		Utils.printSpace();
		System.out.println("Enter Employee ID : ");
		Utils.printSpace();
		
		try
		{
			
			int userID = Utils.getIntInput();
			Utils.printSpace();
			
			for(Employee employee : Resource.employees)
			{
				if(employee.getemployeeID() == userID && employee.getEmployeeTeamName().equals(teamName) && employee.getemployeeRole().getValue() < role.getValue())
				{
					ReportingTochecker = 1;
					return employee.getemployeeName();
				}
			}
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.printSpace();
			Utils.scanner.nextLine();
			return displayReportingToEmployees(teamName, role);
		}
		
		if( ReportingTochecker == 0)
		{
			Utils.printSpace();
			String reportName = displayReportingToEmployees(teamName, role);
			return reportName;
			
		}
		
		return null;
	}
	
	
	private int displayReportingToID(String teamName, String reportingTo)
	{
		
		if(reportingTo.equalsIgnoreCase(Resource.employees.get(0).getemployeeName()))
		{
			return Resource.employees.get(0).getemployeeID();
		}
		
		for(Employee employee : Resource.employees)
		{
			if(employee.getemployeeName().equalsIgnoreCase(reportingTo) && employee.getEmployeeTeamName().equalsIgnoreCase(teamName))
			{
				return employee.getemployeeID();
			}
		}
		return 0;
	}
	
	
	private void addTeam() 
	{
		
		//default teamID, if no team is present
		teamID = 110;
		Utils.printSpace();
		System.out.println(" Enter team name : ");
		String teamName = Utils.getStringInput();
		Utils.printSpace();
		boolean isTeamExists = EmployeeValidation.isTeamAlreadyExists(teamName);
		
		if( isTeamExists == true )
		{
			Utils.printSpace();
			System.out.println(" Team Name Already Exists.");
			System.out.println(" Choose a Unique Name.");
			addTeam();
		}
		
		if(isTeamExists == false )
		{
			if(Resource.teamMap.isEmpty())
			{
				
				Resource.teamMap.put(++teamID, teamName.toUpperCase());
				Utils.printTeamAddedSuccessful();
			}
			else
			{
				teamID = Resource.teamMap.lastKey();
				Resource.teamMap.put(++teamID, teamName.toUpperCase());
				Utils.printTeamAddedSuccessful();
			}
		}
	}

	
	private boolean getInputFromHR(Employee employee)
	{
		 System.out.println("Choose an Option : ");
		 
		 try
		 {
			 int userInput = Utils.getIntInput();
			 Utils.printSpace();
			 
			 switch(userInput)
			 {
			 
			 	case ADD_NEW_EMPLOYEE :
			 		addEmployee();   
			 		return false;
			 		
			 	case EDIT_EMPLOYEE_DETAILS :
			 		editEmployeeDetails();						
			 		return false;
			 		
			 	case ADD_NEW_TEAM :
			 		addTeam();		
			 		return false;
			 				 		
			 	case VIEW_TEAM_INFO :
			 		SeniorEmployee.viewTeamList();					
			 		return false;
			 			 		
			 	case INBOX :
			 		requestMessages(employee);
			 		return false;
			 		
			 	case LOG_OUT :
			 		Utils.printLogOutMessage();
			 		return true;
			 		
			 	default :
			 		Utils.printInvalidInputMessage();
				 	return false;
				 				 	
			 }	//switch ends
		 }
		 catch(InputMismatchException e)
		 {
				 Utils.printInvalidInputMessage();
				 Utils.printSpace();
				 Utils.scanner.nextLine();
				 listEmployeeMenu(employee);

		 }
		 return true;
 
	} 

	
	private  void processInbox(Employee employee)
	{
		int requestedID, checker = 0, senderID;
		System.out.println(" Choose Index number.");
		Utils.printSpace();
		
		try
		{
			
			int userInput = Utils.getIntInput();
			userInput--;
			Utils.printSpace();
			
			for( String messages : employee.getInbox())
			{
				
				if(messages.indexOf(messages) == userInput)
				{
					checker = 1;
					String[] splitMessage = messages.split("-");
					senderID = Integer.parseInt(splitMessage[0]); 
					requestedID = Integer.parseInt(splitMessage[2]);
					 
					proceedMessage(employee, userInput, requestedID, senderID, messages );
					break;
				}
			}
			
			if(checker == 0)
			{
				System.out.println(" Please, Enter a Valid Index Number. ");
				Utils.printSpace();
				processInbox(employee);
				return;
				
			}
			
		}
		catch(InputMismatchException e)
		{
			
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			processInbox(employee);
			return;
			
		}
	}


	private  void proceedMessage(Employee employee, int indexOfMessage, int requestID, int senderId, String message) 
	{
		
		try
		{
			System.out.println(" Choose a option :");
			Utils.printSpace();
			System.out.println(" 1. Change Team");
			System.out.println(" 2. Back.");
			Utils.printSpace();
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			switch(userInput)
			{
				case PROCESS_REQUEST :
					processRequest(employee, message, requestID, senderId, indexOfMessage );
					break;
					
				case IGNORE_MESSAGE :
					break;
					
					default :
						Utils.printInvalidInputMessage();
						requestMessages(employee);
						return;
			}
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			requestMessages(employee);
			return;
		}	
		
	}

	
	private void processRequest(Employee employee, String message, int requestID, int senderId , int indexOfMessage )
	{
		
		String[] getTeamName = message.split("-");
		String teamName = getTeamName[3];
		editTeamName(requestID, teamName, employee);
		employee.getInbox().remove(indexOfMessage);
		
		//get sender employee object
		Employee sender = Utils.getEmployeeObject(senderId);
		//get requester employee object
		Employee requestBy = Utils.getEmployeeObject(requestID);
		String requesterName = Utils.getEmployeeName(requestID);
		
		sender.setNotificationSeen(false);
		String messageSender = " ~ Team changed Successfully for \""+requesterName.toUpperCase()+"\"    "+Utils.getCurrentDateTime();
		sender.getNotification().replace(requestBy.getemployeeID(), messageSender );
		
		requestBy.setNotificationSeen(false);
		String messageRequester = " ~ Your Team changed Successfully to ["+teamName+"]       "+Utils.getCurrentDateTime();
		requestBy.getNotification().replace(requestBy.getemployeeID(), messageRequester);
		requestBy.setTeamChanged(false);
		
	}


	private void displayHrMenu(Employee employee)
	{

		 System.out.println(" Features : ");
		 Utils.printLine();
		 System.out.println(" 1. Add Employee");  
		 System.out.println(" 2. Edit Employee Info");  
		 System.out.println(" 3. Add Team ");  
		 System.out.println(" 4. View Team Info");
		 System.out.print(" 5. Employee Requests. ");
		 
		 if(EmployeeValidation.isRequestsEmpty(employee) == false)
		 {
			 int messageCount = Utils.printmessageCount(employee);
			 System.out.print(" ~ ["+messageCount+"] Unread Messages");
		 }
		 
		 Utils.printSpace();
		 Utils.printSpace();
		 System.out.println(" 6. Logout.");
		 Utils.printLine();
		 Utils.printSpace();

	}

	
	private String getNameInput()
	{
		
			Utils.printSpace();
			System.out.println(" Enter User Name : ");
			Utils.printSpace();
			System.out.println("   * Name should be minimum of 3 characters.");
			Utils.printSpace();
			System.out.println("   * It should not have Numberic values and do not ends with [.] period ");
			Utils.printSpace();
			
			
			String name = Utils.getStringInput();
			Utils.printSpace();
			
			if(EmployeeValidation.isNameValid(name))
			{
				return name;
			}
			else
			{
				Utils.printSpace();
				System.out.println(" Invalid Name Input. ");
				return getNameInput();
			}
		
	}
	
	
	private String getGenderInput() 
	{
		
		String gender;
		Utils.printSpace();
		System.out.println(" Choose Gender :");
		Utils.printSpace();
		System.out.println(" 1. MALE.");
		System.out.println(" 2. FEMALE.");
		System.out.println(" 3. Others.");
		
		try
		{
			int userInput = Utils.getIntInput();
			Utils.printSpace();
			switch(userInput)
			{
				case MALE :
					gender = "MALE";
					break;
					
				case FEMALE :
					gender = "FEMALE";
					break;
					
				case OTHERS :
					gender = "OTHERS";
					break;
					
				default :
					Utils.printInvalidInputMessage();
					return null;
			}
			
		}
		catch(InputMismatchException e)
		{
			Utils.printInvalidInputMessage();
			Utils.scanner.nextLine();
			gender = getGenderInput();
		}
		return gender;
		
	}
	
	
	private String getDateInput()
	{
		
		String todayDate = Utils.getTodayDate();
		
		System.out.println(" Enter Employee Date of Joining :  [ dd/mm/yyyy ] ");
		Utils.printSpace();
		System.out.println("  * you can Choose Date Of Joining from a week before "+todayDate);
		Utils.printSpace();
		String joiningDate = Utils.getStringInput();
		Utils.printSpace();
		
		if( EmployeeValidation.isDateValid(joiningDate, todayDate) == true)
		{
			return joiningDate;
		}
		else
		{
			Utils.printSpace();
			System.out.println(" Please, Enter a Valid Date. ");
			Utils.printSpace();
			return getDateInput();
		}	
		
	}

	
	private String getEmailID()
	{
		
		System.out.println("  * Add some Characters [a-z] or Digits [0-9] or Special Characters [-.&$_] ");
		Utils.printSpace();
		System.out.println(" Please enter USERNAME only. Domain Name will be automatically generated.");
		Utils.printSpace();
		String mail = Utils.getStringInput()+"@zoho.in";
		Utils.printSpace();
		
		if( EmployeeValidation.isEmailValid(mail) == true && EmployeeValidation.isOfficialMailExists(mail) == false)
		{
			return mail;
		}
		else
		{
			Utils.printSpace();
			System.out.println(" Invalid User Name.");
			Utils.printSpace();
			return getEmailID();
		}
		
	}
	
	
	
} // class end.
