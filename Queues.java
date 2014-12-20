package queues;

import java.util.NoSuchElementException;

import printer.PrintRequest;

//E represents the generic type
public class Queues<E> extends QueueNode<E> implements Queue207<E> {

	//head to points to the first element of the linked list while the tail points to the end of the linked list
	public QueueNode<E> head;
	public QueueNode<E> tail;
	int dequeue_counter;

	//Since no elements are in our queue to begin, both head and tail are null
	public Queues() {
		head = null;
		tail = null;
		dequeue_counter = 0;
	}

	@Override
	public boolean add(E e) {

		//We can't add a null to the linked list
		if (e == null) {
			throw new NullPointerException();
		}

		//create new node with given data
		QueueNode<E> node = new QueueNode<E>(e, null);

		//If this is the first element added to the linked list, set and head and tail to created node since we will only have one element
		if (head == null) {
			head = node;
			tail = node;

		} 
		//otherwise add to tail of the queue
		else {
			tail.setNext(node);
			tail = node;

		}
		return true;
	}

	//Remove from head of queue and return its data
	@Override
	public E remove() throws NoSuchElementException {
		// TODO Auto-generated method stub
		dequeue_counter++;
		E temp;

		//can't remove element if queue is empty
		if (head == null) {
			throw new NoSuchElementException();
		} 
		
		//If we only have one element, set both head and tail to null
		else if (head == tail) {
			temp = head.getData();
			head = null;
			tail = null;
			return temp;
		}

		else {
			temp = head.getData();
			head = head.getNext();
			return temp;
		}
	}

	//Retrieve the data of head of queue
	@Override
	public E element() throws NoSuchElementException {
		{
			//If queue is empty, throw exception
			if (head == null) {
				throw new NoSuchElementException();
			}

			else {
				return head.getData();
			}

		}
	}

	//Obtain size of queue by going through entire linked list and adding to counter everytime
	@Override
	public int size() {
		// TODO Auto-generated method stub
		int counter = 0;
		QueueNode<E> newptr = new QueueNode<E>();
		newptr = head;

		while (newptr != null) {
			counter++;
			newptr = newptr.getNext();
		}

		return counter;
	}

	//Print the entire queue
	public void print() 
	{
		// TODO Auto-generated method stub
		QueueNode<E> newptr = new QueueNode<E>();
		newptr = head;

		while (newptr != null) {
			System.out.println(newptr.getData());
			newptr = newptr.getNext();
		}

	}
	
	//Return our dequeue counter
	public int getdequeuecounter()
	{
		return dequeue_counter;
	}

	public static void main(String args[]) {

		//Some simple testing for the class
		Queues<String> list = new Queues<String>();

		list.add("a");
		list.add("b");
		list.add("c");
		
		System.out.println(list.element());
		
		System.out.println(list.size());
		
		list.remove();
		list.remove();
		
		list.print();
		
		Queues<PrintRequest> myQueues[];
		
		myQueues = new Queues[5]; 
		for (int i = 0; i <  5; i++) 
		{
			myQueues[i] = new Queues<PrintRequest>(); 
		}
		
		myQueues[0] = new Queues<PrintRequest>(); 

	}

}
