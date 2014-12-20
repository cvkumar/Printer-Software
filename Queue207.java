
package queues;
/***
 * This interface presents a simplified specification for the
 * full Java Queue Interface
 */

import java.lang.NullPointerException;
import java.util.NoSuchElementException;

public interface Queue207 <E>
{
    /**
     * inserts the specified element e into this queue,
     * if it is possible to do so
     * @param e   the element to add
     * @returns true upon success
     * @throws NullPointerException if e is null
     */
    public boolean add (E e);

    /**
     * retrieves and removes the head of this queue
     * @returns the head of this queue
     * @throws NoSuchElementException  if this queue is empty
     */
    public E remove () throws NoSuchElementException;

    /**
     * retrieves, but does not remove, the head of this queue
     * @returns the head of this queue
     * @throws NoSuchElementException  if this queue is empty
     */
    public E element () throws NoSuchElementException;

    /** 
     * returns the number of elements in this queue
     */
    public int size();
}

 