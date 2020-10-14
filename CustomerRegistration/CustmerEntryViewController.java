/**
 * Sample Skeleton for 'CustmerEntryView.fxml' Controller Class
 */

package CustomerRegistration;

import java.io.File;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import connectdb.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CustmerEntryViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtAddress"
    private TextArea txtAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtMobNo"
    private TextField txtMobNo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCQty"
    private TextField txtCQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtCPrice"
    private TextField txtCPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtBQty"
    private TextField txtBQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtBPrice"
    private TextField txtBPrice; // Value injected by FXMLLoader

    @FXML // fx:id="DOS"
    private DatePicker DOS; // Value injected by FXMLLoader
    
    @FXML // fx:id="imgCustomer"
    private ImageView imgCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="lblimg"
    private Label lblimg; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtStatus"
    private TextField txtStatus; // Value injected by FXMLLoader
    
    Connection con=ConnectionDB.doconnect();
    String MF="";
    @FXML
    void doBrowse(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.bmp", "*.png", "*.jpg", "*.gif"));
    	if (selectedFile != null) 
    	{
    		try {
    			lblimg.setVisible(false);
				lblimg.setText(selectedFile.getPath());
				imgCustomer.setImage(new Image(selectedFile.toURI().toURL().toString()));
    		} catch (Exception e) {   
				e.printStackTrace();
			}
    		}
    	else {
    	    imgCustomer.setImage(null);
    	}
    }
    
    void Checkempty()
    {
   	if(txtName.getText().isEmpty()== true || txtAddress.getText().isEmpty()== true || txtBPrice.getText().isEmpty()== true || DOS.getEditor().getText().isEmpty()==true ||txtBQty.getText().isEmpty()== true || txtCPrice.getText().isEmpty()== true || txtCPrice.getText().isEmpty()== true || txtStatus.getText().isEmpty()== true || txtMobNo.getText().isEmpty()== true )
    	{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("ERROR");
    		alert.setHeaderText("Please Fill All Details...");
    		alert.setContentText("Ooops, there was an error!");
    		alert.showAndWait();    	
    	}
    }

    @FXML
    void doClose(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation");
    	alert.setHeaderText("You want to close...");
    	alert.setContentText("Are you sure?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		lblimg.getScene().getWindow().hide();
    	}
    }
    
    
    @FXML
    void doFetch(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst=con.prepareStatement("select * from customerentry where name=?");
			pst.setString(1,txtName.getText());
			ResultSet rs= pst.executeQuery();
			boolean jasus=false;
       		while(rs.next())
       		{
       			jasus=true;
       		   	doNew(event);
       			txtName.setText(rs.getString("Name"));
       			lblimg.setText(rs.getString("Pic"));
       			File Path= new File(lblimg.getText());
       			txtAddress.setText(rs.getString("address"));
       			txtMobNo.setText(rs.getString("Mobile"));
       			txtCPrice.setText(rs.getFloat("CPrice")+"");
       			txtCQty.setText(rs.getFloat("CQty")+"");
       			txtBPrice.setText(rs.getFloat("BPrice")+"");
       			txtBQty.setText(rs.getFloat("BQty")+"");
       			txtStatus.setText(rs.getInt("status")+"");
       			LocalDate date=LocalDate.parse(rs.getString("dos"));
       			DOS.setValue(date);
       			lblimg.setVisible(false);
       			if(Path.canExecute()==true)
       			imgCustomer.setImage(new Image(Path.toURI().toURL().toString()));
       			else{
       				lblimg.setVisible(true);
       				lblimg.setText("No image uploaded");
       			}
       		}
       		if(jasus==false)
       		{
       			Alert alert = new Alert(AlertType.ERROR);
   	    		alert.setTitle("ERROR");
   	    		alert.setHeaderText("Incorrect Data");
   	    		alert.setContentText("Entered value is not valid...");
   	    		alert.showAndWait();
       		}
       		
    	}
		 catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doNew(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	lblimg.setVisible(true);
    	lblimg.setText("Upload Image");
    	txtAddress.setText("");
    	txtBPrice.setText("");
    	txtBQty.setText("");
    	txtCPrice.setText("");
    	txtCQty.setText("");
    	txtMobNo.setText("");
    	txtName.setText("");
    	txtStatus.setText("");
    	DOS.setValue(null);
    	imgCustomer.setImage(null);
    }
    
    @FXML
    void doSave(ActionEvent event) 
    { 
    	MF="Sound.mp3";
    	doSound(MF);
    	Checkempty();
    	
    	try {
    		LocalDate local=DOS.getValue();
        	java.sql.Date dos=	java.sql.Date.valueOf(local);
			PreparedStatement pst=con.prepareStatement("insert into customerentry values(?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1,txtName.getText());
			pst.setString(2,lblimg.getText());
			if(txtMobNo.getText().length()==10)
			pst.setString(3,txtMobNo.getText());
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Check Mobile No.");
				alert.setContentText("Invalid Mobile  No.!");
				alert.showAndWait();
			}
			pst.setString(4,txtAddress.getText());
			pst.setFloat(5,Float.parseFloat(txtCQty.getText()));
			pst.setFloat(6,Float.parseFloat(txtCPrice.getText()));
			pst.setFloat(7,Float.parseFloat(txtBQty.getText()));
			pst.setFloat(8,Float.parseFloat(txtBPrice.getText()));
			pst.setDate(9,dos);	
			pst.setInt(10,Integer.parseInt(txtStatus.getText()));
			pst.executeUpdate();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Saved...");
			alert.setHeaderText("Success");
			alert.setContentText("Your Data is Saved...");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {	
    	MF="Sound.mp3";
    	doSound(MF);
    	Checkempty();
        LocalDate local=DOS.getValue();
    	java.sql.Date dos=	java.sql.Date.valueOf(local);
        	
        	try {
    			PreparedStatement pst=con.prepareStatement("update customerentry set pic=?,Mobile=?,address=?,Cqty=?,cprice=?,Bqty=?,bprice=?,dos=?,status=? where name=?");
    			pst.setString(10,txtName.getText());
    			pst.setString(1,lblimg.getText());
    			if(txtMobNo.getText().length()==10)
    				pst.setString(2,txtMobNo.getText());
    				else
    				{
    					Alert alert = new Alert(AlertType.ERROR);
    					alert.setTitle("Warning Dialog");
    					alert.setHeaderText("Check Mobile No.");
    					alert.setContentText("Invalid Mobile  No.!");
    					alert.showAndWait();
    				}
    			pst.setString(3,txtAddress.getText());
    			pst.setFloat(4,Float.parseFloat(txtCQty.getText()));
    			pst.setFloat(5,Float.parseFloat(txtCPrice.getText()));
    			pst.setFloat(6,Float.parseFloat(txtBQty.getText()));
    			pst.setFloat(7,Float.parseFloat(txtBPrice.getText()));
    			pst.setDate(8,dos);
    			pst.setInt(9,Integer.parseInt(txtStatus.getText()));
    			pst.executeUpdate();
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("INFO");
        		alert.setHeaderText("Data is Updated...");
        		alert.setContentText("Updation Succesful");
        		alert.showAndWait();
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
        
    }
}
