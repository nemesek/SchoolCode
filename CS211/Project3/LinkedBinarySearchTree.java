//*******************************************************************
//  LinkedBinarySearchTree.java       Java Foundations
//
//  Implements the binary tree using a linked representation.
//*******************************************************************

//package javafoundations;

//import javafoundations.*;
//import javafoundations.exceptions.*;
import java.util.*;
import java.math.*;

public class LinkedBinarySearchTree<T extends Comparable<T>>
extends LinkedBinaryTree<T> implements BinarySearchTree<T>
{
	//-----------------------------------------------------------------
	//  Creates an empty binary search tree.
	//-----------------------------------------------------------------
	public LinkedBinarySearchTree()
	{
		super();
	}

	//-----------------------------------------------------------------
	//  Creates a binary search tree with the specified element at its
	//  root.
	//-----------------------------------------------------------------
	public LinkedBinarySearchTree (T element)
	{
		root = new BSTNode<T>(element);
	}
	
	public String toString()
	{
		return root.toString();		
	}
	public String printOne()
	{
		BTNode.level(root);
		return root.printOne();
	}
	public String printTwo()
	{
		return root.printTwo();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printLevels()
	{
		String result = "";
		double height = root.height();
		double numElements = Math.pow(2, height+1);
		numElements--;
		int num = (int)numElements;
		int[] elements = new int[num];
		Iterator<T> iter  = this.levelorder();
		int index = 0;
		BTNode current = root;
		String tempStr = "";
		int value = 0;
		
		//Store tree in array format per p 665
		while(iter.hasNext())
		{
			
			tempStr = iter.next().toString();
			value = Integer.parseInt(tempStr);
			current = current.find(value);
			if(current == null)
			{
				current = root;
				current = current.find(value);
			}
				  
			if(index == 0)
			{
				elements[index] = value;
			}
			while(index < elements.length && elements[index]==0)
			{
				index++;
			}
	    	if(current.getLeft() != null)
	    	{
	    		tempStr = current.getLeft().element.toString();
	    		value = Integer.parseInt(tempStr);
	    		int left = index*2 + 1;
	    		if(left < elements.length)
	    			elements[left] = value;	    		  
	    		  
	    	}
	
	    	if(current.getRight() != null)
	    	{
	    		tempStr = current.getRight().element.toString();
	    		value = Integer.parseInt(tempStr);
	    		int right = 2*(index+1);
	    		if(right < elements.length)
	    			elements[right] = value;
	    	}
	    	index++;
		
		}			

		//Now Build the return String result
		int i=0;
		for(int level=0; level <= height + 1; level++)
		{
			String gap = "";
			double numNodes = Math.pow(2, level);
			double printGap = Math.pow(2, height);
	
			for(int k=0; k < (printGap/numNodes); k++)
			{
				gap += "    ";

			}
			
			for(int j=0; j<numNodes; j++)
			{
				if(i < numElements)
				{
					if(elements[i] > 0)
					{
						result += gap + Integer.toString(elements[i]) + gap;
					}
					else
					{
						result += gap +" " + gap;
					}
					i++;
				}
				
			}
			result += "\n\n";
		}		
		
		
		return result;
	}
	
	

	//-----------------------------------------------------------------
	//  Adds the specified element to this binary search tree.
	//-----------------------------------------------------------------
	public void add (T item)
	{
		if (root == null)
			root = new BSTNode<T>(item);
		else
		{
			//Need to check if item already exists
			if(root.find(item) == null)
				((BSTNode)root).add(item);	
				
		}
	}

	//-----------------------------------------------------------------
	//  Removes and returns the element matching the specified target
	//  from this binary search tree. Throws an ElementNotFoundException
	//  if the target is not found.
	//-----------------------------------------------------------------
	public T remove (T target)
	{
		BSTNode<T> node = null;

		if (root != null)
			node = ((BSTNode)root).find(target);

		if (node == null)
			throw new ElementNotFoundException ("Remove operation failed. "
					+ "No such element in tree.");

		root = ((BSTNode)root).remove(target);

		return node.getElement();
	}

	//-----------------------------------------------------------------
	//  The following methods are left as programming projects.
	//-----------------------------------------------------------------
	public T findMin() { 

        //<your code goes here> 
		return null;
	}


	public T findMax() { 

		//<your code goes here> 
		return null;
	}
}
