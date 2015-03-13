package resource.extract;
import java.io.*;

public class memInfo 
{
	public int totalMem, usedMem, activeMem, inactiveMem,freeMem,bufferMem,swapMem;
	
	public memInfo()
	{
		totalMem=0;
		usedMem=0;
		activeMem=0;
		inactiveMem=0;
		freeMem=0;
		bufferMem=0;
		swapMem=0;
		
	}

	public int getTotalMem() 
	{
		return totalMem;
	}

	public int getUsedMem() 
	{
		return usedMem;
	}

	public int getActiveMem() 
	{
		return activeMem;
	}

	public int getInactiveMem() 
	{
		return inactiveMem;
	}

	public int getFreeMem() 
	{
		return freeMem;
	}

	public int getBufferMem() 
	{
		return bufferMem;
	}

	public int getSwapMem() 
	{
		return swapMem;
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
		return (parameter/1024);
	}
	public int extract()
	{
		String s = null;

        try {
        
	   
     	   Process p = Runtime.getRuntime().exec("vmstat -s");
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // read the output from the command
      //  System.out.println("Here is the standard output of the command:\n");

        	while ((s = stdInput.readLine()) != null) 
        	{
          //  System.out.println(s);
        		if(s.contains("total memory"))
        			totalMem=setVal(s);
        		if(s.contains("used memory"))
        			usedMem=setVal(s);
        		if(s.contains("free memory"))
        			freeMem= setVal(s);
        		if(s.contains("buffer memory"))
        			bufferMem=setVal(s);
        		
        		if(s.contains("inactive memory"))
        			inactiveMem=setVal(s);
        		else
        			if(s.contains("active memory"))
        				activeMem=setVal(s);
        		if(s.contains("total swap"))
        		{
        			swapMem=setVal(s);
        			break;
        		}
        			
        			
       		
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
		System.out.println("\n\t--------------Memory Information--------------");
		
		System.out.println("Total memory------>"+totalMem);
		System.out.println("used memory------>"+usedMem);
		System.out.println("active memory------>"+activeMem);
		System.out.println("inactive memory------>"+inactiveMem);
		System.out.println("free  memory------>"+freeMem);
		System.out.println("buffer memory------>"+bufferMem);
		System.out.println("swap memory------>"+swapMem);
		
	}
}

