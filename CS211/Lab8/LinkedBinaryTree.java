//*******************************************************************
//  LinkedBinaryTree.java       Java Foundations
//
//  Implements a binary tree using a linked representation.
//*******************************************************************

//package javafoundations;

import java.util.Iterator;
//import javafoundations.*;
//import javafoundations.exceptions.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>
{
   protected BTNode<T> root;

   //-----------------------------------------------------------------
   //  Creates an empty binary tree.
   //-----------------------------------------------------------------
   public LinkedBinaryTree()
   {
      root = null;
   }

   //-----------------------------------------------------------------
   //  Creates a binary tree with the specified element as its root.
   //-----------------------------------------------------------------
   public LinkedBinaryTree (T element){
      root = new BTNode<T>(element);
   }

   //-----------------------------------------------------------------
   //  Creates a binary tree with the two specified subtrees.
   //-----------------------------------------------------------------
   public LinkedBinaryTree (T element, LinkedBinaryTree<T> left,
      LinkedBinaryTree<T> right)
   {
      root = new BTNode<T>(element);
      root.setLeft(left.root);
      root.setRight(right.root);
   }

   //-----------------------------------------------------------------
   //  Returns the element stored in the root of the tree. Throws an
   //  EmptyCollectionException if the tree is empty.
   //-----------------------------------------------------------------
   public T getRootElement()
   {
      if (root == null)
    	  System.out.println("Get root operation failed.  The tree is empty");
         //throw new EmptyCollectionException ("Get root operation "
           // + "failed. The tree is empty.");

      return root.getElement();
   }

   //-----------------------------------------------------------------
   //  Returns the left subtree of the root of this tree.
   //-----------------------------------------------------------------
   public LinkedBinaryTree<T> getLeft()
   {
      if (root == null)
    	  System.out.println("Get left operation failed.  The tree is empty");
         //throw new EmptyCollectionException ("Get left operation "
           // + "failed. The tree is empty.");

      LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
      result.root = root.getLeft();

      return result;
   }

   //-----------------------------------------------------------------
   //  Returns the right subtree of the root of this tree.
   //-----------------------------------------------------------------
   public LinkedBinaryTree<T> getRight()
   {
      if (root == null)
    	  System.out.println("Get right operation failed.  The tree is emptpy");
         //throw new EmptyCollectionException ("Get right operation "
           // + "failed. The tree is empty.");

      LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
      result.root = root.getRight();

      return result;
   }

   //-----------------------------------------------------------------
   //  Returns the element in this binary tree that matches the
   //  specified target. Throws an EmptyCollectionException if the tree
   //  is empty. Throws a ElementNotFoundException if the
   //  target is not found.
   //-----------------------------------------------------------------
   public T find (T target)
   {
      BTNode<T> node = null;

      if (root == null)
    	  System.out.println("Find operation failed.  The tree is empty");
         //throw new EmptyCollectionException("Find operation failed. The tree is empty.");
    	  
      node = root.find(target);
      if (node == null)
    	  System.out.println("Find operation failed. No such element in tree.");
         //throw new ElementNotFoundException("Find operation failed. "
           // + "No such element in tree.");

      return node.getElement();
   }

   //-----------------------------------------------------------------
   //  Returns true if the binary tree contains an element that
   //  matches the specified element and false otherwise.
   //-----------------------------------------------------------------
   public boolean contains (T target) {
	   
	   boolean result = false;
	   try {
		   T element = this.find(target);
		   if (element != null){
			   result = true;
		   }
	   } catch (Exception exception){
		   result = false;
	   }// catch (Exception ece) {
		  // result = false;
	   //}
	   return result;
   }
 
 
  //-----------------------------------------------------------------
   //  Satisfies the Iterable interface using an inorder traversal.
   //-----------------------------------------------------------------
   public Iterator<T> iterator()
   {
      return inorder();
      
   }
 
  //-----------------------------------------------------------------
   //  Returns the number of elements in this binary tree.
   //-----------------------------------------------------------------
   public int size()
   {
      int result = 0;

      if (root != null)
         result = root.count();

      return result;
   }

   //-----------------------------------------------------------------
   // Returns the string representation of the binary tree.
   //-----------------------------------------------------------------
   public String toString() { 
	
	   if (root == null){
		   return "empty tree.";
	   }
	   return root.toString();
   }
   
   //-----------------------------------------------------------------
   //  Populates and returns an iterator containing the elements in
   //  this binary tree using a levelorder traversal.
   //-----------------------------------------------------------------
   /*public Iterator<T> levelorder()
   {
      LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
      ArrayIterator<T> iter = new ArrayIterator<T>();

      if (root != null)
      {
         queue.enqueue(root);
         while (!queue.isEmpty())
         {
            BTNode<T> current = queue.dequeue();

            iter.add (current.getElement());

            if (current.getLeft() != null)
               queue.enqueue(current.getLeft());
            if (current.getRight() != null)
               queue.enqueue(current.getRight());
         }
      }

      return iter;
   }*/

 
   //-----------------------------------------------------------------
   //  Populates and returns an iterator containing the elements in
   //  this binary tree using an inorder traversal.
   //-----------------------------------------------------------------
   public Iterator<T> inorder()
   {
      ArrayIterator<T> iter = new ArrayIterator<T>();

      if (root != null)
         root.inorder (iter);

      return iter;
   }
  
   //-----------------------------------------------------------------
   //  Populates and returns an iterator containing the elements in
   //  this binary tree using a preorder traversal.
   //-----------------------------------------------------------------
   public Iterator<T> preorder()
   {
      ArrayIterator<T> iter = new ArrayIterator<T>();
      if (root != null)
          root.preorder (iter);

      // fill in the missing code
      
      return iter;
   }

   //-----------------------------------------------------------------
   //  Populates and returns an iterator containing the elements in
   //  this binary tree using an postorder traversal.
   //-----------------------------------------------------------------
   public Iterator<T> postorder()
   {
      ArrayIterator<T> iter = new ArrayIterator<T>();
      if (root != null)
          root.postorder (iter);

      // fill in the missing code
      
      return iter;
   }

   //-----------------------------------------------------------------
   // Returns true if the binary tree contains no elements, and
   //  false otherwise.
   //-----------------------------------------------------------------
   public boolean isEmpty() { 
   
	   //fill in the missing code
	   if(root == null)
		   return true;
	   else
		   return false;
      //return true; // return true to satisfy compiler until method is completed.
   }

   //-----------------------------------------------------------------
   // Returns the number of leaves in the binary tree.
   //-----------------------------------------------------------------
   public int countLeaves() { 
   
	   int result = 0;

      // fill in the missing code
	   if(root != null)
		   result = root.countLeaves();
      
      return result;
   }   
}
