/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vicemarks;

import java.awt.Checkbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

/**
 *
 * @author KAMDEM VADECE
 */
public class tableviewwithcheckbox {
    String coursename = new String();
    CheckBox cb = new CheckBox();
    String lecturer = new String();

    tableviewwithcheckbox(String coursename, CheckBox cb,String lecturer) {
        this.coursename = coursename;
        this.cb = cb;
        this.lecturer = lecturer;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public CheckBox getCb() {
        return cb;
    }

    public void setCh(CheckBox cb) {
        this.cb = cb;
    }
    
}
