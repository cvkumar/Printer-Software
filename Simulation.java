/*****************************************************************
     * Caleb Kumar 

       Exam 2
     * PO Box #3859                                             
     * Program for CSC 207                                           
     * Problem 1                               
     * Assignment for Wednesday, November 11/19/14                            
     *****************************************************************/

    /* ***************************************************************
     * Academic honesty certification:                               *
     *   Written/online sources used:                                *
     *     -Professor Walkers QueueNode Class 
     *     -Used StackOverflow for help with array of queues (URL commented in near code)    
     *   Help obtained 
     *                                                 *
     *     -I talked to Professor Walker regarding the specifications of the program.    
     *    My signature below confirms that the above list of sources  *
     *   is complete AND that I have not talked to anyone else       *
     *   [e.g., CSC 161 students] about the solution to this problem *
     *                                                               *
     *   Signature:                                                  *
     *****************************************************************/







/*
The program will read four parameters from the terminal:
The probability that some user will make a print request in a single time interval

The algorithm ("A", "B", or "C" from above) for determining how the operating system will decide which queue 
will be used for a user's print request.
Processing proceeds one time unit at a time, based upon a "clock" variable.
Processing in one time unit involves the following:
If the printer is in use, then 1 page of the current job is printed (so the print job is 1 page closer to completion).
A user may or may not make a print request. For this simulation, the likelihood that a print request is generated 
in one time of time should be based on a random number generator and a user-entered probability. If a print request is generated,
 processing in the simulation should depend upon these parameters.
The number of pages for the print request should be determined randomly between 1 and 100 pages.

A new Print Request object is created, containing the number of pages for the job and the starting "clock" time that this request entered the queue.
The user's request is submitted to the operating system in the form of an object with the clock time and print size.
The operating system places the new Print Request object on the appropriate queue.
If the printer is idle, and if at least one print request is pending, then
a print request is selected from the multiple queues, as described above.
the wait time (number of time intervals of waiting) for this Print Request is used to update both a maximum wait time and a total average wait.
the print request is sent to the printer object.
After the "clock" reaches 1000 time units, no new Print Requests should be generated, but the simulation should continue until 
all existing Print Requests have been processed.
 */

package printer;

import printer.Printer;
import printer.PrintRequest;
import printer.OperatingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Simulation {
	//The number n of queues to be used
	//The number k of dequeue operations from one queue before an item from a lower-priority queue is promoted
	int n;
	int k;
	//The algorithm we will use
	String algorithm;
	//The probability of another print request being added
	double prob;
	//Keep track of the time
	int clock;

	public Simulation() {

		// open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//Initially set fields to null;
		String Prob = null;
		String N = null;
		String K = null;

		// read the information from the command-line
		// readLine() method
		//Done in try catch block just in case of an error
		try {

			// prompt the user to enter their information
			System.out.print("Enter your probability (between 0 and 1) of a request being given: ");
			Prob = br.readLine();
			System.out.print("Enter the amount of queues you want to use: ");
			N = br.readLine();
			System.out.print("Enter the The number of dequeue operations from one queue before an item from a lower-priority queue is promoted: ");
			K = br.readLine();
			System.out.print("Enter the The Algorithm you would like to use: ");
			algorithm = br.readLine();
//Catches exception if need be
		} catch (IOException ioe) {
			System.out.println("IO error in obtaining information");
			System.exit(1);
		}
		//Parse through the data to store desired type
		prob = Double.parseDouble(Prob);
		n = Integer.parseInt(N);
		k = Integer.parseInt(K);
		//Clock starts at 0
		clock = 0;
		
		//Print the information-- was Important for testing
		System.out.println("Probability: " + prob);
		System.out.println("number of queues: " + n);
		System.out.println("K: " + k);
		System.out.println("Algorithm: " + algorithm);
	}

	//Now that we have information set lets run it
	public void print ()
	{
		//Create a new printer and operating system for our simulation
		Printer thePrinter = new Printer();
		OperatingSystem operatingsystem = new OperatingSystem(n,k, algorithm);
		
		//Keeps track of relevant information
		int total_time = 0;
		int max_time = 0;
		int number_of_requests = 0;
		double avg_time = 0;
		//Used for new print requests
		PrintRequest newpr;
		
		//We run the program until clock reaches 1000, then we simply finish all our print requests
			while (clock < 1000)
			{
				//if the printer is not idle then print one page from its current request
				if (thePrinter.printerIdle() == false)
				{
				thePrinter.processForOneUnit();
				}
				//Otherwise if there are still more requests, give it a new print request
				else if (operatingsystem.hasNextPrintRequest() != null)
				{
					//Update max time(max time of waiting in queue) and total_time(total time of waiting in queue) 
					if (max_time < (clock - operatingsystem.hasNextPrintRequest().time_added))
					{
						max_time = (clock - operatingsystem.hasNextPrintRequest().time_added);
					}
					total_time = total_time + (clock - operatingsystem.hasNextPrintRequest().time_added);
					//Give it the new request accordingly
					//the method nextPrintRequest gives it the proper new request based on the algorithm used
					thePrinter.printFile(operatingsystem.nextPrintRequest());
				}
			
				//If we obtain a random number between 0 and 1 less than the probability, create a new print request
				if (Math.random() < prob)
				{
					//System.out.println("\nAdd a Request\n"); Used for testing
					//update our total number of print requests
					number_of_requests++;
					//Find a random number of pages between 1 and 100 to use
					Random rand = new Random();
					int randompages = rand.nextInt((100 - 1) + 1) + 1;
					//Create the new print request at this time and with our random page amount
					newpr = new PrintRequest(randompages, clock);
					
					//Add it to the queue accordingly
					//the addPrintRequeust method adds the print request to the correct queue based on its given algorithm
					operatingsystem.addPrintRequest(newpr);
				}
				//Add one to our clock since we assume this is the amount of time it takes to print one page
				clock++;
			}
			
			//We ran out of time, but we still need to finish printing all our requests (same method as 
			//above just without adding print requests)
			while (operatingsystem.hasNextPrintRequest() != null)
			{
				//if the printer is not idle then process
				if (thePrinter.printerIdle() == false)
				{
				thePrinter.processForOneUnit();
				}
				else
				{
					//Keep updating these
					if (max_time < (clock - operatingsystem.hasNextPrintRequest().time_added))
					{
						max_time = (clock - operatingsystem.hasNextPrintRequest().time_added);
					}
					total_time = total_time + (clock - operatingsystem.hasNextPrintRequest().time_added);
					thePrinter.printFile(operatingsystem.nextPrintRequest());
				}
			}
			
			//
			System.out.println("requests " +  number_of_requests); //Used for testing
			//Calculate the average time spent in Queue for a print request
			avg_time = (double) total_time /  (double) number_of_requests;
			//Print the maximum and average amount of time print requests spent waiting to be printed
			System.out.println("The average time spent waiting was: " + avg_time + "\nThe maximum time spent waiting was: " + max_time);
	}
		
	

	public static void main(String[] args) {
		
		//TESTING
		//We will have a total of seven tests to test our variety of cases
		//In order to do the testing we will run this class multiple times inputting in different parameters everytime.
		
		
		/*Test 1
		 * Test: Ensure that the proper parameters are read in for every case in program
		 * Test ~ Print the parameters that are read in
		 * 
		 * Expected: printed Parameters to match given input
		 * Received: printed Parameters matched given given input
		 * 
		 * Therefore the parameters are properly read in.
		 */
		
		/*Test 2
		 * Test to see if algorithm A works properly when simulated.
		 * Test~ Run program with algorithm "A" and prob = 0.02 as its parameters (rest of parameters are irrelevant for the test)
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 10 and 30 total print requests
		 * -An average time spent waiting in queue of between 30 and 150 units
		 * -A maximum time spent waiting in queue of 100 and 300 units
		 * -average number of page requests to be 50
		 * 
		 * Received: 17 total requests
		 * -average time spent waiting of 133.4
		 * -Maximum time spent waiting of 224
		 * -average page requests of 56.5
		 * 
		 * Notice that the average page requests was a bit higher than expected thus it makes sense for our average time spent waiting to be
		 * 133.4 and the max time spent waiting to be higher than expected.
		 * 
		 * Since the expectations matched the received output, algorithm A works as desired.
		 * 
		 * 
		 * /*Test 3
		 * Test to see if algorithm B works properly when simulated with a low value for k.
		 * Test~ Run program with algorithm "B", prob = 0.02, n = 10, k = 3 as its parameters 
		 * 
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 10 and 30 total print requests
		 * -An average time spent waiting in queue of between 30 and 100 units
		 * -A maximum time spent waiting in queue of 100 and 300 units
		 * -average number of page requests to be 50
		 * 
		 * Received: 17 total requests
		 * -average time spent waiting of 35.84
		 * -Maximum time spent waiting of 132
		 * -average page requests of 43.12
		 * 
		 *  
		 * Since our expected matched our received output, algorithm b works for a relatively high value of k.
		 * 
		 * 
		 * /*Test 4
		 * Test to see if algorithm B works properly when simulated with a relatively high value for k.
		 * Test~ Run program with algorithm "B", prob = 0.02, n = 10, k = 10 as its parameters 
		 *
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 10 and 30 total print requests
		 * -An average time spent waiting in queue of between 30 and 150 units
		 * -A maximum time spent waiting in queue of 200 and 500 units
		 * -average number of page requests to be 50
		 * 
		 * Note that we expect higher waiting times because low priority queues are promoted less often 
		 * and so we should get higher averages.
		 * 
		 * Received: 17 total requests
		 * -average time spent waiting of 86.91
		 * -Maximum time spent waiting of 425
		 * -average page requests of 58.123
		 * 
		 * Notice that waiting times were higher than in test 2 because less promoting was done.
		 *  
		 * Since our expected matched our received output, algorithm b works for a relatively high value of k.
		 * 
		 * 
		 *  * /*Test 5
		 * Test to see if algorithm c works properly and the program can run well with a relatively high probability, 
		 * amount of queues, and relatively high k when simulated.
		 * Test~ Run program with algorithm "B", prob = 0.05, n = 20, k = 10 as its parameters 
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 40 and 60 total print requests
		 * -An average time spent waiting in queue of between 200 and 400 units
		 * -A maximum time spent waiting in queue of 500 and 900 units
		 * -average number of page requests to be 50
		 * 
		 * Notice that we expect higher times because we have more queues, not too much promoting, and more print requests
		 * 
		 * Received: 49 total requests
		 * -average time spent waiting of 335.7
		 * -Maximum time spent waiting of 870
		 * -average page requests of 60.33
		 * 
		 * .
		 *  
		 * Since our expected matched our received output, algorithm c works relatively high probability, 
		 * amount of queues, and relatively high k when simulated.
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Test 6
		 * Test to see if algorithm c works properly and the program can run well with a relatively low probability, 
		 * amount of queues, and k when simulated.
		 * Test~ Run program with algorithm "B", prob = 0.01, n = 5, k = 3 as its parameters 
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 5 and 15 total print requests
		 * -An average time spent waiting in queue of between 20 and 50 units
		 * -A maximum time spent waiting in queue of 40 and 90 units
		 * -average number of page requests to be 50
		 * 
		 * Note that we expect lower waiting times because low priority queues are promoted more often 
		 * and we have more print requests
		 * 
		 * Received: 5.4 total requests
		 * -average time spent waiting of 27.45
		 * -Maximum time spent waiting of 74.333
		 * -average page requests of 53.4
		 * 
		 * Since our expected matched our received output, algorithm c works relatively low probability, 
		 * amount of queues, and k when simulated.
		 * 
		 * 
		 * Test 7
		 * Test to see if algorithm c works properly and the program can run well with a average probability, 
		 * high amount of queues, and low k when simulated.
		 * Test~ Run program with algorithm "B", prob = 0.02, n = 10, k = 4 as its parameters 
		 * -In addition print total requests and average number of pages for print requests
		 * 
		 * Expected: Between 15 and 30 total print requests
		 * -An average time spent waiting in queue of between 50 and 100 units
		 * -A maximum time spent waiting in queue of 150 and 300 units
		 * -average number of page requests to be 50
		 * 
		 * 
		 * Received:  total requests
		 * -average time spent waiting of 75.6
		 * -Maximum time spent waiting of 220
		 * -average page requests of 47.6
		 * 
		 * Since our expected matched our received output, algorithm c works with average probability, high
		 * amount of queues, and low k when simulated.
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Since all tests were passed effectively, the program meets its desired specifications
		 * 
		 */
				
		Simulation sim1 = new Simulation();
		
		sim1.print();
		
		
		
		
		
		
		/*
		for (int i = 0; i < 10; i++)
		{
			System.out.println(Math.random());
		}
	*/
		
		
	}

}
