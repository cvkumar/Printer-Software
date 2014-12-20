package printer;

/*
 * PrintRequest class contains relevant information regarding a user request for printing.
 *  this PrintRequest class uses fields to record the number of pages to be printed and 
 *  the clock time when the user submitted a print request.
 */

public class PrintRequest {

	int num_pages;
	int time_added;
	
	public PrintRequest (int Num_pages, int time)
	{
		time_added = time;
		num_pages = Num_pages;
	}
	
	public int getNum_pages() {
		return num_pages;
	}

	public int return_time()
	{
		return time_added;
	}
	
}
