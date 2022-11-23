package Utils;

public class EnumClass 
{
	
	
	//Enum for Role
	
	public enum Role 
	{
		CEO(), HR(), MANAGER(1), LEAD(2), MTS(3), PT(4);
		
			private int value;
			
			//constructor for no parameter value
			 Role() {}
			 
			private Role(int value)
			{
			    this.value = value;
			}	
		 
			public int getValue() 
			 {
			    return value;
			 }
			 
	}
	
	
	//Enum for  work location
	
	public enum PreferedLocation
	{
		CHENNAI(1), COIMBATORE(2), BANGALORE(3);
		
		private int value;
		
		private PreferedLocation(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
		
		
	}

}
