package printer;

public class Printer {

	//The printer's current request
	PrintRequest currentrequest;
	
	//Initially the printer has no request
	Printer ()
	{
		currentrequest = null;
	}
	
	//check if the printer is processing a request
	public boolean printerIdle()
	{
		
		if (currentrequest == null)
		{
				return true;	
		}
		else
		{
			return false;
		}
	}

	/*
	 * a boolean printFile (PrintRequest pr) method:
if the printer is idle, the printer will start processing the given print request, and the method returns true
if the printer is already processing another Print Request, the new Print Request pr is ignored, and the method returns false
	 */
	public boolean printFile(PrintRequest pr)
	{
		if (currentrequest == null)
		{
			currentrequest = pr;
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
	/*
	 * a PrintRequest processForOneUnit() method that prints one page of the current print request
if the printer is idle, or if the current Print Request is NOT completed within 1 unit of time, then processForOneUnit returns null. (Internally, 
the printer object records that one additional page of the current Print Request has been printed.)
if the current Print Request is completed in the current time unit, the method returns the current PrintRequest object that 
just finishes printing.
 */
	public Object processForOneUnit()
	{
		PrintRequest temp = null;
		 if (currentrequest == null)
		 {
			 
			 return null;
		 }
		 else if (currentrequest.num_pages != 1)
		 {
			 currentrequest.num_pages--;
			 return null;
		 }
		 else 
		 {
			 temp = currentrequest;
			 currentrequest = null;
			 return temp;
		 }
		 
	}

	
	
	
} // end class
