/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vicemarks;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author KAMDEM VADECE
 */
public class alltableviewwithtextfield {
 String sname = new String();
 String smatricule = new String();
 //sname,smatricule,se_mail,sphonenumber,sfeestatus
ComboBox grade = new ComboBox();
Integer  id = new Integer(0);
String acgrade = new String();
 
 public alltableviewwithtextfield(String sname,String smatricule, ComboBox  grade, Integer  id, String acgrade ) {
    this.sname = sname;
    this.smatricule = smatricule;
    this.grade = grade;
    this.id = id;
    this.acgrade = acgrade;
    }

    public String getAcgrade() {
        return acgrade;
    }

    public void setAcgrade(String acgrade) {
        this.acgrade = acgrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ComboBox getGrade() {
        return grade;
    }

    public void setGrade(ComboBox grade) {
        this.grade = grade;
    }

}
