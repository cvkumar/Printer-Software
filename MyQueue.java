package printer;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue {
	
	
	
	
	
	public static void main (String[] args)
	{
		Queue<String> test = new LinkedList<String>();
		
		test.add("hello");
		
		System.out.println(test.peek());
		
		test.remove("hello");
		
		test.add("1");
		test.add("2");
		test.add("2=3");
		test.add("4");
		test.add("5");
		test.add("6");
		
		
		System.out.println (test.toString() );
		
	}

}
