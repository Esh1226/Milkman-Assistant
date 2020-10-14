/**
 * Sample Skeleton for 'RegularEntryView.fxml' Controller Class
 */

package VariationEntry;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import connectdb.ConnectionDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class RegularEntryViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lstALL"
    private ListView<String> lstALL; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtCQty"
    private TextField txtCQty; // Value injected by FXMLLoader

    @FXML // fx:id="txtBQty"
    private TextField txtBQty; // Value injected by FXMLLoader

    @FXML // fx:id="DATE"
    private DatePicker DATE; // Value injected by FXMLLoader
    
    Connection con=ConnectionDB.doconnect();
    String MF="";
    
    @FXML
    void doClose(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	txtName.getScene().getWindow().hide();
    }
    
    @FXML
    void doDelete(ActionEvent event) 
    {
    	MF="Bomb.mp3";
    	doSound(MF);
    	ObservableList<String> selectedItems =  lstALL.getSelectionModel().getSelectedItems();
        lstALL.getItems().retainAll(selectedItems);
    }

    @FXML
    void doSave(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst=con.prepareStatement("Insert into routine values(?,?,?,?)");
			pst.setString(1,txtName.getText());
			pst.setFloat(2,Float.parseFloat(txtCQty.getText()));
			pst.setFloat(3,Float.parseFloat(txtBQty.getText()));
			if(DATE.getEditor().getText().isEmpty()==false){
			LocalDate local=DATE.getValue();
        	java.sql.Date dov=	java.sql.Date.valueOf(local);
			pst.setDate(4,dov);
			}
			else{
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("ERROR");
	    		alert.setHeaderText("Please fill all details...");
	    		alert.setContentText("Date is invalid!");
	    		alert.showAndWait();    	
	    	
			}
			pst.executeUpdate();
			lstALL.getItems().remove(txtName.getText());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Saved...");
			alert.setHeaderText("Success");
			alert.setContentText("Your Data is Saved...");
			alert.showAndWait();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doSelect(MouseEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	lstALL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	if(event.getClickCount()==2)
    	{
    		MF="Sound.mp3";
    		doSound(MF);
    	
    		try {
				PreparedStatement pst=con.prepareStatement("select * from customerentry where name=?");
				pst.setString(1,lstALL.getSelectionModel().getSelectedItem());
				ResultSet rs=pst.executeQuery();
				boolean jasus=false;
				while(rs.next())
	       		{
	       			jasus=true;
	       			txtName.setText(rs.getString("name"));
	       			txtCQty.setText(rs.getFloat("CQty")+"");
	       			txtBQty.setText(rs.getFloat("BQty")+"");
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
    	lstALL.getItems().clear();
    	try {
			PreparedStatement pst = con.prepareStatement("select * from Customerentry");
        	ResultSet rs= pst.executeQuery();
       		while(rs.next())
       		{
       		String name=rs.getString("name");
       		lstALL.getItems().add(name);	
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
