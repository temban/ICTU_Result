/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vicemarks;

import dataaccesslayer.*;
import java.awt.Checkbox;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
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
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

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
    private TableColumn<tableviewwithcheckbox, String> lecturer;
    @FXML
    private TextField sendalltextfield;
    @FXML
    private TextField password;
    @FXML
    private TextField subject;

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
                coursenamelist.add(new tableviewwithcheckbox(cname,cb,lect));
                
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
    private void btnsendall(ActionEvent event) throws SQLException, AddressException, MessagingException {
      labelnotification.setText(" ");
      String[] matricule={};
      String[] grade={};
      String[] email={};
      ArrayList<String> matriculelist =  new ArrayList<>(Arrays.asList(matricule));
      ArrayList<String> gradelist =  new ArrayList<>(Arrays.asList(grade));
      ArrayList<String> emaillist =  new ArrayList<>(Arrays.asList(email));
      DataAccessLayer dbl = new DataAccessLayer();
      
      if(!sendalltextfield.getText().isEmpty()){
          if(!password.getText().isEmpty()){
              if(!subject.getText().isEmpty()){
          
      
      
      ResultSet rs[] = dbl.dbreadcourse();
      while(rs[0].next()){
      
          String cn = rs[0].getString("coursename");
          ResultSet rss[]=dbl.dbgetmatriculeemailgrade(cn);
          
          while(rss[0].next() && rss[1].next() && rss[2].next()){
          String ml = rss[0].getString("smatricule");
          String gl = rss[1].getString("sgrade");
          String el = rss[2].getString("se_mail");
          matriculelist.add(ml);
          String glfinal = "COURSE:" +cn+" ------ GRADE:" +gl;
          gradelist.add(glfinal);
          emaillist.add(el);
          matricule = matriculelist.toArray(matricule);
          grade = gradelist.toArray(grade);
          email = emaillist.toArray(email);
  
          }
  
      }
      String senderemail = sendalltextfield.getText();
      String passwordemail = password.getText();
      String subjectemail = subject.getText();
      for(int index=0;index<=matricule.length;index++){
 //Get properties object    
          Properties props = new Properties();    
          
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
          //get Session   
          Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(senderemail, passwordemail);

            }

        });
           // Used to debug SMTP issues
        session.setDebug(true);
          //compose message    
           
           // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(senderemail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email[index]));

            // Set Subject: header field
            message.setSubject(subjectemail);

            // Now set the actual message
            message.setText("YOUR MATRICULE IS:"+matricule[index]+System.lineSeparator()+grade[index]);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");   
         }
              }else{
              labelnotification.setText("PLEASE ENTER EMAIL SUBJECT");
              }
          }else{
              labelnotification.setText("PLEASE ENTER EMAIL PASSWORD");
              }
      }else{
              labelnotification.setText("PLEASE ENTER SENDER EMAIL ADDRESS");
              }  
        
    }

    @FXML
    private void btnaddcourse(ActionEvent event) throws SQLException, IOException {
  
            labelnotification.setText(" ");
        DataAccessLayer dbaccesslayer = new DataAccessLayer();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT EXCEL SHEET");
        File file = fileChooser.showOpenDialog(home.getScene().getWindow());
       
        dbaccesslayer.dbgetcoursenameandlecturerfromexcelsheet(file);
        dbaccesslayer.dbaddcoursetable();
        dbaccesslayer.dbaddcourse();
        dbaccesslayer.dbaddexcelsheet(file);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeFXML.fxml"));
        Parent homepage = loader.load();
        
        Scene homepagescene = new Scene(homepage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(homepagescene);
        stage.setTitle("WELCOME TO STUDENT-FEE FILTER SYSTEM");
        stage.show();
 
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
         
       DataAccessLayer dbaccesslayer = new DataAccessLayer();
        //code to get name to be deleted
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simplegrades.fxml"));
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
    
}
