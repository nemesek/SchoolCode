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
		   lbst.add(19);
		   lbst.add(35);
		   lbst.add(23);
		   lbst.add(11);
		   lbst.add(12);
		   lbst.add(13);
		   
		   //int[]  treeArray = new int[8];
		   
	      // print an inorder traversal of the tree
	      //System.out.println ("Inorder Traversal: ");
	      //Iterator<Integer> i = lbst.inorder();
	      //int index =0;
	      /*while (i.hasNext())
	      {
	    	  int temp = i.next();
	         System.out.println(" --> " + temp);
	         treeArray[index] = temp;
	         index++;
	      }*/
	      System.out.println(lbst.printOne());
	      System.out.println(lbst.printTwo());
	      //System.out.println(lbst.printLevels());
	      System.out.println(lbst.printLevels2());
	      //System.out.println(lbst.printLevels());
	      /*Iterator<Integer> i = lbst.printLevels();
	      while(i.hasNext())
	      {
	    	  System.out.println("--> " + i.next());
	      }
	      lbst.printLevels();*/
	      
	      
	}

}
