import resource.analyze.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.*;

public class Server 
{
	String clientId;
	Memory m ;	
	Cpu c;
	Disk d;
	Network n;
	@SuppressWarnings("deprecation")
	void update()
	{
		Connection con;
		Statement st;
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
			 
			 clientId=c.getClientId();
			 clientId=clientId.trim();
			 Date dt = new Date();
			 String message =c.getMessage()+"-"+m.getMessage()+"-"+d.getMessage()+"-"+n.getMessage();
			// System.out.println(message);
			 
			 
			 Calendar cal = Calendar.getInstance();
			 
			 int day = cal.get(Calendar.DATE);
			 
			 int month = cal.get(Calendar.MONTH) + 1;
			 
			 int year = cal.get(Calendar.YEAR);
			 
			 String dtTime ="'"+year+"-"+"0"+month+"-"+day+" "+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds()+"'";
			 String stmt="insert into status values("+dtTime+",'"+clientId+"','"+c.getStatus()+"','"+m.getStatus()+"','"+d.getStatus()+"','"+n.getStatus()+"','"+message+"')";
			// System.out.println(stmt);
			 
			 st.executeUpdate(stmt);
			 
			 		 
			 
		}
		catch(Exception e)
		{
			System.out.println("Problem with url.." + e);
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void main(String args[])
	{
		Server s = new Server();
		s.c=new Cpu();
		s.m=new Memory();
		s.d=new Disk();
		s.n=new Network();
		
		System.out.println(s.m.calculate()+"\n\n");
		System.out.println(s.c.calculate()+"\n\n");
		System.out.println(s.d.calculate()+"\n\n");
		System.out.println(s.n.calculate()+"\n\n");
		s.update();
	}
}
