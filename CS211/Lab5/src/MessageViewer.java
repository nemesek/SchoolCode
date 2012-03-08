import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MessageViewer extends JFrame
{
   private JButton okJButton; 
   private JButton clearJButton; 
   private JPanel  buttonPnl;
   private JTextArea contentJTextArea;
   
   public MessageViewer()
   {
      super( "Debugging Exercise" ); 

      //setLayout( BorderLayout());
      setLayout(new BorderLayout());
 
      JLabel title = new JLabel("Click to see a friendly message!");
      
      // build a text area and add to the center
      contentJTextArea = new JTextArea( 1, 4 );
      //TODOadd( contentJTextArea, CENTER ); 
      add(contentJTextArea, JTextArea.CENTER_ALIGNMENT);

      // build a button panel and add to the south      
      okJButton = new JButton("Message of the Day");
      //TODOclearJButton =  JButton("Clear");
      clearJButton = new JButton("Clear");
      buttonPnl = new JPanel();
      buttonPnl.add(okJButton);
      add(clearJButton);
      add( buttonPnl, BorderLayout.SOUTH); 

      //Register an ActionListener for the ok button
      okJButton.addActionListener( new ActionListener()
         {
            public void actionPerformed( ActionEvent e )
            {
               contentJTextArea.setText( "The only constant is change!" );  
            }
         }
      );
                         
      //Register an ActionListener for the clear button
      clearJButton.addActionListener (new ActionListener() 
         {
            public void actionPerformed( ActionEvent e ) 
            {
               
            }
         }
      );
            
   } 
   
    public static void main( String args[] )
   {
      MessageViewer application = new MessageViewer();
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      application.setSize( 400, 100 );
      application.setVisible( true );
   } 
}