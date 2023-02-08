/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentfeefilter.pkg0.pkg1;

import java.awt.Checkbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author KAMDEM VADECE
 */
public class tableviewwithcheckbox {
    String coursename = new String();
    CheckBox cb = new CheckBox();
    TextField lecturer = new TextField();

    tableviewwithcheckbox(String coursename, CheckBox cb, TextField lecturer) {
        this.coursename = coursename;
        this.cb = cb;
        this.lecturer = lecturer;
    }

    public TextField getLecturer() {
        return lecturer;
    }

    public void setLecturer(TextField lecturer) {
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
