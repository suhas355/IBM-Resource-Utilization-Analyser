
import resource.extract.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
class cpu implements Runnable
{
	Thread Tcpu;
	String usr,sys,iowait,idle;
	public cpu()
	{
		
		Tcpu = new Thread(this);
		Tcpu.start();
	}
	
	public void run()
	{
		try
		{
			cpuInfo c = new cpuInfo();
			c.extract();
			//c.display();
			
			usr=new Float(c.usr).toString();
			
			sys=new Float (c.sys).toString();
			idle=new Float(c.idle).toString();
			iowait=new Float(c.iowait).toString();
			informixMain.key--;
			
		}
		catch(Exception e)
		{
			System.out.println("Exception!!! :(");
		}
	}
	
	
}
class disk implements Runnable
{
	Thread Tdisk;
	String disks,reads,partition,writes;
	public disk()
	{
		
		Tdisk = new Thread(this);
		Tdisk.start();
	}
	
	public void run()
	{
		try
		{
			diskInfo d = new diskInfo();
			d.extract();
			//d.display();
			disks=new Integer(d.disks).toString();
			reads=new Integer(d.reads).toString();
			partition=new Integer(d.partition).toString();
			writes=new Integer(d.writes).toString();
			informixMain.key--;
			
		}
		catch(Exception e)
		{
			System.out.println("Exception!!! :(");
		}
	}
	
	
}
class memory implements Runnable
{
	Thread Tmem;
	String totalMem, usedMem, activeMem, inactiveMem,freeMem,bufferMem,swapMem;
	public memory()
	{
		
		Tmem = new Thread(this);
		Tmem.start();
	}
	
	public void run()
	{
		try
		{
			memInfo m = new memInfo();
			m.extract();
			//m.display();
			totalMem=new Integer(m.totalMem).toString();
			usedMem=new Integer(m.usedMem).toString();
			activeMem=new Integer(m.activeMem).toString();
			inactiveMem=new Integer(m.inactiveMem).toString();
			freeMem=new Integer(m.freeMem).toString();
			bufferMem=new Integer(m.bufferMem).toString();
			swapMem=new Integer(m.swapMem).toString();
			informixMain.key--;
			
		}
		catch(Exception e)
		{
			System.out.println("Exception!!! :(");
		}
	}
	
	
}
class network implements Runnable
{
	Thread Tnet;
	String totalReceived,forwarded,inDiscarded,inDelivered,outRequests;
	public network()
	{
		
		Tnet = new Thread(this);
		Tnet.start();
	}
	
	public void run()
	{
		try
		{
			netInfo n = new netInfo();
			n.extract();
			//n.display();
			totalReceived=new Integer(n.totalReceived).toString();
			forwarded=new Integer(n.forwarded).toString();
			inDiscarded=new Integer(n.inDiscarded).toString();
			inDelivered=new Integer(n.inDelivered).toString();
			outRequests=new Integer(n.outRequests).toString();
			informixMain.key--;
		}
		catch(Exception e)
		{
			System.out.println("Exception!!! :(");
		}
	}
	
	
}
public class informixMain 
{
	static int key=4;
	@SuppressWarnings("deprecation")
	public static void main(String args[])
	{
		String url = "jdbc:informix-sqli://192.168.1.65:9088/resource:INFORMIXSERVER=ol_informix1170;user=informix;password=informix";
		String ip,time,sql;
		Connection con;
		Statement st;
	
		try
		{
		Class.forName("com.informix.jdbc.IfxDriver");
		 
		
			con = DriverManager.getConnection(url);
			 st = con.createStatement();
			// System.out.println("Connection to database successfully");
			 
		
		//To Calculate time...
		Calendar cal = Calendar.getInstance();
		int day,month,year;
		 day=cal.get(Calendar.DATE);
		 month=cal.get(Calendar.MONTH)+1;
		 year=cal.get(Calendar.YEAR);
		 Date dt=new Date();
		 
         time ="'"+year+"-"+month+"-"+day+" "+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds()+"'";
        
         //To get ip address of system...

	    	   	
	    	    Process p = Runtime.getRuntime().exec("ifconfig eth0");
	    	    
	            
	            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            
	         //   BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	            
	           stdInput.readLine();
	           String s=stdInput.readLine();
	           String add[]=s.split(":");
	           String val[]=add[1].split(" ");
	           ip="'"+val[0]+"'";
	    	
	    	
		//---------To extract system resource information-----------
		cpu c=new cpu();
		network n=new network();
	    memory m=new memory();
	    disk d=new disk();
		while(key!=0)
			Thread.sleep(100);
		//To insert Cpu info to DB
		sql="insert into cpu values("+time+","+ip+","+c.usr+","+c.sys+","+c.iowait+","+c.idle+")";
		st.executeUpdate(sql);
		
		//To insert Disk info to DB
		sql="insert into disk values("+time+","+ip+","+d.disks+","+d.reads+","+d.partition+","+d.writes+")";
		st.executeUpdate(sql);
		
		//To insert Memory info to DB
		sql="insert into memory values("+time+","+ip+","+m.totalMem+","+m.usedMem+","+m.activeMem+","+m.inactiveMem+","+m.freeMem+","+m.bufferMem+","+m.swapMem+")";
		st.executeUpdate(sql);
	
		//To insert Network info to DB
		sql="insert into network values("+time+","+ip+","+n.totalReceived+","+n.forwarded+","+n.inDiscarded+","+n.inDelivered+","+n.outRequests+")";
		st.executeUpdate(sql);
		
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
