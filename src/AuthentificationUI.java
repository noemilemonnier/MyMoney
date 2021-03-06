package src;
//-------------------------------------------------------
//For Comp 354 Section PP - Winter 2018
//Iteration 2: Noemi Lemonnier 40001085
//Description: AuthentificationUI.java is the class used to display the AuthentificationList and AuthentificationUser 
// It pops up a window that requires the user to enter a username and password and the system will check if they are
// accepted or rejected and if accepted, they will be taken to the main menu ApplicationLayout
//          
//--------------------------------------------------------
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;



public class AuthentificationUI extends JFrame{
	//open the window
	JFrame frame = new JFrame();

	//to launch the different features in the ApplicationLayout.java
	private static CashSpending cashSpending = new CashSpending();
	private static ApplicationLayout applicationLayout = new ApplicationLayout();
	private static Budgeting budgetting = new Budgeting();
	private static MyCards myCards= new MyCards();

	//declaring attributes
	private static ArrayList <AuthentificationUser> users_list = new ArrayList<AuthentificationUser>();
	private static String username;
	private static String password;
	private static AuthentificationUser user;
	JTextField userTxt;
	JTextField passwTxt;
	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);

	public AuthentificationUI() {

		//to clear all past transactions
		try {
			CashSpendingUI.clearDataBaseTransactionsDone();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setting the array
		AuthentificationList.readFromTheFile(users_list);

		//setting the display, their background color and their layout
		JPanel panel = new JPanel();
		panel.setBackground(Constants.AUTHENTIFICATION_COLOR);
		JPanel verticalPan = new JPanel();
		verticalPan.setBackground(Constants.AUTHENTIFICATION_COLOR);
		JPanel horizPan = new JPanel();
		horizPan.setBackground(Constants.AUTHENTIFICATION_COLOR);
		JPanel horizPan2 = new JPanel();
		horizPan2.setBackground(Constants.AUTHENTIFICATION_COLOR);
		JPanel horizPan3 = new JPanel();
		horizPan3.setBackground(Constants.AUTHENTIFICATION_COLOR);
		BoxLayout boxLayoutV = new BoxLayout(verticalPan, BoxLayout.Y_AXIS);
		BoxLayout boxLayoutH1 = new BoxLayout(horizPan, BoxLayout.X_AXIS);
		BoxLayout boxLayoutH2 = new BoxLayout(horizPan2, BoxLayout.X_AXIS);
		BoxLayout boxLayoutH3 = new BoxLayout(horizPan3, BoxLayout.X_AXIS);
		verticalPan.setLayout(boxLayoutV);
		horizPan.setLayout(boxLayoutH1);
		horizPan2.setLayout(boxLayoutH2);
		horizPan3.setLayout(boxLayoutH3);

		//setting the Unicorn image
		ImageIcon imgPan = new ImageIcon("unicorn.png"); // load the image to a imageIcon
		Image image = imgPan.getImage(); // transform it 
		Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imgPan = new ImageIcon(newimg);  // transform it back
		JLabel imgLab = new JLabel(imgPan);

		//setting the buttons and their actionListener
		JButton newUser = new JButton("New User");
		newUser.addActionListener(new CreateUserListener());
		JButton signIn = new JButton("Sign In");
		signIn.addActionListener(new SignInListener());

		//setting the textfields
		userTxt = new JTextField(10);
		JLabel infoUser = new JLabel();
		infoUser.setLabelFor(userTxt);
		passwTxt = new JTextField(10);
		JLabel infoPass = new JLabel();
		infoPass.setLabelFor(passwTxt);

		//adding all needed components
		JLabel txt1 = new JLabel("To access MyMoneyApp, please enter:");
		JLabel txt2 = new JLabel("Username:\t");
		JLabel txt3 = new JLabel("Password:\t");
		txt1.setFont(new Font("Garamond", Font.PLAIN, 16)); //setting the font
		txt2.setFont(new Font("Arial", Font.BOLD, 14)); //setting the font
		txt3.setFont(new Font("Arial", Font.BOLD, 14)); //setting the font
		verticalPan.add(txt1);
		horizPan.add(txt2);
		horizPan.add(userTxt);
		verticalPan.add(horizPan);
		horizPan2.add(txt3);
		horizPan2.add(passwTxt);
		verticalPan.add(horizPan2);
		horizPan3.add(signIn);
		horizPan3.add(newUser);
		verticalPan.add(horizPan3);
		verticalPan.add(imgLab);
		panel.setBorder(compound);
		panel.add(verticalPan);

		//setting the default JFrame
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(400, 300);
		frame.setResizable(false);
		frame.setTitle("MyMoneyApp Authentification"); //setting title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	/*
	 * Private class to customize the events that will happen when the user clicks on the new user button
	 */
	private class CreateUserListener implements ActionListener{
		/*
		 * Displays a window to allow the user to create its account
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//making sure only one login information are in the database textfile
			//if there is already a login information in the DB textfile
			if(users_list.size()>0){
				JPanel pan=new JPanel();

				//Setting the label for text field
				JLabel aF = new JLabel("This is a one user application.\nDo you want to remove the existing user and create your own?");
				pan.add(aF);

				int optn=JOptionPane.showConfirmDialog(null, pan, "INVALID INPUT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(optn != 0){ //if user clicks on Cancel or closes the window
					JOptionPane.getRootFrame().dispose();
				};
				//if the user clicks on the YES/OK BUTTON
				//remove the login information from the file and from the array
				if(optn == 0){ 
					users_list.remove(0);
					try {
						clearDataBaseAuthentification();
					} catch (IOException e) {
						System.err.println("Error in clearing the database Authentification.txt");
						e.printStackTrace();
					}
					JOptionPane.getRootFrame().dispose(); //close the JOptionPane
				}
			}
			//if there is only no user account in the database Authentification.txt
			if(users_list.size()<1){
				//changing the color of the panel and optionPane
				UIManager.put("OptionPane.background",new ColorUIResource(204, 204, 205));
				UIManager.put("Panel.background",new ColorUIResource(204, 204, 205));

				//create a panel and a layout that fits the amount of information required.
				JPanel pane=new JPanel(new GridLayout(5,2));

				//create text fields to input the information
				JTextField usn = new JTextField(5);
				JTextField psw = new JTextField(10);

				//creating labels for the text fields
				JLabel uS= new JLabel("Enter a username:\t");
				uS.setFont(new Font("Courier New", Font.BOLD, 14));
				JLabel pW= new JLabel("Enter a password:\t");
				pW.setFont(new Font("Courier New", Font.BOLD, 14));

				//setting the labels to the text fields
				uS.setLabelFor(usn);
				pW.setLabelFor(psw);

				//adding the elements to the panel
				pane.add(uS);
				pane.add(usn);
				pane.add(pW);
				pane.add(psw);

				//make the option panel appear in order to ask the user for information to create its account
				int option =  JOptionPane.showConfirmDialog(null, pane, "Creating an Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				//if the user clicks on the CANCEL button or Closes the window
				if(option != 0){
					JOptionPane.getRootFrame().dispose();
				};
				//if the user clicks on the YES/OK BUTTON
				if(option == 0){ 	
					try{
						//if the user does not enter any value
						if((usn.getText().isEmpty()) || (psw.getText().isEmpty())){
							throw new NumberFormatException();
						}
						
						//getting information entered by the user
						username = usn.getText();
						password = psw.getText();

						//creating a new AuthentificationUser object
						user = new AuthentificationUser(username,password);

						//if  login information already exists
						boolean isDuplicate = AuthentificationList.readToFindDuplicate(user);
						if(isDuplicate == true){
							throw new NumberFormatException();
						}
						//if  login information does not exist
						else{
							users_list.add(user); //add object to the ArrayList
							AuthentificationList.writeToFile(user); //write information to the Db txt 
							JOptionPane.getRootFrame().dispose(); //close the window

							//close the authentification frame
							frame.dispose();
							//open the main menu of the application
							applicationLayout.setVisible(true);



						}

					}//if the user enters an invalid value or a card duplicate
					catch (NumberFormatException nfe){
						JOptionPane.showMessageDialog(null, Constants.INVALID_MSG, Constants.INVALID_TITLE, JOptionPane.WARNING_MESSAGE, Constants.WARNING_IMAGE);
						int opt = JOptionPane.CLOSED_OPTION;
						if(opt != 0){
							JOptionPane.getRootFrame().dispose();
						}
					}
				}

			}

		}

	}

	/*
	 * Private class to customize the events that will happen when the user clicks on the button signIn
	 */
	private class SignInListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				//if the user did not enter any value
				if(userTxt.getText().isEmpty() || passwTxt.getText().isEmpty()){
					throw new NumberFormatException();
				}
				//set attributes with information entered by user
				String name = userTxt.getText();
				String psswd = passwTxt.getText();
				//creating new object AuthentificationUser
				user = new AuthentificationUser(name, psswd);
				
				//checks if the username entered exists in the database textfile
				boolean isValid = AuthentificationList.readToFindDuplicate(user);
				//if the username and password matche the db txt information
				if(isValid == true){
					//close the authentification frame
					frame.dispose();
					//launches the application layout frame
					applicationLayout.setVisible(true);
				}
				else{
					throw new NumberFormatException();
				}
			}
			catch (NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "This user login does not exist.\nPlease try again.", Constants.INVALID_TITLE, JOptionPane.WARNING_MESSAGE, Constants.WARNING_IMAGE);
				int opt = JOptionPane.CLOSED_OPTION;
				if(opt != 0){
					JOptionPane.getRootFrame().dispose();
				}
			}

		}

	}
	public static CashSpending getCashSpending() {
		return cashSpending;
	}
	public static ApplicationLayout getApplicationLayout() {
		return applicationLayout;
	}

	public static Budgeting getBudgetting() {
		return budgetting;
	}

	public static MyCards getMyCards() {
		return myCards;
	}

	/*
	 * method to clear the database textfiles for Authentification and MyCards
	 */
	public static void clearDataBaseAuthentification() throws IOException{
		File budget = new File(Constants.BUDGETING_FILE);
		
		if (Constants.AUTHENTIFICATION_FILE.exists() && Constants.AUTHENTIFICATION_FILE .isFile() && Constants.MYCARDS_FILE.exists()&&Constants.MYCARDS_FILE.isFile() && budget.exists() && budget.isFile())
		{
			//delete if exists
			Constants.AUTHENTIFICATION_FILE .delete();
			Constants.MYCARDS_FILE.delete();
			budget.delete();
			
		}
		Constants.AUTHENTIFICATION_FILE .createNewFile();
		Constants.MYCARDS_FILE.createNewFile();
		//budget.createNewFile();
		budgetDup(Constants.DEFAULT_BUDGET_FILE,budget);
	}
	
	/*
	 * Static method that will create a copy of an inputted file
	 */
	public static void budgetDup(File original, File duplicate) throws IOException {
	    InputStream input = null;
	    OutputStream output = null;
	    try {
	        input = new FileInputStream(original);
	        output = new FileOutputStream(duplicate);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = input.read(buffer)) > 0) {
	            output.write(buffer, 0, length);
	        }
	    } finally {
	        input.close();
	        output.close();
	    }
	}
}





