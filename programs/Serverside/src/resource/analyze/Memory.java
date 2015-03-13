package resource.analyze;

import java.sql.*;
public class Memory 
{
	String time;
	String clientId;
	int totalMemory;
	int usedMemory;
	int activeMemory;
	int inactiveMemory;
	int freeMemory;
	int bufferMemory;
	int swapMemory;
	float memoryUtilization;
	String status;
	String message=" ";
	
	Statement st;
	Connection con;
	
	public Memory()
	{
		String url = "jdbc:informix-sqli://192.168.1.65:9088/resource:INFORMIXSERVER=ol_informix1170;user=informix;password=informix";
		try
		{
		Class.forName("com.informix.jdbc.IfxDriver");
		 
		}
		catch( Exception e )
		{	
		 System.out.println("Unable to make connection\n" + e);
		 System.exit(0);	
		}
		try
		{
			con = DriverManager.getConnection(url);
			 st = con.createStatement();
			// System.out.println("Connection to database successfully");
			 
		}
		catch(Exception e)
		{
			System.out.println("Problem with url.." + e);
			//e.printStackTrace();
			System.exit(0);
		}
	}
	
	public String calculate()
	{
		try
		{
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select first 1 * from memory order by time desc");
			rs.next();
			
				time=String.valueOf(rs.getTime(1));
				clientId=rs.getString(2);
				totalMemory = rs.getInt(3);
				usedMemory = rs.getInt(4);
				activeMemory = rs.getInt(5);
				inactiveMemory = rs.getInt(6);
				freeMemory = rs.getInt(7);
				bufferMemory = rs.getInt(8);
			
//************			To calculate memory Utilization	       **************
			memoryUtilization = (usedMemory/totalMemory)*100;
			if(memoryUtilization<20)
			{
				status="low";
				message+="Memory utilization is low";
			}
			else if(memoryUtilization >=20 && memoryUtilization <80)
			{
				status = "normal";
				message+=" memory consumption is normal";
			}
			else
			{
				status="high";
				if(activeMemory/usedMemory > 0.80)
					message+="Active memory consumption is High";
				if(usedMemory/usedMemory > 0.80)
					message+="Used memory consumption is High";
				if(bufferMemory/usedMemory > 0.80)
					message+="Buffered memory consumption is High";
								
			}			
			
			
			rs.close();
		}
		catch(Exception e)
		{
		System.out.println(e);

		System.exit(0);
		}
		String value= ("Memory Information \n Time = " + time +"\nClient ID = "+ clientId + "\nTotal Memory = "+totalMemory +
						"\nUsed Memory = "+ usedMemory +"\n Active Memory = "+ activeMemory + 
						"\nInactive Memory = " + inactiveMemory + "\n Free memory = " + freeMemory +
						"\n Buffer Memory = " + bufferMemory + "\n Status = " + status);
		return value;
		
	}
	
	protected void finalize() throws Throwable 
	{
		super.finalize();
		try {
			st.close();
			con.close();	
			}
			catch( Exception ex) 
			{ 
			System.out.println(ex);	
			}
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}	
	}
	


