package resource.extract;
import java.io.*;

public class diskInfo 
{
	
	public int disks,reads,partition,writes;

	public int getDisks() 
	{
		return disks;
	}
	public int getReads() 
	{
		return reads;
	}
	public int getPartition() 
	{
		return partition;
	}
	public int getWrites() 
	{
		return writes;
	}
	public diskInfo() 
	{
		disks = 0;
		reads = 0;
		partition =0;
		writes = 0;
	}
	public int setVal(String s)
	{
		int i=0;
		while(s.charAt(i)==' ')
			i++;
		int j=i;
		while(s.charAt(i)!=' ')
			i++;
		String value = s.substring(j,i);
		int parameter = Integer.parseInt(value);
		return parameter;
	}
	public int extract()
	{

        String s = null;

        try {
        
	   
     	   Process p = Runtime.getRuntime().exec("vmstat -D");
        
        BufferedReader stdInput = new BufferedReader(new 
           	 InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new 
             InputStreamReader(p.getErrorStream()));

        // read the output from the command
       // System.out.println("Here is the standard output of the command:\n");

        	while ((s = stdInput.readLine()) != null) 
        	{
          //  System.out.println(s);
        		if(s.contains("disks"))
        			disks=setVal(s);
        		if(s.contains("total reads"))
        			reads=setVal(s);
        		if(s.contains("partitions"))
        			partition= setVal(s);
        		if(s.contains("writes"))
        		{
        			writes=setVal(s);
        			break;
        		}
        		
        		
        		
        			
       		
       	 	}
         
           
        
       
          
     
     
        // read any errors from the attempted command
//System.out.println("Here is the standard error of the command (if any):\n");
        
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
		System.out.println("\n\t----------Disk Information-----------");
		System.out.println("Disks---->"+disks);
		System.out.println("reads---->"+reads);
		System.out.println("partition---->"+partition);
		System.out.println("writes---->"+writes);
	}
}
