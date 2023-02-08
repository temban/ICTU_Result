/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfeefilter.pkg0.pkg1;

import java.awt.Checkbox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class HomeFXMLController implements Initializable {

    @FXML
    private Button sendall;
    @FXML
    private TableColumn<tableviewwithcheckbox, String> coursename;
    @FXML
    private CheckBox selectallcourse_checkbox;
    @FXML
    private Button addcourse;
    @FXML
    private Button deletecourse;
    @FXML
    private Label labelnotification;
    @FXML
    private TextField textnewcourse;
    @FXML
    private TableView<tableviewwithcheckbox> tableview;
    @FXML
    private TableColumn<tableviewwithcheckbox, CheckBox> cb;
    
    ObservableList<tableviewwithcheckbox> coursenamelist = FXCollections.observableArrayList();
    @FXML
    private Button work;
    @FXML
    private MenuItem refresh;
    @FXML
    private AnchorPane home;
    @FXML
    private TableColumn<tableviewwithcheckbox, TextField> lecturer;
    @FXML
    private Button savebutton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            // TODO
             
           
           DataAccessLayer dbl = new DataAccessLayer();
           ResultSet rs[] = dbl.dbreadcourse();
            while(rs[0].next() && rs[1].next()){
               
                CheckBox cb = new CheckBox();
                String cname = rs[0].getString("coursename");
                String lect = rs[1].getString("lecturer");
                TextField lecturer = new TextField(lect);
                coursenamelist.add(new tableviewwithcheckbox(cname,cb,lecturer));
                
            }   
            tableview.setItems(coursenamelist);
            coursename.setCellValueFactory(new PropertyValueFactory<>("coursename"));
            cb.setCellValueFactory(new PropertyValueFactory<>("cb"));
            lecturer.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        } catch (SQLException ex) {
            Logger.getLogger(HomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    @FXML
    private void btnsendall(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
           
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("SELECT LOCATION TO STORE MF SHEET");
        File file = directorychooser.showDialog(home.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/MfAllResultSheet.xls");
        
             int rownum0 = 0;
             int rownum1 = rownum0+1;
             int rownum2 = rownum1+1;
             int rownum3 = rownum2+1;
             int index = rownum3+1;
             HSSFWorkbook workbook=new HSSFWorkbook();
             HSSFSheet sheet=  workbook.createSheet("FEE_RESULTS");;
        for(tableviewwithcheckbox table : coursenamelist) {
          table.cb.setSelected(true);selectallcourse_checkbox.setSelected(true); 
         if(selectallcourse_checkbox.isSelected())
         {
       labelnotification.setText(" ");
       String cn = table.getCoursename();    
        DataAccessLayer dbl = new DataAccessLayer();
        
        ResultSet rs = dbl.dbexportcourse(cn);
          
       
        HSSFRow row0= sheet.createRow(rownum0);
        row0.createCell(0).setCellValue("");
        row0.createCell(1).setCellValue("");
        row0.createCell(2).setCellValue("");
        row0.createCell(3).setCellValue("");
        row0.createCell(4).setCellValue("");
        row0.createCell(5).setCellValue("");
        HSSFRow row1= sheet.createRow(rownum1);
        row1.createCell(0).setCellValue("COURSE NAME");
        row1.createCell(1).setCellValue(cn.toUpperCase());
        row1.createCell(2).setCellValue("");
        row1.createCell(3).setCellValue("");
        row1.createCell(4).setCellValue("");
        row1.createCell(5).setCellValue("");
        HSSFRow row2= sheet.createRow(rownum2);
        row2.createCell(0).setCellValue("LECTURER");
        row2.createCell(1).setCellValue(dbl.dbreadlecturer(cn));
        row2.createCell(2).setCellValue("");
        row2.createCell(3).setCellValue("");
        row2.createCell(4).setCellValue("");
        row2.createCell(5).setCellValue("");
        HSSFRow row3= sheet.createRow(rownum3);
        row3.createCell(0).setCellValue("ID");
        row3.createCell(1).setCellValue("STUDENT NAME");
        row3.createCell(2).setCellValue("STUDENT MATRICULE");
        row3.createCell(3).setCellValue("EMAIL");
        row3.createCell(4).setCellValue("PHONE NUMBER");
        row3.createCell(5).setCellValue("FEE STATUS");
        
        while(rs.next()){
         HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("sname"));
        rowindex.createCell(2).setCellValue(rs.getString("smatricule"));
        rowindex.createCell(3).setCellValue(rs.getString("se_mail"));
        rowindex.createCell(4).setCellValue(rs.getString("sphonenumber"));
        rowindex.createCell(5).setCellValue(rs.getString("sfeestatus"));
        
        index++;
        rownum0++;
        rownum1++;
        rownum2++;
        rownum3++;
       
        }
        
        
         } table.cb.setSelected(false);selectallcourse_checkbox.setSelected(false);
         }
        workbook.write(f); 
        
       
        f.close();
         
        
    }


    @FXML
    private void btnselectallcourse_checkbox(ActionEvent event) {
         for(tableviewwithcheckbox checkbox : coursenamelist) {
         if(selectallcourse_checkbox.isSelected())
         {
         checkbox.cb.setSelected(true);
         }
         else{
         checkbox.cb.setSelected(false);
         }
         }
        
        
    }

    @FXML
    private void btnaddcourse(ActionEvent event) throws SQLException, IOException {
        String coursen = textnewcourse.getText();
        if(!coursen.isEmpty()){
            labelnotification.setText(" ");
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT EXCEL SHEET");
        File file = fileChooser.showOpenDialog(home.getScene().getWindow());
       
        
        dbaccesslayer.dbaddcoursetable(coursen);
        dbaccesslayer.dbaddcourse(coursen);
        dbaccesslayer.dbaddexcelsheet(coursen, file);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WELCOME TO STUDENT-FEE FILTER SYSTEM");
        stage.show();
        }else{
         labelnotification.setText("PLEASE ENTER A COURSE NAME");
        }
    }

    @FXML
    private void btndeletecourse(ActionEvent event) throws SQLException, IOException {
       
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        //code to get name to be deleted
       for(tableviewwithcheckbox table : coursenamelist) 
       {
       if(table.getCb().isSelected()){
           labelnotification.setText(" ");
       String cname = table.getCoursename();
       dbaccesslayer.dbdeletecourse(cname);
       dbaccesslayer.dbdeletecoursetable(cname);
       
       }
       else{
       labelnotification.setText("PLEASE SELECT A COURSE");
       }
       }
     FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WELCOME TO STUDENT-FEE FILTER SYSTEM");
        stage.show();
}

    @FXML
    private void btnwork(ActionEvent event) throws SQLException, IOException {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WORKING SPACE");
        stage.show(); 
    }

    @FXML
    private void btnrefresh(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("homeFXML.fxml"));
        home.getChildren().setAll(pane);
    }

    @FXML
    private void btnsavebutton(ActionEvent event) throws SQLException, IOException {
          DataAccessLayer dbaccesslayer = new DataAccessLayer();
        
       for(tableviewwithcheckbox table : coursenamelist) 
       {
       if(table.lecturer.getText()!=null){
      
       String coursen = table.getCoursename();
       String lecturer = table.lecturer.getText();
       dbaccesslayer.dbaddlecturertocourse(lecturer,coursen);
       
       
       AnchorPane pane = FXMLLoader.load(getClass().getResource("homeFXML.fxml"));
        home.getChildren().setAll(pane);
        }else{
         labelnotification.setText("PLEASE ENTER LECTURER NAME");
        }
       }
       
    }
    
}
