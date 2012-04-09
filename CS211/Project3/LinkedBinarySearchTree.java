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
		//root.level(root);
		return root.toString();
		//return "from lbst";
		
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
	public String printLevels2()
	{
		String result = "";
		double height = root.height();
		double numElements = Math.pow(2, height+1) - 1;
		int num = (int)numElements;
		int[] elements = new int[num];
		Iterator<T> iter  = this.levelorder();
		int currentLevel = 0;
		int index = 0;
		BTNode current = root;
		String tempStr = "";
		int value = 0;
		while(iter.hasNext())
		{	
			  
			  if(index == 0)
			  {
				  tempStr = iter.next().toString();
				  value = Integer.parseInt(tempStr);
				  elements[index] = value;
			  }
	    	  if(current.getLeft() != null)
	    	  {
	    		  tempStr = current.getLeft().element.toString();
	    		  value = Integer.parseInt(tempStr);
	    		  int left = index*2 + 1;
	    		  elements[left] = value;	    		  
	    		  
	    	  }
	    	  if(current.getRight() != null)
	    	  {
	    		  tempStr = current.getRight().element.toString();
	    		  value = Integer.parseInt(tempStr);
	    		  int right = 2*(index+1);
	    		  elements[right] = value;
	    	  }
	    	  index = 2*(index+1) + 1;
	    	  
	    	  /*int level = root.getLevel(tempStr);
	    	  if(level == currentLevel)
	    	  {
	    		  int value = Integer.parseInt(tempStr);
	    		  elements[index] = value;
	    		  index++;
	    	  }
	    	  else
	    	  {
	    		  int value = Integer.parseInt(tempStr);
	    		  elements[index] = value;
	    		  index++;
	    	  }*/
			
		}
		
		
		
		return result;
	}

	public String printLevels()
	{
		String result = "";
		  Iterator<T> iter  = this.levelorder();
		  int currentLevel = 0;
		  
	      while(iter.hasNext())
	      {  	  
	    	  
	    	  String tempStr = iter.next().toString();
	    	  int height = root.height();
	    	  int level = root.getLevel(tempStr);
	    	  if(level == currentLevel)
	    	  {
	    		  while(height > currentLevel)
	    		  {
	    			  result += "		";
	    			  height--;
	    		  }
	    		  result += tempStr + "	";
	    	  }
	    	  else
	    	  {
	    		  result += "\n";
	    		  while(height > currentLevel)
	    		  {
	    			  result += "	";
	    			  height --;
	    		  }
	    		  result += tempStr + "	";
	    		  currentLevel = level;
	    	  }
 	  
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
