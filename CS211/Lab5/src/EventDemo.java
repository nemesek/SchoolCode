import javax.swing.JFrame;

public class EventDemo
{
   public static void main( String args[] )
   {
      EventDemoFrame eventDemoFrame = new EventDemoFrame(); 
      eventDemoFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      eventDemoFrame.setSize( 500, 800 ); 
      eventDemoFrame.setVisible( true ); 
   } 
} 