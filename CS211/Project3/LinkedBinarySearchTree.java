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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printLevels3()
	{
		String result = "";
		double height = root.height();
		double numElements = Math.pow(2, height+1);
		numElements--;
		int num = (int)numElements;
		int[] elements = new int[num];
		Iterator<T> iter  = this.levelorder();
		//int currentLevel = 0;
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
		//print out values
		/*for(int i=0; i<numElements; i++)
		{
			result += Integer.toString(elements[i]) + ",";
		}*/
		
		int i=0;
		for(int level=0; level <= height + 1; level++)
		{
			String gap = "";
			double numNodes = Math.pow(2, level);
			double printGap = Math.pow(2, height);
			//printGap /= 2;
			for(int k=0; k < (printGap/numNodes); k++)
			{
				gap += "    ";
				//gap += "  ";
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
	public String printLevels2()
	{
		String result = "";
		double height = root.height();
		double numElements = Math.pow(2, height+1);
		int num = (int)numElements + 10;
		int[] elements = new int[num];
		Iterator<T> iter  = this.levelorder();
		int currentLevel = 0;
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
			  //current = root.find(value);
			  
			  if(index == 0)
			  {
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
	    	  index++;
		
		}
		int previous = 1;
		//Print out elements
        for(int i=0; i<numElements; i++)
        {
        	
        	if(i == 0)
        	{
        		result += "\n";
        		int temp = i*2 + 1;
        		while(temp < numElements && elements[temp] != 0)
        		{
        			result +="        ";
        			temp = temp*2 + 1;
        		}
        		result += Integer.toString(elements[i]);
        	}
        	else if(i == 1)
        	{
        		result += "\n";
        		previous = 2*i;
        		int temp = i*2 + 1;
        		while(temp < numElements && elements[temp] != 0)
        		{
        			result +="   ";
        			temp = temp*2 + 1;
        		}
        		result += "   " + Integer.toString(elements[i]) + "     ";
        		i++;
        		//result += " ";
        		temp = i*2 + 1;
        		while(temp < numElements && elements[temp] != 0)
        		{
        			result +="     ";
        			temp = temp*2 + 1;
        		}
        		result += "      " + Integer.toString(elements[i]);
        		
        	}
        	else
        	{
        		result += "\n";
        		previous = 2*previous;
        		for(int j = 0; j < previous; j++)
        		{
        			//result += Integer.toString(elements[i]) + "	";
        			//i++;
        			int temp = i*2 + 1;
        			while(temp < numElements && elements[temp] != 0)
        			{
        				result +="    ";
        				temp = temp*2 + 1;
        			}
        			if(elements[i] > 0)
        			{
        				for(int k = 0; k <j; k++)
        				{
        					result += " ";
        				}
        				result += "   " + Integer.toString(elements[i]) + "  ";
        			}
        			else
        			{
        				result += "    ";
        			}
        			i++;
        		}
        		
        	}
        		
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
