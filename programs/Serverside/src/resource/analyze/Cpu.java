package resource.analyze;

import java.sql.*;

public class Cpu 
{
	String time;
	String clientId;
	float usr;
	float sys;
	float iowait;
	float idle;
	String status;
	String message=" ";
	float cpuUtilization;
	
	Statement st;
	Connection con;
	
	public Cpu()
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
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public String calculate()
	{
		
		try
		{
		st = con.createStatement();
		ResultSet rs = st.executeQuery("select first 1 * from cpu order by time desc");
		rs.next();
		time=String.valueOf(rs.getTime(1));
		clientId=rs.getString(2);
		usr = rs.getFloat(3);
		sys = rs.getFloat(4);
		iowait = rs.getFloat(5);
		idle = rs.getFloat(6);
		
		//To calculate utilization
		cpuUtilization = 1- idle;
		if(cpuUtilization < 20)
		{
			status = "low";
		    message="cpu util is low";
		}
		else if(cpuUtilization >=20 && cpuUtilization <80)
		{
			status = "normal";
			message="cpu util is normal ";
		}
		else
			status= "high";
		
		//To determine parameter causing status to be high
		
		if(status.equals("high"))
		{
			if(usr > sys)
				message="User processes are large in number with %"+ usr;
			else
				message="System processes are large in number with %"+ sys;
		}
		if(iowait > 60)
			message += " Imbalance process mixture with iowait %"+ iowait;
		
		//to insert status values into status database
		
		
		rs.close();
		}
		catch(Exception e)
		{
		System.out.println(e);
		System.exit(0);
		}
		String value= ("Cpu Information \n Time = " + time +"\nClient ID = "+ clientId + "\n% Usr = "+usr +
						"\n% Sys = "+ sys +"\n % Iowait = "+ iowait + 
						"\n% Idle = " + idle + "\n Status = " + status);
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

	public String getClientId() {
		return clientId;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	
	}
	


