package resource.extract;
import java.io.*;
public class netInfo 
{
	public int totalReceived,forwarded,inDiscarded,inDelivered,outRequests;

	public netInfo() 
	{
		totalReceived=0;
		forwarded=0;
		inDelivered=0;
		inDiscarded=0;
		outRequests=0;
	}

	public int getTotalReceived() 
	{
		return totalReceived;
	}

	public int getForwarded() 
	{
		return forwarded;
	}

	public int getInDiscarded() 
	{
		return inDiscarded;
	}

	public int getInDelivered() 
	{
		return inDelivered;
	}

	public int getOutRequests() 
	{
		return outRequests;
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
        
	   
     	   Process p = Runtime.getRuntime().exec("netstat -s");
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // read the output from the command
       // System.out.println("Here is the standard output of the command:\n");

        	while ((s = stdInput.readLine()) != null) 
        	{
          //  System.out.println(s);
        		if(s.contains("total packets received"))
        			totalReceived=setVal(s);
        		if(s.contains("forwarded"))
        			forwarded=setVal(s);
        		if(s.contains("incoming packets discarded"))
        			inDiscarded= setVal(s);
        		if(s.contains("incoming packets delivered"))
        			inDelivered=setVal(s);
        		if(s.contains("requests sent out"))
        			outRequests=setVal(s);
        		if(s.contains("Icmp:"))
        			break;
        			
       		
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
		System.out.println("\n\t--------------Network Information------------");
		System.out.println("total Received ----->"+totalReceived);
		System.out.println("forwarded ----->"+forwarded);
		System.out.println("input Discarded ----->"+inDiscarded);
		System.out.println("input delivered ----->"+inDelivered);
		System.out.println("out requests ----->"+outRequests);
		
		
	}
}
