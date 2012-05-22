package Jan;

import static upietz.Constants.*;
import Alex.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.event.KeyEvent.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

	/*  File: KeyBindingExample.java
	 *  
	 *  Description: This class was created to provide a simple example of the
	 *               application of KeyBinding to a Swing component. In this
	 *               example, a JTextField and JButton are displayed on a JPanel in
	 *               a JFrame. When the the focus is on the JTextField and an Enter
	 *               key is pressed, the JButton action will occur as though the
	 *               button had been pressed.
	 *               
	 *               The behavior shown in this simple example:
	 *               1.  When the window first appears, the text field has focus.
	 *                   Any keys typed will be entered as data in the text field;
	 *               2.  Pressing Enter while the text field has focus will cause
	 *                   the data typed to be selected so that any additional typing
	 *                   will replace the existing. this behavior is useful for
	 *                   multiple entries; and 
	 *               3.  If the user presses the Enter button, focus is immediately
	 *                   transferred back to the text field and the data in the 
	 *                   field is selected.  
	 *
	 *  Source: Java Tutorials and Swing component APIs as needed. 
	 *
	 *  Author: GregBrannon, August 2011
	 */
	public class Spielmodus
	{
	    private JFrame mainFrame;
	    private JTextField dataField;
	    private JButton enterButton;
	    private JPanel mainPanel;
	    private Action enterAction;
	    private Action playerDownAction;
	    private Action playerUpAction;
	    private Action playerLeftAction;
	    private Action playerRightAction;
	    private Action playerBombAction;
	    private ButtonListener buttonListener;
	    
	    private Gameplay gameplay;
	    
	    // the main() method creates a simple JFrame to demonstrate the
	    // key binding of the enter key to the component button "enter" 
	    public void main( String[] args )
	    {
	    	this.gameplay = new Gameplay(1);
	        mainFrame = new JFrame( "Key Binding Frame" );

	        mainFrame.add( makePanel() );
	        mainFrame.setLocationRelativeTo( null );
	        mainFrame.setSize( 200, 100 );
	        mainFrame.setResizable( false );
	        mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	        
	        mainFrame.setVisible( true );

	    } // end method main()
	    
	    JPanel makePanel()
	    {
	        // declares the components used to create the JFrame's content and
	        // the actions that will occur when the enter button is selected
	        mainPanel = new JPanel();
	        //buttonListener = new ButtonListener();
	        dataField = new JTextField( 15 );
	        //enterButton = new JButton( "Enter Player" );
	        //enterButton.addActionListener( buttonListener );
	        
	        // defines an AbstractAction item that will program the action to occur
	        // when the enter key is pressed
	        enterAction = new EnterAction();
	        playerDownAction = new PlayerDownAction();
	        playerUpAction = new PlayerUpAction();
	        playerLeftAction = new PlayerLeftAction();
	        playerRightAction = new PlayerRightAction();
	        playerBombAction = new PlayerBombAction();

	        // the following two lines do the magic of key binding. the first line
	        // gets the dataField's InputMap and pairs the "ENTER" key to the
	        // action "doEnterAction" . . .
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ),
	                "doEnterAction" );
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( VK_DOWN, 0 ), "doPlayerDownAction");
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( VK_UP, 0 ), "doPlayerUpAction");
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( VK_LEFT, 0 ), "doPlayerLeftAction");
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( VK_RIGHT, 0 ), "doPlayerRightAction");
	        dataField.getInputMap().put( KeyStroke.getKeyStroke( VK_SPACE, 0 ), "doPlayerBombAction");

	        // . . . then this line pairs the AbstractAction enterAction to the
	        // action "doEnterAction"
	        dataField.getActionMap().put( "doEnterAction", enterAction );
	        dataField.getActionMap().put( "doPlayerDownAction", playerDownAction);
	        dataField.getActionMap().put( "doPlayerUpAction", playerUpAction);
	        dataField.getActionMap().put( "doPlayerLeftAction", playerLeftAction);
	        dataField.getActionMap().put( "doPlayerRightAction", playerRightAction);
	        dataField.getActionMap().put( "doPlayerBombAction", playerBombAction);

	        // the following commented line 'seems' to have the same affect as the
	        // two previous lines. this may be an acceptable approach when only a  
	        // single action is required.
	        // dataField.setAction( enterAction );
	        
	        // add the components to the JPanel and return the completed product
	        mainPanel.add( dataField );
	        //mainPanel.add( enterButton );
	        
	        return mainPanel;
	    }

	    // class EnterAction is an AbstractAction that defines what will occur
	    // when the enter key is pressed. 
	    class EnterAction extends AbstractAction
	    {
	        public void actionPerformed( ActionEvent tf )
	        {
	            // provides feedback to the console to show that the enter key has
	            // been pressed
	            System.out.println( "The Enter key has been pressed." );
	            // pressing the enter key then 'presses' the enter button by calling
	            // the button's doClick() method
	            enterButton.doClick();
	            
	        } // end method actionPerformed()
	        
	    } // end class EnterAction
	    
	    class PlayerDownAction extends AbstractAction
	    {
	    	public void actionPerformed( ActionEvent tf )
	    	{
	    		System.out.println("action: down");
	    		gameplay.controls("down");
	    	}	
	    }
	    
	    class PlayerUpAction extends AbstractAction
	    {
	    	public void actionPerformed( ActionEvent tf )
	    	{
	    		System.out.println("action: up");
	    		gameplay.controls("up");
	    	}	
	    }
	    
	    class PlayerLeftAction extends AbstractAction
	    {
	    	public void actionPerformed( ActionEvent tf )
	    	{
	    		System.out.println("action: left");
	    		gameplay.controls("left");
	    	}	
	    }
	    
	    class PlayerRightAction extends AbstractAction
	    {
	    	public void actionPerformed( ActionEvent tf )
	    	{
	    		System.out.println("action: right");
	    		gameplay.controls("right");
	    	}	
	    }
	    
	    class PlayerBombAction extends AbstractAction
	    {
	    	public void actionPerformed( ActionEvent tf )
	    	{
	    		System.out.println("action: bomb");
	    		gameplay.controls("bomb");
	    	}	
	    }
	    
	    // class ButtonListener defines the action to occur when the enter button
	    // is pressed
	    class ButtonListener implements ActionListener
	    {
	        public void actionPerformed( ActionEvent bp )
	        {
	            // provides feedback to the console to show that the enter button
	            // was pressed
	            System.out.println( "The enter button was pressed." );

	            // focus must be returned to the text field in order for the
	            // selectAll() method to work.
	            dataField.requestFocusInWindow();
	            dataField.selectAll();
	            
	        } // end method actionPerformed()
	        
	    } // end class ButtonListener

	} // end class KeyBindingExample