/**
 * Sample Skeleton for 'BillPaymentView.fxml' Controller Class
 */

package BillPayment;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import connectdb.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BillPaymentViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboid"
    private ComboBox<String> comboid; // Value injected by FXMLLoader

    @FXML // fx:id="payment"
    private ToggleGroup payment; // Value injected by FXMLLoader

    @FXML // fx:id="txtSD"
    private TextField txtSD; // Value injected by FXMLLoader

    @FXML // fx:id="txtED"
    private TextField txtED; // Value injected by FXMLLoader

    @FXML // fx:id="txtCQty"
    private TextField txtCQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtBQty"
    private TextField txtBQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtAmnt"
    private TextField txtAmnt; // Value injected by FXMLLoader
    
    @FXML // fx:id="rd1"
    private RadioButton rd1; // Value injected by FXMLLoader

    @FXML // fx:id="rd1"
    private RadioButton rd2; // Value injected by FXMLLoader

    
    Connection con=ConnectionDB.doconnect();
    String MF="";
    String MN="*";

    void dofetchMN()
    {
    	PreparedStatement pst;
		try {
			pst = con.prepareStatement("Select mobile from customerentry where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
			MN=rs.getString(1);
			}
			System.out.println(MN);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
    @FXML
    void doExit(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setHeaderText("You want to exit...");
    	alert.setContentText("Are you sure?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		txtAmnt.getScene().getWindow().hide();
    	}
    }

    @FXML
    void doFetchWithCombo(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
    		dofetchMN();
			PreparedStatement pst = con.prepareStatement("select * from bills where name=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
	    	ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				txtSD.setText(rs.getDate("Start Date")+"");
				txtED.setText(rs.getDate("End Date")+"");
				txtAmnt.setText(rs.getFloat("Amnt")+"");
				txtBQty.setText(rs.getFloat("BQty")+"");
				txtCQty.setText(rs.getFloat("CQty")+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void doPay(ActionEvent event) 
    {
    	String pm="";
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
    		if(rd1.isSelected())
    			pm=rd1.getText();
    		else
    			if(rd2.isSelected())
    				pm=rd2.getText();
    			else
    			{
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("ERROR");
    	    		alert.setHeaderText("Payment Mode Not Selected!");
    	    		alert.setContentText("Please Select Payment Mode....");
    	    		alert.showAndWait();
    				
    			}
			PreparedStatement pst = con.prepareStatement("Update bills set Status=1,PaymentMode=? where name=?");
			pst.setString(1,pm);
			System.out.println(pm);
			pst.setString(2,comboid.getSelectionModel().getSelectedItem());
			pst.executeUpdate();
			String msg="Dear Customer, your milk bill is here for Name:"+comboid.getSelectionModel().getSelectedItem()+" from:"+txtSD.getText()+"  to:"+txtED.getText()+" of amount:"+txtAmnt.getText()+" Bill Paid Successfully.Your Milkman(Eshant)-7696045291";
			String resp=sms.SST_SMS.bceSunSoftSend(MN, msg);
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("INFO");
    		alert.setHeaderText("Bill Paid Successfully...");
    		if(resp.indexOf("Exception")!=-1)
				alert.setContentText("Check Internet Connection");
	    	else
	    		if(resp.indexOf("successfully")!=-1)
	    			alert.setContentText("Sent");
	    		else
	    			alert.setContentText( "Invalid Number");
				alert.showAndWait();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void doNew(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	comboid.getEditor().setText("");
    	comboid.getItems().clear();
    	doFetchCombo();
    	txtAmnt.setText("");
    	txtBQty.setText("");
    	txtCQty.setText("");
    	txtED.setText("");
    	txtSD.setText("");
    	rd1.setSelected(false);
    	rd2.setSelected(false);
    }
    
    void doFetchCombo()
    {
    	comboid.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() 
        {
			public void handle(javafx.scene.input.KeyEvent event) {
				MF="Typing.mp3";
				doSound(MF);					
			}
        });
		try 
		{
			PreparedStatement pst = con.prepareStatement("select distinct name from bills where status=0");
			ResultSet rs = pst.executeQuery();
			while(rs.next())
				{
				comboid.getItems().add(rs.getString(1));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    void doSound(String s)
    {
    	Media sound = new Media(new File(s).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    	doFetchCombo();
        
    }
}
