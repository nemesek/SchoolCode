//import javafoundations.*;
import java.util.*;

public class TreeTester
{
   public static void main (String[] args) 
   {
      //declare a few references to use in building the tree
      LinkedBinaryTree<Integer> emptyNode, lc, rc, subTree1, subTree2, root;
      
      emptyNode = new LinkedBinaryTree<Integer>();
      
      // make a left child
      lc   = new LinkedBinaryTree<Integer>(1);
      // make a right child
      rc   = new LinkedBinaryTree<Integer>(19);
      // build a tree with root 15 and right child of 19 and left child of 1
      subTree1 = new LinkedBinaryTree<Integer>(15, lc, rc);
      
      // build a tree with root 30 and left child of 22 whose rc is 24      
      rc = new LinkedBinaryTree<Integer>(24);
      lc = new LinkedBinaryTree<Integer>(22, emptyNode, rc);
      subTree2  = new LinkedBinaryTree<Integer>(30, lc, emptyNode);
      
      // build a tree with root 20 and attach subTree1 as left child and attach subTree2 as right child
      root  = new LinkedBinaryTree<Integer>(20, subTree1, subTree2);

      LinkedBinaryTree<Integer> current = root;
      
      // print an inorder traversal of the tree
      System.out.println ("Inorder Traversal: ");
      Iterator<Integer> i = current.inorder();
      while (i.hasNext()) 
         System.out.println(" --> " + i.next());
   
      // print a preorder traversal of the tree
      System.out.println("Preorder Traversal: ");
      current = root;
      Iterator<Integer> j = current.preorder();
      while(j.hasNext())
    	  System.out.println("--> " + j.next());
      System.out.println("Postorder Travrsal: ");
      current = root;
      j = current.postorder();
      while(j.hasNext())
    	  System.out.println("--> " + j.next());
      boolean empty = root.isEmpty();
      if(empty)
    	  System.out.println("Tree is empty");
      else
    	  System.out.println("Tree is not empty");
      boolean empty2 = emptyNode.isEmpty();
      if(empty2)
    	  System.out.println("Tree is empty");
      else
       	  System.out.println("Tree is not empty");
      
      int leaves = root.countLeaves();
      System.out.println("Number of leaves in tree: " + Integer.toString(leaves));
    	  
      // print a postorder traversal of the tree
     
      // print the number of nodes in the tree
      
      // print the number of leaves in the tree
   
   }
}