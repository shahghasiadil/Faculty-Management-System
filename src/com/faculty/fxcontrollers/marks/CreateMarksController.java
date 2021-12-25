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

public class CreateMarksController implements Initializable {
    @FXML
     private ComboBox<Attendence> cmbCourse;

    @FXML
    private ComboBox<Student> cmbStudent;

    @FXML
            private TextField txtMid;
    @FXML
            private TextField txtFinalMarks;


   private FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
    fillControls();


    }

    void fillControls(){
        cmbCourse.getItems().addAll(DBUtil.getAll(Attendence.class));
        cmbStudent.getItems().addAll(DBUtil.getAll(Student.class));

    }

    @FXML
    void createBtnAction(ActionEvent event){

        if(! validator.isValid()){
            System.err.println("Marks info is not valid!!!");
            return;
        }

        Marks markss = new Marks();
        markss.setAttendence(cmbCourse.getSelectionModel().getSelectedItem());
        markss.setStudent(cmbStudent.getSelectionModel().getSelectedItem());
        markss.setFinal_m(Long.parseLong(txtFinalMarks.getText()));
        markss.setMid_term(Long.parseLong(txtMid.getText()));
        DBUtil.saveOrUpdate(markss);
        backBtnAction(event);

    }

    @FXML
    void backBtnAction(ActionEvent event){

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
                ComboBox studentComboBox = (ComboBox) comboBox;

                return studentComboBox.getValue() != null;
            }

            return false;
        });


        ValidationRule AttendenceValiditionRule = new ValidationRule(cmbCourse,"");
        AttendenceValiditionRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox AttendanceComboBox = (ComboBox) comboBox;

                return AttendanceComboBox.getValue() != null;
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
}
