package resource.analyze;

import java.sql.*;
public class Network 
{
	String time;
	String clientId;
	int totalReceived;
	int forwarded;
	int inputDiscarded;
	int inputDelivered;
	int outputRequests;
	String status;
	String message=" ";
	Statement st;
	Connection con;
	
	public Network()
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
		ResultSet rs = st.executeQuery("select first 1 * from Network order by time desc");
		rs.next();
		time=String.valueOf(rs.getTime(1));
		clientId=rs.getString(2);
		totalReceived = rs.getInt(3);
		forwarded = rs.getInt(4);
		inputDiscarded = rs.getInt(5);
		inputDelivered = rs.getInt(6);
		outputRequests = rs.getInt(7);
	//**********    To calculate network utilization     *************
		if(inputDiscarded> totalReceived/2)
		{
			status="high";
			message+="Network failure due to more discarding of requests";
		}
		if(inputDelivered < forwarded/2)
		{
			status="high";
			message="Network failure due to low delivery rate";
		}
		else if(outputRequests == 0)
		{
			status="low";
			message += "Network utilization is low";
		}
		else
		{
			status="normal";
			message+="Network utilizaton is normal";
		}
		
		rs.close();
		}
		catch(Exception e)
		{
		System.out.println(e);
		System.exit(0);
		}
		String value= ("Network Information \n Time = " + time +"\nClient ID = "+ clientId + "\ntotalReceived = "+totalReceived +
						"\nReads = "+ forwarded +"\n inputDiscarded = "+ inputDiscarded + 
						"\n inputDelivered = " + inputDelivered + "\nOutput Requests  " + outputRequests +"\n Status = " + status);
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
	


