/**
 * Sample Skeleton for 'RETableView.fxml' Controller Class
 */

package VariationLog;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import connectdb.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RETableViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tbl"
    private TableView<RegularBean> tbl; // Value injected by FXMLLoader
    
    @FXML // fx:id="comboid"
    private ComboBox<String> comboid; // Value injected by FXMLLoader
    
    @FXML // fx:id="dateSD"
    private DatePicker dateSD; // Value injected by FXMLLoader

    @FXML // fx:id="dateED"
    private DatePicker dateED; // Value injected by FXMLLoader

    @FXML
    void doExport(ActionEvent event) 
    {
    	MF="Sound.mp3";
    	doSound(MF);
    	Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Variations");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tbl.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tbl.getColumns().get(j).getText());
        }

        for (int i = 0; i < tbl.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tbl.getColumns().size(); j++) {
                if(tbl.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(tbl.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut;
		try { 
			FileChooser fileChooser = new FileChooser();
	    	File selectedFile = fileChooser.showSaveDialog(null);
	    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel", "*.xls", "*.xlsx"));
	    	if (selectedFile != null) 
	    	{
			fileOut = new FileOutputStream(selectedFile.getName());
			workbook.write(fileOut);
			fileOut.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("File Exported..");
			alert.setContentText("Your file is exported to Excel.");
			alert.showAndWait();
	    	}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void doFetchWithCombo(ActionEvent event) 
    {
    	MF="Click Sound.mp3";
    	doSound(MF);
    	try {
    		LocalDate localsd=LocalDate.now();
        	LocalDate localed=LocalDate.now();
        	if(dateSD.getEditor().getText().isEmpty() || dateSD.getEditor().getText().isEmpty()){
    			Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Empty fields");
				alert.setContentText("Fill data..");
				alert.showAndWait();
    		}
    		else{
    			localsd=dateSD.getValue();
    			localed=dateED.getValue();
            	java.sql.Date SD=Date.valueOf(localsd);
            	java.sql.Date ED=Date.valueOf(localed);
        		
    		PreparedStatement pst=con.prepareStatement("Select * from routine where name=? and ?<=VarDate<=?");
			pst.setString(1,comboid.getSelectionModel().getSelectedItem());
			pst.setDate(2,SD);
			pst.setDate(3,ED);
			doLoad(pst);
			doFillClmn();
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    Connection con=ConnectionDB.doconnect();
    ObservableList<RegularBean> list;
    String MF="";
    
    @FXML
    void doFetchAll(ActionEvent event) 
    {
    	dateSD.getEditor().setText("");
    	dateED.getEditor().setText("");
    	comboid.getEditor().setText("");
    	MF="Sound.mp3";
    	doSound(MF);
    	try {
			PreparedStatement pst = con.prepareStatement("Select * from routine");
	    	doLoad(pst);
	    	doFillClmn();
    	}
    	catch (SQLException e) {
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
    
    void doFillClmn()
    {
    	TableColumn<RegularBean, String> name= new TableColumn<>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("Name"));
    	
    	TableColumn<RegularBean, String> CQty= new TableColumn<>("CQTY");
    	CQty.setCellValueFactory(new PropertyValueFactory<>("CQty"));
    	
    	TableColumn<RegularBean, String> BQty= new TableColumn<>("BQTY");
    	BQty.setCellValueFactory(new PropertyValueFactory<>("BQty"));
    	
    	TableColumn<RegularBean, String> VD= new TableColumn<>("Date Of Variation");
    	VD.setCellValueFactory(new PropertyValueFactory<>("DOV"));
    	
    	tbl.getColumns().clear();
    	tbl.getColumns().addAll(name,CQty,BQty,VD);
    	tbl.setItems(list);

    }

    void doLoad(PreparedStatement pst)
    {
    	list=FXCollections.observableArrayList();
    	
		try {
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RegularBean rb=new RegularBean();
				rb.setName(rs.getString("Name"));
			rb.setCQty(rs.getFloat("CVar"));
			rb.setBQty(rs.getFloat("BVar"));
			rb.setDOV(rs.getDate("VarDate")+"");
			System.out.println(rs.getString("Name"));
			list.add(rb);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    void doLoadCombo()
    {
    	comboid.setOnKeyReleased(new EventHandler<KeyEvent>() 
        {
            public void handle(final KeyEvent keyEvent) 
            {
                handleEvent(keyEvent);
            }

			private void handleEvent(KeyEvent keyEvent) {
				MF="Typing.mp3";
				doSound(MF);
				
			}
        });

    	try {
			PreparedStatement pst=con.prepareStatement("Select distinct name from routine");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				comboid.getItems().add(rs.getString("name"));	
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        doLoadCombo();
    }
}
