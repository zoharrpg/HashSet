import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets;
	private Node[] bucketArray; 
	private int size; 
	
	 
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 5;  
	
	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}
 

	public boolean add( String key )
	{	
		if(contains(key))
		return false;

		int index=hashof(key,numBuckets);
		bucketArray[index]=new Node(key,bucketArray[index]);

		
		++size;
		if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
			upSize_ReHash_AllKeys(); // DOUBLE THE ARRAY .length THEN REHASH ALL KEYS
		return true;
	}
	public boolean remove(String key)
	{
		if(!contains(key))
		return false;

		int index=hashof(key,numBuckets);
		Node curr=bucketArray[index];

		if(curr.data.equals(key))
		{
			bucketArray[index]=curr.next;
			return true;
		}
		

		while(curr.next!=null)
		{
			if(curr.next.data.equals(key))
			{
				curr.next=curr.next.next;
				return true;
			}
			curr=curr.next;

		}

		return false;

		

		

	}
	
	private void upSize_ReHash_AllKeys()
	{	System.out.format("KEYS HASHED=%10d UPSIZING TABLE FROM %8d to %8d REHASHING ALL KEYS\n",size, bucketArray.length, bucketArray.length*2  );
		
	Node[] biggerArray = new Node[ bucketArray.length * 2 ];
		this.numBuckets = biggerArray.length; 
		
		
		for(int i=0;i<bucketArray.length;i++)
		{
			Node curr=bucketArray[i];
			
			while(curr!=null)
			{
				int index = hashof(curr.data,numBuckets);
				biggerArray[index]=new Node(curr.data,biggerArray[index]);

				curr=curr.next;
			}
		}
		
		

		this.bucketArray = biggerArray;
	} 
	private int hashof(String key,int numBuckets)
	{

		int sum =key.length();
		
		char[] array = key.toCharArray();
		for(int i=0; i<array.length;i++)
		{
			int number = (int)array[i]-96;

			sum=(13*sum+number)%numBuckets;
			
			


		}
		return (sum%numBuckets);

	}
public int size()
{
	return size;

}
public boolean isEmpty()
{
	return size()==0;
}

public boolean contains(String key)
{
	int index= hashof(key,numBuckets);
	Node curr=bucketArray[index];

	while(curr!=null)
	{
		if(curr.data.equals(key))
		return true;
		curr=curr.next;
	}

	return false;

}

public void clear()
{
	for(int i=0;i<bucketArray.length;i++)
	bucketArray[i]=null;
}

} //END MyHashSet CLASS

class Node
{	String data;
	Node next;  
	public Node ( String data, Node next )
	{ 	this.data = data;
		this.next = next;
	}
} 



