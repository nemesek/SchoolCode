import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class EventDemoFrame extends JFrame 
{
   private JPanel selectionPanel;
   
   private JScrollPane  outputScrlPane;
   private JTextArea    outputTxtArea;
   private JComboBox    deptCmboBx;
   private JRadioButton showPrereqsRdoBtn;
   private JList        courseList;
   private JButton      clearBtn;

   private String depts[] = { "Computer Science", "Mechanical Engineering", "Electrical Engineering" };

   private String csCourses[] = { "CSci 111", "CSci 112", "CSci 211"};


   // set up GUI and register event handlers
   public EventDemoFrame()
   {
      super( "Events" );

      // create an output area for query results
      outputTxtArea = new JTextArea( 10, 30 );
      outputTxtArea.setLineWrap( true );
      outputTxtArea.setEditable( false );
      outputTxtArea.setBackground( Color.WHITE );
      outputTxtArea.setForeground( Color.BLACK );
      // add the output area to a scroll pane 
      outputScrlPane = new JScrollPane(outputTxtArea);

      // clear button for clearing the output area
      clearBtn = new JButton( "Clear" );
      // registering an ActionListener to respond to this button's click event 
      clearBtn.addActionListener( new ActionListener() 
         {
             public void actionPerformed(ActionEvent ae)
             {
            	 outputTxtArea.setText("");
            
             }
         }
      ); 
               
      // create a comboBox that lists the departments and listens for item and key events
      deptCmboBx = new JComboBox( depts );
      // Register an ItemListener
      //<write or complete the code below > 
      deptCmboBx.addItemListener(new ItemListener() 
         {
            public void itemStateChanged( ItemEvent event ) 
            {
              outputTxtArea.append("\nCombo Box getItem()->" + event.getItem());
              //TODO add stuff here
           
            }
         } 
      );
 
      // create a radioButton that gives the option of listing the pre-reqs and 
      //   listens for action events
      showPrereqsRdoBtn = new JRadioButton( "Show Pre-requisites", false );
      // Register an ActionListener
      //<write or complete the code below > 
      showPrereqsRdoBtn.addActionListener(new ActionListener()
         {
    	  public void actionPerformed(ActionEvent event)
    	  {
    		  outputTxtArea.append("\nRadio Button ActionCommand() ->" + event.getActionCommand());
    		  
    	  }
    	  
    	  	
		  
         }
      );
      
      // create a JList and listens for list selection events
      courseList = new JList( csCourses );
      courseList.setBorder(new LineBorder(Color.BLACK, 2));      
      // Register a ListSelectionListner
      //<write or complete the code below > 
      courseList.addListSelectionListener( new ListSelectionListener()
          {
    	  	public void valueChanged(ListSelectionEvent listSelectionEvent) 
    	  	{
    	  		outputTxtArea.append(("\nList toString() ->" + listSelectionEvent.toString()));
    	  		//string x = Boolean.parseBoolean(listSelectionEvent.getValueIsAdjusting());
    	  		boolean y = listSelectionEvent.getValueIsAdjusting();
    	  		String x = Boolean.toString(y);
    	  		outputTxtArea.append("\n" + x);
    	  	}
            
          }
      );      

      selectionPanel = new JPanel();
      GridLayout panelLayout = new GridLayout(2,3);
      panelLayout.setHgap(50);
      selectionPanel.setLayout(panelLayout);    
      // row 1, columns 1, 2 & 3
      selectionPanel.add( deptCmboBx );

      JLabel listLabel = new JLabel("Select any that apply:    ");
      listLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      selectionPanel.add(listLabel);
      selectionPanel.add( courseList );
      // row 2, columns 1, 2 & 3
      selectionPanel.add(new JLabel(""));
      selectionPanel.add( showPrereqsRdoBtn );
      selectionPanel.add( clearBtn );

      setLayout( new BorderLayout() );
      add( outputScrlPane, BorderLayout.CENTER );
      add( selectionPanel, BorderLayout.SOUTH );
 
   } 
  
} 