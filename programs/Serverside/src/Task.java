import java.sql.*;
import java.io.*;
public class Task 
{
	
		String output;
		
		public Task()
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
				 ResultSet rs=st.executeQuery("select first 1 * from status order by time desc" );
				 rs.next();
				 output="date N time-> "+rs.getDate(1)+"\r\n"+"clientId->"+rs.getString(2)+"\r\nResource status utiliztion->"+rs.getString(7);
				 FileWriter f = new FileWriter("C:\\informix\\ClientResourceStatus.txt");
				 f.write(output);
				 f.close();
				 
				 // to store warning msg in warning.txt only if status is high
				 String warning="";
				 
				 		if(rs.getString(3).equals("high") ||
						 rs.getString(4).equals("high") ||
						 rs.getString(5).equals("high") ||
						 rs.getString(6).equals("high"))
				 		{
				 			
				 			warning=output;
				 			
				 			
				 		}
				 		
				 		FileWriter f1 = new FileWriter("C:\\informix\\Warning.txt");
						 f1.write(warning);
						 f1.close();
				 		
				 
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		public static void main(String args[])
		{
			new Task();
			//System.out.println("end....");
		}

}
