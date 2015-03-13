package resource.extract;
import java.io.*;
public class cpuInfo 
{
	public float usr,sys,iowait,idle;
	public cpuInfo()
	{
		usr=0;
		sys=0;
		iowait=0;
		idle=0;
		
	}
	public float getUsr() 
	{
		return usr;
	}
	public float getSys() 
	{
		return sys;
	}
	public float getIowait() 
	{
		return iowait;
	}
	public float getIdle() 
	{
		return idle;
	}
	public void setVal(String s)
	{
		int index = s.indexOf("all");
		int i=index;
		//skip CPU attribute "all"
		while(s.charAt(i)!=' ')
			i++;
		
		//skip Blank characters
		while(s.charAt(i)==' ')
			i++;
		
		int j=i;
		while(s.charAt(i)!=' ')
			i++;
		usr=Float.parseFloat(s.substring(j, i));
		
		//To skip %nice
		while(s.charAt(i)==' ')
			i++;
		while(s.charAt(i)!=' ')
			i++;
		while(s.charAt(i)==' ')
			i++;
		
		//To retrieve %sys
		j=i;
		while(s.charAt(i)!=' ')
			i++;
		sys=Float.parseFloat(s.substring(j, i));
		
		//To retrieve IOwait
		
		while(s.charAt(i)==' ')
			i++;
		j=i;
		while(s.charAt(i)!=' ')
			i++;
		iowait=Float.parseFloat(s.substring(j,i));
		
		//To retrieve %idle
		
		i=s.length()-1;
		while(s.charAt(i)!=' ')
			i--;
		//point to character soon after ' '(space)
		i++;
		
		idle=Float.parseFloat(s.substring(i));
		
		
		
		
		
	}
	public int extract()
	{

        String s = null;

        try {
        
	   
     	   Process p = Runtime.getRuntime().exec("mpstat");
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // read the output from the command
       // System.out.println("Here is the standard output of the command:\n");

        	while ((s = stdInput.readLine()) != null) 
        	{
        		if(s.contains("all"))
        			setVal(s);
        		
       		
       	 	}
         
          // read any errors from the attempted command
        //	System.out.println("Here is the standard error of the command (if any):\n");
        
        	while ((s = stdError.readLine()) != null) 
        	{
            System.out.println(s);
        	}
        
        //System.exit(0);
      }
    catch (IOException e)
    {
        System.out.println("exception happened - here's what I know: ");
        e.printStackTrace();
        //System.exit(-1);
    }
		
		
		return 0;
	}
	public void display()
	{
		System.out.println("\n\t----------CPU Information-----------");
		System.out.println("%usr--------->"+getUsr());
		System.out.println("%sys--------->"+getSys());
		System.out.println("%iowait--------->"+getIowait());
		System.out.println("%idle--------->"+getIdle());
	}
}
