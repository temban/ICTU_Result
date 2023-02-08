/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfeefilter.pkg0.pkg1;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author KAMDEM VADECE
 */
public class alltableviewwithcheckbox {
 String sname = new String();
 String smatricule = new String();
 //sname,smatricule,se_mail,sphonenumber,sfeestatus
 CheckBox mfcheckboxcoursename = new CheckBox();
String  sfeestatus = new String();
Integer  id = new Integer(0);
 
 public alltableviewwithcheckbox(String sname,String smatricule, CheckBox mfcheckboxcoursename, String  sfeestatus, Integer  id ) {
    this.sname = sname;
    this.smatricule = smatricule;
    this.mfcheckboxcoursename = mfcheckboxcoursename; 
    this.sfeestatus = sfeestatus;
    this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSfeestatus() {
        return sfeestatus;
    }

    public void setSfeestatus(String sfeestatus) {
        this.sfeestatus = sfeestatus;
    }

 
 
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSmatricule() {
        return smatricule;
    }

    public void setSmatricule(String smatricule) {
        this.smatricule = smatricule;
    }

    public CheckBox getMfcheckboxcoursename() {
        return mfcheckboxcoursename;
    }

    public void setMfcheckboxcoursename(CheckBox mfcheckboxcoursename) {
        this.mfcheckboxcoursename = mfcheckboxcoursename;
    }

}
