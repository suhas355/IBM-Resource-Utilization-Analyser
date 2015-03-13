package resource.analyze;

import java.sql.*;
public class Disk 
{
	String time;
	String clientId;
	int disks;
	int reads;
	int partition;
	int writes;
	String status;
	String message=" ";
	
	Statement st;
	Connection con;
	
	public Disk()
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
		ResultSet rs = st.executeQuery("select first 1 * from disk order by time desc");
		rs.next();
		time=String.valueOf(rs.getTime(1));
		clientId=rs.getString(2);
		disks = rs.getInt(3);
		reads = rs.getInt(4);
		partition = rs.getInt(5);
		writes = rs.getInt(6);
		
//*************     To calculate disk Utilization        ***********
		float readFactor,writeFactor;
		readFactor = (reads/disks)*100;
		writeFactor = (writes/disks)*100;
		if(readFactor > 80)
		{
			status="high";
			message+="Reads exceeds threshold";
			
		}
		if(writeFactor > 80)
		{
			status="high";
			message+="Writes exceeds threshold";
			
		}
		if(readFactor <20 && writeFactor<20)
		{
			status="low";
			message+="Low disk usage";
		}
		else if(readFactor>=20 && readFactor<80 && writeFactor >=20 && writeFactor<80)
		{
			status="normal";
			message+="Disk usage is normal";
		}
		
		rs.close();
		}
		catch(Exception e)
		{
		System.out.println(e);
		System.exit(0);
		}
		String value= ("Disk Information \n Time = " + time +"\nClient ID = "+ clientId + "\nDisks = "+disks +
						"\nReads = "+ reads +"\n Partition = "+ partition + 
						"\n Writes = " + writes + "\n Status = " + status);
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
	


