package printer;

/*
 * Define a Printer class with [at least] these methods (beyond a constructor)
a boolean printerIdle() method that returns true if the printer currently is not processing a print request, and false if the printer 
is busy processing a Print Request (i.e., the printer is printing a requested file).

a boolean printFile (PrintRequest pr) method:
if the printer is idle, the printer will start processing the given print request, and the method returns true
if the printer is already processing another Print Request, the new Print Request pr is ignored, and the method returns false

a PrintRequest processForOneUnit() method that prints one page of the current print request
if the printer is idle, or if the current Print Request is NOT completed within 1 unit of time, then processForOneUnit returns null. (Internally, the printer object should record that one additional page of the current Print Request has been printed.)
if the current Print Request is completed in the current time unit, the method returns the current PrintRequest object that just finishes printing.
 */

import java.util.NoSuchElementException;

import printer.PrintRequest;
import queues.Queues;

public class OperatingSystem {
	
	//Source: http://stackoverflow.com/questions/8346052/create-an-array-of-queues-in-java
	//An Array of Queues containing print requests
	Queues<PrintRequest> myQueues[];
	//Flags for which algorithm we use
	boolean A = false;
	boolean B = false;
	boolean C = false;
	
	//number of queues used
	int n;
	//number of dequeues before promotion of lower queue element 
	int k;
	
	public OperatingSystem(int number_of_queues, int K, String algorithm)
	{
		n = number_of_queues;
		k = K;
		
		//Based on algorithm received switch flag to true accordingly and make proper number of queues
		if (algorithm.compareTo("A") == 0)
		{
			myQueues = new Queues[number_of_queues]; 
			myQueues[0] = new Queues<PrintRequest>(); 
			A = true;
		}
		else if (algorithm.compareTo("B") == 0)
		{
			myQueues = new Queues[number_of_queues]; 
			for (int i = 0; i <  number_of_queues; i++) 
			{
				myQueues[i] = new Queues<PrintRequest>(); 
			}
			B = true;
		}
		else if (algorithm.compareTo("C") == 0)
		{
			myQueues = new Queues[number_of_queues]; 
			for (int i = 0; i <  number_of_queues; i++) 
			{
				myQueues[i] = new Queues<PrintRequest>(); 
			}
			C = true;
		}
		else
		{
			//If we didn't get the right flag, throw exception
			System.out.println("We require A, B, or C as an algorithm");
			throw new IllegalArgumentException();
		}
	}
	
	//Find which element is going to be sent to the printer next
	public PrintRequest hasNextPrintRequest()
	{
		if (A)
		{
			//If the head is not null, there exists print request(s) to send to the printer
			if (myQueues[0].head != null)
			{
				return myQueues[0].head.getData();
			}
			else
			{
				//Otherwise there are no requests left at the moment so return a null
				System.out.println("No Requests left");
				return null;
			}
		}
		else
		{
			//Look through entire array of queues for next item to print
			for (int i = 0; i < myQueues.length; i++)
			{
				if (myQueues[i].head != null)
				{
					return myQueues[i].head.getData();
				}
			}
			//If none are left, return null
			System.out.println("No Requests left");
			return null;
		}
	}
	
	//Hand the printer a new request
	public PrintRequest nextPrintRequest()
	{
		PrintRequest temp = null;
		//We use promote to move a lower priority request up
		PrintRequest promote;
		
		//If were using algorithm A, then send the next print request to the printer and remove it from the operating system
		if (A)
		{
			if (myQueues[0].head != null)
			{
				temp = myQueues[0].head.getData();
				myQueues[0].remove();
				return temp;
			}
		}
		//Same process for algorithm B and C
		else
		{
			//Find the next queue to print, return it while deleting it from the operating system
			//Also check if we need to promote a lower priority print request
			for (int i = 0; i < myQueues.length; i++)
			{
				if (myQueues[i].head != null)
				{
					//A bit confusing but we are simply checking if we need to promote a lower priority request and if there exists a lower
					//priority request to move up. Then we delete it from the lower priority queue
					if (myQueues[i].getdequeuecounter() >= k && myQueues.length < i)
					{
						if (myQueues[i + 1].head != null)
						{
						promote = myQueues[i+1].getData();
						myQueues[i].add(promote);
						myQueues[i+1].remove();
						}
					}
					//Return our next print request to print while removing it from our queue
					temp = myQueues[i].head.getData();
					myQueues[i].remove();
					return temp;
				}
			}
			
		}
		//if we couldn't find anything our checking method does not work properly
		return null;
	}
	
	//We add the print request based on our algorithm
	void addPrintRequest(PrintRequest pr)
	{
		//Can't add a null print request
		if (pr == null)
		{
			throw new IllegalArgumentException();
		}
		
		else if(A)
		{
			myQueues[0].add(pr);
		}
		else if (B)
		{
			//Add it to the ith queue where i = pages of the print request / 10
			myQueues[pr.num_pages / 10].add(pr);
		}
		else 
		{
			//Add it to the ith queue where i = pages of the print request mod our number of queues
			myQueues[pr.num_pages % myQueues.length].add(pr);
		}
		
	}
	
	public static void main (String[] args)
	{
		//Simple test
		OperatingSystem op = new OperatingSystem(10, 5, "C");
	}
	
	
}
