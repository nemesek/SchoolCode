//*******************************************************************
//  LinkedBinarySearchTree.java       Java Foundations
//
//  Implements the binary tree using a linked representation.
//*******************************************************************

//package javafoundations;

//import javafoundations.*;
//import javafoundations.exceptions.*;

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

	//-----------------------------------------------------------------
	//  Adds the specified element to this binary search tree.
	//-----------------------------------------------------------------
	public void add (T item)
	{
		if (root == null)
			root = new BSTNode<T>(item);
		else
			((BSTNode)root).add(item);
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
			//throw new ElementNotFoundException ("Remove operation failed. "
					//+ "No such element in tree.");
			System.out.println("Remove operation failed. No such element in tree");

		root = ((BSTNode)root).remove(target);

		return node.getElement();
	}

	//-----------------------------------------------------------------
	//  The following methods are left as programming projects.
	//-----------------------------------------------------------------
	public LinkedBinaryTree<T> findMin() 
	{ 
		LinkedBinaryTree<T> lbt = this;
		while(!lbt.getLeft().isEmpty())
		{
			lbt = lbt.getLeft();
		}
		return lbt;

	}


	public LinkedBinaryTree<T> findMax() 
	{ 
		LinkedBinaryTree<T> lbt = this;
		while(!lbt.getRight().isEmpty())
		{
			lbt = lbt.getRight();

		}
		return lbt;
	}
}
