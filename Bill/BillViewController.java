/**
 * Sample Skeleton for 'BillView.fxml' Controller Class
 */

package Bill;

import java.io.File;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import connectdb.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BillViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lstAll"
    private ListView<String> lstAll; // Value injected by FXMLLoader

    @FXML // fx:id="dateSD"
    private DatePicker dateSD; // Value injected by FXMLLoader

    @FXML // fx:id="dateED"
    private DatePicker dateED; // Value injected by FXMLLoader

    @FXML // fx:id="lbldiff"
    private Label lbldiff; // Value injected by FXMLLoader

    @FXML // fx:id="txtCQty"
    private TextField txtCQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtBQty"
    private TextField txtBQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtCPrice"
    private TextField txtCPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtBPrice"
    private TextField txtBPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtCQtyV"
    private TextField txtCQtyV; // Value injected by FXMLLoader

    @FXML // fx:id="txtBQtyV"
    private TextField txtBQtyV; // Value injected by FXMLLoader

    @FXML // fx:id="txtAmnt"
    private TextField txtAmnt; // Value injected by FXMLLoader

    Connection con=ConnectionDB.doconnect();
    PreparedStatement pst;
    LocalDate localSD;
    LocalDate localED;
    String MF="";
    
    @FXML
    void doGenBill(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	if(dateSD.getEditor().getText().isEmpty()==true ||dateED.getEditor().getText().isEmpty()==true || txtCQtyV.getText().isEmpty()==true || txtBQty.getText().isEmpty()==true)
    	{
	    	Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Error...");
			alert.setHeaderText("Field is empty");
			alert.setContentText("Please Fill all fields..");
			alert.showAndWait();
	    }
    	else
    	{
    	float TCA=((p2+1)*Float.parseFloat((txtCQty.getText()))+Float.parseFloat((txtCQtyV.getText())))*Float.parseFloat((txtCPrice.getText()));
    	float TBA=((p2+1)*Float.parseFloat((txtBQty.getText()))+Float.parseFloat((txtBQtyV.getText())))*Float.parseFloat((txtBPrice.getText()));
    	txtAmnt.setText(String.valueOf(TCA+TBA));
	    }
    }
    long p2;
    @FXML
    void doGetDays(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	if(dateSD.getEditor().getText().isEmpty()==true ||dateED.getEditor().getText().isEmpty()==true)
    	{
	    	Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Error...");
			alert.setHeaderText("Set Dates");
			alert.setContentText("Date is empty..");
			alert.showAndWait();
	    }
	    else
		{
	    	localSD=dateSD.getValue();
	    	localED=dateED.getValue();
	     	p2 = ChronoUnit.DAYS.between(localSD, localED);
	    	lbldiff.setText("Bill of "+(p2+1)+" Days");
		}

    }
   
    @FXML
    void doGetVar(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	if(dateSD.getEditor().getText().isEmpty()==true ||dateED.getEditor().getText().isEmpty()==true)
    	{

    	Alert alert=new Alert(AlertType.ERROR);
    	alert.setTitle("Error...");
    	alert.setHeaderText("Set Dates");
    	alert.setContentText("Date is empty..");
    	alert.showAndWait();
 
    }
    else
    	{
    	java.sql.Date SD=java.sql.Date.valueOf(localSD);
    	java.sql.Date ED=java.sql.Date.valueOf(localED);
    	try {
    		
			pst=con.prepareStatement("select SUM(cvar) as CQ ,Sum(bvar) as BQ from routine where name=? and ?<=VarDate<=?");
			pst.setString(1,lstAll.getSelectionModel().getSelectedItem());
			pst.setDate(2,SD);
			pst.setDate(3,ED);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				txtCQtyV.setText(String.valueOf(rs.getFloat("CQ")));
				txtBQtyV.setText(String.valueOf(rs.getFloat("BQ")));
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    }
    boolean jasuss=false;
    void checkentry()
    {
    	localSD=dateSD.getValue();
    	localED=dateED.getValue();
		try {
			pst=con.prepareStatement("select * from bills where name=?");
			pst.setString(1,lstAll.getSelectionModel().getSelectedItem());
			ResultSet rs=pst.executeQuery();
				while(rs.next())
				{
				LocalDate sdg=LocalDate.parse(rs.getString("Start Date"));
				LocalDate edg=LocalDate.parse(rs.getString("End Date"));
				System.out.println(localSD+" "+localED+"-***"+sdg+"**"+edg+"********");
				if(sdg.isEqual(localSD) && edg.isEqual(localED))
				{
					jasuss=true;
					System.out.println(jasuss);
				}
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    String MN="";
	@FXML
    void doSaveSMS(ActionEvent event) 
    {
		MF="Sound.mp3";
    	doSound(MF);
    	if(dateSD.getEditor().getText().isEmpty()==true ||dateED.getEditor().getText().isEmpty()==true || txtCQtyV.getText().isEmpty()==true || txtBQty.getText().isEmpty()==true)
    	{
	    	Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Error...");
			alert.setHeaderText("Field is empty");
			alert.setContentText("Please Fill all fields..");
			alert.showAndWait();
	    }
    	else
    	{
    		checkentry();
   		try {
   			java.sql.Date SD=java.sql.Date.valueOf(localSD);
        	java.sql.Date ED=java.sql.Date.valueOf(localED);
        	if(jasuss==false)
        	{
			pst=con.prepareStatement("Insert into bills values(?,?,?,?,?,?,?,?)");
			pst.setString(1,lstAll.getSelectionModel().getSelectedItem());
			pst.setDate(2,SD);
			pst.setDate(3,ED);
			float TCMQ=((p2+1)*Float.parseFloat(txtCQty.getText()));
			float TBMQ=((p2+1)*Float.parseFloat(txtBQty.getText()));
			pst.setFloat(4,TCMQ);
			pst.setFloat(5,TBMQ);
//			pst.setFloat(4,Float.parseFloat((txtCQty.getText()))+Float.parseFloat((txtCQtyV.getText())));
//			pst.setFloat(5,Float.parseFloat((txtBQty.getText()))+Float.parseFloat((txtBQtyV.getText())));
			pst.setFloat(6,Float.parseFloat((txtAmnt.getText())));
			pst.setInt(7,0);
			pst.setString(8,"Pending..");
			pst.executeUpdate();
			String msg="Dear Customer, your milk bill is here for Name:"+lstAll.getSelectionModel().getSelectedItem()+" from:"+dateSD.getEditor().getText()+"  to:"+dateED.getEditor().getText()+" of amount:"+txtAmnt.getText()+"Kindly Pay intime.Your Milkman(Eshant)-7696045291";
			String resp=sms.SST_SMS.bceSunSoftSend(MN, msg);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("Data is Saved");
			if(resp.indexOf("Exception")!=-1)
				alert.setContentText("Check Internet Connection");
	    	else
	    		if(resp.indexOf("successfully")!=-1)
	    			alert.setContentText("Sent");
	    		else
	    			alert.setContentText( "Invalid Number");
				alert.showAndWait();
			} 
        	else
        	{
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("ERROR");
        		alert.setHeaderText("Try Again....");
        		alert.setContentText("Bill for this date already generated");
        		alert.showAndWait();
        		}
			
		}
		catch (SQLException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	}
    }

    @FXML
    void doSelect(MouseEvent event) 
    {
    	//lstAll.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	if(event.getClickCount()==2)
    	{
    		MF="Sound.mp3";
        	doSound(MF);
        	try {
				pst=con.prepareStatement("select * from customerentry where name=?");
				pst.setString(1,lstAll.getSelectionModel().getSelectedItem());
				ResultSet rs=pst.executeQuery();
				boolean jasus=false;
				while(rs.next())
	       		{
	       			jasus=true;
	       			//txtName.setText(rs.getString("name"));
	       			txtCQty.setText(rs.getFloat("CQty")+"");
	       			txtCPrice.setText(String.valueOf(rs.getFloat("CPrice")));
	       			txtBQty.setText(rs.getFloat("BQty")+"");
	       			txtBPrice.setText(rs.getFloat("BPrice")+"");
	       			MN=rs.getString("mobile");
	       		}
				if(jasus==false)
				{
					Alert alert = new Alert(AlertType.ERROR);
	   	    		alert.setTitle("ERROR");
	   	    		alert.setHeaderText("Incorrect Data");
	   	    		alert.setContentText("Entered value is not valid...");
	   	    		alert.showAndWait();
				}
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}

    }
    void doLoad()
    {
    	try {
			pst=con.prepareStatement("select name from customerentry");
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				{
				String name=rs.getString("name");
				lstAll.getItems().add(name);
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
    void initialize() {
    	doLoad();
    }
}
