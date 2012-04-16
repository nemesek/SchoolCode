/*
     Name: Dan Nemesek
     Email: djnemese@olemiss.edu
     Program Source File Name: BTNode.java
     Current Date: 4/15/2012
     Course Information: CSCI 211 - Section 01
     Instructor: Ms. C. B. Zickos
     Program Description: Handles implemntation of node related methods - normally invoked from BST and BT modules
     Sources Consulted: Got idea from Stackoverflow on levelsAndNumbers method - used to create a hashtable which stored the
     //node levels.  Used the hashtable within printone()
    
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort.
                    ... My Signature is on File.
*/ 
//*******************************************************************


//  BTNode.java       Java Foundations
//

//  Represents a node in a binary tree with a left and right child.
//  Therefore this class also represents the root of a subtree.
//*******************************************************************

//package javafoundations;
import java.util.Iterator;
import java.util.*;

public class BTNode<T>
{
   protected T element;
   protected BTNode<T> left, right;
   //Added to support level finds for printOne()
   public static Hashtable <String, Integer> ht = new Hashtable<String, Integer>();
	   
   //-----------------------------------------------------------------
   //  Creates a new tree node with the specified data.
   //-----------------------------------------------------------------
   public BTNode (T element)
   {
      this.element = element;
      left = right = null;
   }

   //-----------------------------------------------------------------
   //  Returns the element stored in this node.
   //-----------------------------------------------------------------
   public T getElement()
   {
      return element;
   }

   //-----------------------------------------------------------------
   //  Sets the element stored in this node.
   //-----------------------------------------------------------------
   public void setElement (T element)
   {
      this.element = element;
   }

   //-----------------------------------------------------------------
   //  Returns the left subtree of this node.
   //-----------------------------------------------------------------
   public BTNode<T> getLeft()
   {
      return left;
   }

   //-----------------------------------------------------------------
   //  Sets the left child of this node.
   //-----------------------------------------------------------------
   public void setLeft (BTNode<T> left)
   {
      this.left = left;
   }

   //-----------------------------------------------------------------
   //  Returns the right subtree of this node.
   //-----------------------------------------------------------------
   public BTNode<T> getRight()
   {
      return right;
   }

   //-----------------------------------------------------------------
   //  Sets the right child of this node.
   //-----------------------------------------------------------------
   public void setRight (BTNode<T> right)
   {
      this.right = right;
   }

   //-----------------------------------------------------------------
   //  Returns the element in this subtree that matches the
   //  specified target. Returns null if the target is not found.
   //-----------------------------------------------------------------
   public BTNode<T> find (T target)
   {
      BTNode<T> result = null;

      if (element.equals(target))
         result = this;
      else
      {
         if (left != null)
            result = left.find(target);
         if (result == null && right != null)
            result = right.find(target);
      }

      return result;
   }

   //-----------------------------------------------------------------
   //  Returns the string representation of the binary tree node.
   //-----------------------------------------------------------------
   public String toString() {
	   
	   String result = 
       "( " + element.toString() +" ";
	   if (left != null) {
		   result += left.toString() + " ";
	   }
	   if (right!= null) {
		   result += right.toString() + " ";
	   }
	   result += " )";
	   return result;
	  
   }
   
   //Added method to implement Part1 number 1 requirements
   public String printOne()
   {
	   
	   String result = "";
	   if(right != null)
	   {
		   result += right.printOne();
	   }	   
	   String tempStr = element.toString();
	   Integer temp = ht.get(tempStr);
	   while(temp > 0)
	   {
		   result += "  	";
		   temp--;
	   }
	   result += element.toString() + "-";
	   result += "\n";
	   if(left != null)
	   {
		   result += left.printOne();
	   }
	   return result;
   }

 //Added method to implement Part1 number 2 requirements
   public String printTwo()
   {
	   String result = "";
	   if(right != null)
	   {
		   result += right.printTwo();
	   }
	   result += "\n " + element.toString();
	   if(left != null)
	   {
		   result += left.printTwo();
	   }
	   return result;
	   
   }
   /*public int getLevel(String node)
   {
	   int level = ht.get(node);
	   return level;
   }*/
   //Next two methods are needed by printone
   //Create a hashtable of the all nodes and store the level as the value in the hashtable
   //Got the idea from http://Stackoverflow
   
   public static void level(BTNode n)
   {
	   levelAndNumbers(n, 0);	   
   }
   private static void levelAndNumbers(BTNode n, Integer i)
   {
	   if(n.getLeft() == null && n.getRight() == null)
	   {
		   //System.out.println(n.element.toString() + "=>" + i);
		   ht.put(n.element.toString(), i);
	   }
	   else
	   {
		   //System.out.println(n.element.toString() + "=>" + i);
		   ht.put(n.element.toString(), i);
		   if(n.getLeft() != null)
			   levelAndNumbers(n.getLeft(), i+1);
		   if(n.getRight() != null)
			   levelAndNumbers(n.getRight(), i+1);
	   }
   }


   
   //-----------------------------------------------------------------
   //  Returns the number of nodes in this subtree.
   //-----------------------------------------------------------------
   public int count()
   {
      int result = 1;

      if (left != null)
         result += left.count();

      if (right != null)
         result += right.count();

      return result;
   }

  
    //-----------------------------------------------------------------
   //  Performs an inorder traversal on this subtree, updating the
   //  specified iterator.
   //-----------------------------------------------------------------
   public void inorder (ArrayIterator<T> iter)
   {
      if (left != null)
         left.inorder (iter);
      
      iter.add (element);

      if (right != null)
         right.inorder (iter);
         
   }


     //-----------------------------------------------------------------
   //  Performs a preorder traversal on this subtree, updating the
   //  specified iterator.
   //-----------------------------------------------------------------
   public void preorder (ArrayIterator<T> iter)
   {

	   iter.add (element);

	   if (left != null)
		   left.preorder (iter);

	   if (right != null)
		   right.preorder (iter);
   }

   //-----------------------------------------------------------------
   //  Performs a postorder traversal on this subtree, updating the
   //  specified iterator.
   //-----------------------------------------------------------------
   public void postorder (ArrayIterator<T> iter)
   {

	   if (left != null)
		   left.postorder (iter);

	   if (right != null)
		   right.postorder (iter);

	   iter.add (element);
   }
   
    //-----------------------------------------------------------------
   //  Returns the number of leaves in this subtree.
   //-----------------------------------------------------------------
   public int countLeaves()
   {
      int result = 0;

      if (left == null && right == null) 
         result = 1;
      
      if (left != null)
         result += left.countLeaves();

      if (right != null)
         result += right.countLeaves();

      return result;
   }

   public int height ()
   {
     
      if (left==null && right==null)
         return 0;
      else if (left==null && right!=null) 
         return 1+right.height();
      else if (left!=null && right==null)
         return 1+left.height();
      else return 1+ Math.max(left.height(), right.height());
   }
}
