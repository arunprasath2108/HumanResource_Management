package Utils;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import Model.Employee;


public class Resource
{
	
	//for storing all employees details.
	public static ArrayList<Employee> employees = new ArrayList<>();
	
	
	//for mapping team ID with team Name
	public static SortedMap<Integer, String> teamMap = new TreeMap<>(); 
	
	
	//for MAIL ID generation and checking for duplicate ID's
	public static ArrayList<String> officialMail = new ArrayList<>();
	

}
