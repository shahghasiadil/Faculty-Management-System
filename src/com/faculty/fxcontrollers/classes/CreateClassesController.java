package com.faculty.fxcontrollers.classes;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Classes;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateClassesController  implements Initializable {

    @FXML
    private TextField txtClassName;

    @FXML
    private JFXTimePicker dtTime;

    @FXML
    private  TextField txtRoomNo;
    FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setRules();


    }

    @FXML
    void backBtnAction (ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/ClassesScreen.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("classes/ClassesScreen.fxml"));
    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    @FXML
    void createBtnAction(ActionEvent event){

        if (!validator.isValid() || dtTime.getValue()==null){

            System.err.println("Class info is not valid...");
            return;
        }

        Classes classes = new Classes();
        classes.setName(txtClassName.getText());
        classes.setRoom_no(txtRoomNo.getText());
        classes.setTime(dtTime.getValue().toString());
        DBUtil.save(classes);
        backBtnAction(event);

    }



    void setRules(){

        Pattern namePattern= Pattern.compile("^[a-z 0-9]{2,15}$",Pattern.CASE_INSENSITIVE);
        Pattern timePattern = Pattern.compile("^[amp 0-9]{1,12}$", Pattern.CASE_INSENSITIVE);
        Pattern roomNoPattern = Pattern.compile("^[a-z 1-9]{1,5}$",Pattern.CASE_INSENSITIVE);
        validator.addRule("className", new ValidationRule(txtClassName,namePattern));
//        validator.addRule("time",new ValidationRule(txtTime,timePattern));
        validator.addRule("room_no",new ValidationRule(txtRoomNo,roomNoPattern));
    }
}
