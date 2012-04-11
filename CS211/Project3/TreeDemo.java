import java.util.*;

public class TreeDemo 
{
	public static void main(String [] args)
	{
		   LinkedBinarySearchTree<Integer> lbst = new LinkedBinarySearchTree();
		   lbst.add(20);
		   lbst.add(30);
		   lbst.add(15);
		   lbst.add(1);
		   lbst.add(19);
		   lbst.add(22);
		   lbst.add(24);
		   //lbst.add(35);
		   //lbst.add(5);
		   //lbst.add(39);
		   //lbst.add(4);
		   //lbst.add(18);
		   
		  //Print out 1 
		  System.out.println("Calling printOne()"); 
	      System.out.println(lbst.printOne());
	      //Print out 2
	      System.out.println("Calling printTwo()");
	      System.out.println(lbst.printTwo());
	      //Try to add an element that is already in the BST, and call printTwo again to show 
	      //no duplicate elements
	      lbst.add(19);
	      System.out.println();
	      System.out.print("printTwo() BST output after trying to add 19 again:");
	      System.out.println(lbst.printTwo());
	      System.out.println();
	      System.out.println("Calling printLevels() to display in level order");
	      System.out.println(lbst.printLevels());
	      
	      
	}

}
