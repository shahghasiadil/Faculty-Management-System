package com.faculty.fxcontrollers.marks;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Attendence;
import com.faculty.model.Marks;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public  class EditMarksController implements Initializable {
    @FXML
    private ComboBox<Attendence> cmbCourse;

    @FXML
    private ComboBox<Student> cmbStudent;

    @FXML
    private TextField txtMid;
    @FXML
    private TextField txtFinalMarks;
    FormValidator validator = new FormValidator();
    private static Marks editSelectedMarks;

    public  static  void setEditSelectedMarks(Marks editSelectedMarks){

        EditMarksController.editSelectedMarks=editSelectedMarks;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        fillControls();
    }
    @FXML
    void saveBtnAction(ActionEvent event){
        if (!validator.isValid()){
            System.err.println("Marks info is not Valid!!!");
            return;
        }
    editSelectedMarks.setMid_term(Long.parseLong(txtMid.getText()));
    editSelectedMarks.setFinal_m(Long.parseLong(txtFinalMarks.getText()));
    editSelectedMarks.setStudent(cmbStudent.getSelectionModel().getSelectedItem());
    editSelectedMarks.setAttendence(cmbCourse.getSelectionModel().getSelectedItem());

    DBUtil.update(editSelectedMarks);
    backBtnAction(event);
    }

    @FXML
    void backBtnAction(ActionEvent event){

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Marks/MarksScreen.fxml"));
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Marks/MarksScreen.fxml"));

    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    void setRules(){
        ValidationRule StudentValidationRule = new ValidationRule(cmbStudent, "");
        StudentValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox customerComboBox = (ComboBox) comboBox;

                return customerComboBox.getValue() != null;
            }

            return false;
        });


        ValidationRule AttendenceValiditionRule = new ValidationRule(cmbCourse,"");
        AttendenceValiditionRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox customerComboBox = (ComboBox) comboBox;

                return customerComboBox.getValue() != null;
            }

            return false;
        });
        Pattern Mid_term = Pattern.compile("^[0-9]|1[0-9]|2[0-0]$", Pattern.CASE_INSENSITIVE);
//        Pattern Mid_term = Pattern.compile("[0-9 &&[^20]] {1,3}$", Pattern.CASE_INSENSITIVE);
        Pattern Final_Marks= Pattern.compile("^[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|6[0-9]|7[0-9]|8[0-0]|$", Pattern.CASE_INSENSITIVE);
        validator.addRule("Mid_term",new ValidationRule(txtMid,Mid_term));
        validator.addRule("Final_Marks",new ValidationRule(txtFinalMarks,Final_Marks));
        validator.addRule("AttendenceFld",AttendenceValiditionRule);
        validator.addRule("studentsFld",StudentValidationRule);

    }

    void fillControls(){

        cmbCourse.getItems().addAll(DBUtil.getAll(Attendence.class));
        cmbStudent.getItems().addAll(DBUtil.getAll(Student.class));

        cmbCourse.setValue(editSelectedMarks.getAttendence());
        cmbStudent.setValue(editSelectedMarks.getStudent());
        txtMid.setText(String.format("%,d",editSelectedMarks.getMid_term()));
        txtFinalMarks.setText(String.format("%,d",editSelectedMarks.getFinal_m()));

    }
}
