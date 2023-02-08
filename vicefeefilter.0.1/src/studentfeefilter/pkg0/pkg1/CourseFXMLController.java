/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfeefilter.pkg0.pkg1;

import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.shaded.apache.poi.hssf.usermodel.HSSFRow;
import org.shaded.apache.poi.hssf.usermodel.HSSFSheet;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.shaded.apache.poi.ss.usermodel.Sheet;
import org.shaded.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author KAMDEM VADECE
 */
public class CourseFXMLController implements Initializable {

    @FXML
    private TableView<alltableviewwithcheckbox> mfcoursename;
    @FXML
    private TableColumn<alltableviewwithcheckbox, CheckBox> mfcheckboxcoursename;
    @FXML
    private Label notifgood;
    @FXML
    private Label notifbad;
    @FXML
    private Button save;
    @FXML
    private Button send;
    @FXML
    private TableColumn<alltableviewwithcheckbox, String> sname;
    @FXML
    private TableColumn<alltableviewwithcheckbox, String> smatricule;
    Collections collections;
    ObservableList<alltableviewwithcheckbox> coursedatalist = FXCollections.observableArrayList();
    ObservableList<String> coursenamelist = FXCollections.observableArrayList();
    ObservableList<String> studentenamelist = FXCollections.observableArrayList();
   
    @FXML
    private ComboBox<String> corn;
    @FXML
    private TableColumn<alltableviewwithcheckbox, String> sfeestatus;
    @FXML
    private TableColumn<alltableviewwithcheckbox, Integer> id;
    @FXML
    private ComboBox<String> particularstudent;
    @FXML
    private AnchorPane coursepage;
    @FXML
    private Button back_home;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        particularstudent.setEditable(true);
        corn.setEditable(true);
       try {
            // TODO
             
           
           DataAccessLayer dbl = new DataAccessLayer();
           ResultSet rs[] = dbl.dbreadcourse();
            while(rs[0].next()){
              
                String cname = rs[0].getString("coursename");
                coursenamelist.add(cname);
                
            }
           collections.sort(coursenamelist);
           TextFields.bindAutoCompletion(corn.getEditor(), coursenamelist).setOnAutoCompleted(e -> {
           
               try {
                   notifgood.setText(" ");
                   String cn = corn.getEditor().getText();
                   particularstudent.getItems().clear();
                   readstudent(cn);
                   mfcoursename.getItems().clear();
                   ResultSet rss[] = dbl.dbreadcoursedata(cn);
                   while(rss[0].next() && rss[1].next() && rss[2].next() && rss[3].next()){
                       String sfs = rss[2].getString("sfeestatus");
                       CheckBox mfcheckboxcoursename = new CheckBox();
                       String cname = rss[0].getString("sname");
                       String smatri = rss[1].getString("smatricule");
                       Integer idn = rss[3].getInt("id");
                       coursedatalist.add(new alltableviewwithcheckbox(cname,smatri,mfcheckboxcoursename,sfs,idn));
                   }
                   mfcoursename.setItems(coursedatalist);
                   sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
                   smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
                   mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
                   sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
                   id.setCellValueFactory(new PropertyValueFactory<>("id"));
                   
                   for(alltableviewwithcheckbox checkbox : coursedatalist) {
                       if(checkbox.sfeestatus.equals("PAYED"))
                       {
                           checkbox.mfcheckboxcoursename.setSelected(true);
                       }
                       else{
                           checkbox.mfcheckboxcoursename.setSelected(false);
                       }
                   }     } catch (SQLException ex) {
                   Logger.getLogger(CourseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
           
           
           
           
           });
           corn.setItems(coursenamelist);
        } catch (SQLException ex) {
            Logger.getLogger(HomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 

    @FXML
    private void btnsave(ActionEvent event) throws SQLException {
       DataAccessLayer dbaccesslayer = new DataAccessLayer();
        
       for(alltableviewwithcheckbox table : coursedatalist) 
       {
       if(table.getMfcheckboxcoursename().isSelected()){
       String cn = corn.getValue().toString();   
       String cname = table.getSmatricule();
       String mfstatus = "PAYED";
       dbaccesslayer.dbstudentpaymentstatus(cn, cname, mfstatus);
        notifgood.setText("SAVED ");
       }
       else{
       String cn = corn.getValue().toString();   
       String cname = table.getSmatricule();
       String mfstatus = "MF";
       dbaccesslayer.dbstudentpaymentstatus(cn, cname, mfstatus);
        notifgood.setText("SAVED ");
       }
       }
      
       String cn = corn.getValue();
       String pn = particularstudent.getValue();
       String pnn = particularstudent.getEditor().getText();
       if(pn==null){
        mfcoursename.getItems().clear();
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedata(cn);
            while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
               
                CheckBox mfcheckboxcoursename = new CheckBox();
                String cname = rs[0].getString("sname");
                String smatri = rs[1].getString("smatricule");
                String sfs = rs[2].getString("sfeestatus");
                Integer idn = rs[3].getInt("id");
                coursedatalist.add(new alltableviewwithcheckbox(cname,smatri,mfcheckboxcoursename,sfs,idn));
                
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
            sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            
             for(alltableviewwithcheckbox checkbox : coursedatalist) {
         if(checkbox.sfeestatus.equals("PAYED"))
         {
         checkbox.mfcheckboxcoursename.setSelected(true);
         }
         else{
         checkbox.mfcheckboxcoursename.setSelected(false);
         }
         }
       }
       if(pn!=null){
       notifgood.setText(" ");
        particularstudent.getItems().clear();
        readstudent(cn);
        mfcoursename.getItems().clear();
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedata(cn);
            while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
               String sfs = rs[2].getString("sfeestatus");
                CheckBox mfcheckboxcoursename = new CheckBox();
                String cname = rs[0].getString("sname");
                String smatri = rs[1].getString("smatricule");
                Integer idn = rs[3].getInt("id");
                coursedatalist.add(new alltableviewwithcheckbox(cname,smatri,mfcheckboxcoursename,sfs,idn));
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
            sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            
             for(alltableviewwithcheckbox checkbox : coursedatalist) {
         if(checkbox.sfeestatus.equals("PAYED"))
         {
         checkbox.mfcheckboxcoursename.setSelected(true);
         }
         else{
         checkbox.mfcheckboxcoursename.setSelected(false);
         }
         }
        
       
       }
       
    }

    @FXML
    private void btnsend(ActionEvent event) throws IOException, SQLException {
         notifgood.setText(" ");
          String cn = corn.getValue();
          DataAccessLayer dbl = new DataAccessLayer();
          ResultSet rs = dbl.dbexportcourse(cn);
          
        
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=  workbook.createSheet("FEE_RESULTS");;
        HSSFRow row0= sheet.createRow(0);
        row0.createCell(0).setCellValue("COURSE NAME");
        row0.createCell(1).setCellValue(cn.toUpperCase());
        row0.createCell(2).setCellValue("");
        row0.createCell(3).setCellValue("");
        row0.createCell(4).setCellValue("");
        row0.createCell(5).setCellValue("");
        HSSFRow row1= sheet.createRow(1);
        row1.createCell(0).setCellValue("LECTURER");
        row1.createCell(1).setCellValue(dbl.dbreadlecturer(cn));
        row1.createCell(2).setCellValue("");
        row1.createCell(3).setCellValue("");
        row1.createCell(4).setCellValue("");
        row1.createCell(5).setCellValue("");
        HSSFRow row2= sheet.createRow(2);
        row2.createCell(0).setCellValue("ID");
        row2.createCell(1).setCellValue("STUDENT NAME");
        row2.createCell(2).setCellValue("STUDENT MATRICULE");
        row2.createCell(3).setCellValue("EMAIL");
        row2.createCell(4).setCellValue("PHONE NUMBER");
        row2.createCell(5).setCellValue("FEE STATUS");
        int index = 3;
        while(rs.next()){
         HSSFRow rowindex= sheet.createRow(index);
        rowindex.createCell(0).setCellValue(rs.getString("id"));
        rowindex.createCell(1).setCellValue(rs.getString("sname"));
        rowindex.createCell(2).setCellValue(rs.getString("smatricule"));
        rowindex.createCell(3).setCellValue(rs.getString("se_mail"));
        rowindex.createCell(4).setCellValue(rs.getString("sphonenumber"));
        rowindex.createCell(5).setCellValue(rs.getString("sfeestatus"));
        index++; 
        }
        
        DirectoryChooser directorychooser = new DirectoryChooser();
       
        directorychooser.setTitle("SELECT LOCATION TO STORE MF SHEET");
        File file = directorychooser.showDialog(coursepage.getScene().getWindow());
        
        FileOutputStream f = new FileOutputStream(new File(file.getAbsolutePath())+"/"+cn+"_MF_RESULT_SHEET.xls");
        
        workbook.write(f);
        f.close();
        
        
    notifgood.setText("SENT");

        
        
          }
     

    @FXML
    private void btncorn(ActionEvent event) throws SQLException {
         notifgood.setText(" ");
        String cn = corn.getValue();
        particularstudent.getItems().clear();
        readstudent(cn);
        mfcoursename.getItems().clear();
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedata(cn);
            while(rs[0].next() && rs[1].next() && rs[2].next() && rs[3].next()){
               String sfs = rs[2].getString("sfeestatus");
                CheckBox mfcheckboxcoursename = new CheckBox();
                String cname = rs[0].getString("sname");
                String smatri = rs[1].getString("smatricule");
                Integer idn = rs[3].getInt("id");
                coursedatalist.add(new alltableviewwithcheckbox(cname,smatri,mfcheckboxcoursename,sfs,idn));
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
            sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            
             for(alltableviewwithcheckbox checkbox : coursedatalist) {
         if(checkbox.sfeestatus.equals("PAYED"))
         {
         checkbox.mfcheckboxcoursename.setSelected(true);
         }
         else{
         checkbox.mfcheckboxcoursename.setSelected(false);
         }
         }
        
    }

   /* private void btsubmit(TableColumn.CellEditEvent<alltableviewwithcheckbox, String> event) throws SQLException {
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        String mf; 
        String cn1;   
       String cname1;
       
         for(alltableviewwithcheckbox table : coursedatalist){
        mf = sfeestatus.getText();
        cn1 = corn.getValue(); 
        cname1 = table.getSmatricule();
       dbaccesslayer.dbstudentpaymentstatus(cn1, cname1, mf);
        notifgood.setText("CHANGE SUCCEEDED ");
    } 
    }*/

    @FXML
    private void btnparticularstudent(ActionEvent event) throws SQLException {
         notifgood.setText(" ");
        
       String cn = corn.getValue();
       String ps = particularstudent.getValue();
        mfcoursename.getItems().clear();
            DataAccessLayer dbl = new DataAccessLayer();
            ResultSet rs[] = dbl.dbreadcoursedataparticularstudent(cn,ps);
            while(rs[0].next() && rs[1].next() && rs[2].next()){
               
                CheckBox mfcheckboxcoursename = new CheckBox();
                String ps1 = particularstudent.getValue();
                String smatri = rs[0].getString("smatricule");
                String sfs = rs[1].getString("sfeestatus");
                Integer idn = rs[2].getInt("id");
                coursedatalist.add(new alltableviewwithcheckbox(ps1,smatri,mfcheckboxcoursename,sfs,idn));
                
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
            sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            
       for(alltableviewwithcheckbox checkbox : coursedatalist) {
         if(checkbox.sfeestatus.equals("PAYED"))
         {
         checkbox.mfcheckboxcoursename.setSelected(true);
         }
         else{
         checkbox.mfcheckboxcoursename.setSelected(false);
         }
         }
        
     
        
    }

public void readstudent(String c) throws SQLException{
    DataAccessLayer dbl = new DataAccessLayer();
 ResultSet rss = dbl.dbreadstudent(c);
            while(rss.next()){
              
                String sname = rss.getString("sname");
              studentenamelist.add(sname);
                
            }
           collections.sort(studentenamelist);
           particularstudent.setValue(null);
            TextFields.bindAutoCompletion(particularstudent.getEditor(), studentenamelist).setOnAutoCompleted(e -> {
           
        try {
            notifgood.setText(" ");
            
            String cn = corn.getValue();
            String ps = particularstudent.getEditor().getText();
            mfcoursename.getItems().clear();
            ResultSet rs[] = dbl.dbreadcoursedataparticularstudent(cn,ps);
            while(rs[0].next() && rs[1].next() && rs[2].next()){
               
                CheckBox mfcheckboxcoursename = new CheckBox();
                String ps1 = particularstudent.getValue();
                String smatri = rs[0].getString("smatricule");
                String sfs = rs[1].getString("sfeestatus");
                Integer idn = rs[2].getInt("id");
                coursedatalist.add(new alltableviewwithcheckbox(ps1,smatri,mfcheckboxcoursename,sfs,idn));
                
            }   
            mfcoursename.setItems(coursedatalist);
            sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
            smatricule.setCellValueFactory(new PropertyValueFactory<>("smatricule"));
            mfcheckboxcoursename.setCellValueFactory(new PropertyValueFactory<>("mfcheckboxcoursename"));
            sfeestatus.setCellValueFactory(new PropertyValueFactory<>("sfeestatus"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            
            for(alltableviewwithcheckbox checkbox : coursedatalist) {
                if(checkbox.sfeestatus.equals("PAYED"))
                {
                    checkbox.mfcheckboxcoursename.setSelected(true);
                }
                else{
                    checkbox.mfcheckboxcoursename.setSelected(false);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
           
           
           
           
           });
           
           particularstudent.setItems(studentenamelist);
        
      
}   

    @FXML
    private void btnback_home(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WELCOME TO STUDENT-FEE FILTER SYSTEM");
        stage.show(); 
        
    }

}
