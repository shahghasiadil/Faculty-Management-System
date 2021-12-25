package com.faculty.fxcontrollers.student;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Address;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditStudentController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private  TextField txtLastName;

    @FXML
    private DatePicker birthDate;

    @FXML
    private TextField txtSSN;


    @FXML
    private ComboBox<Address> cmbAddress;


    private  static Student editStudent;
    FormValidator studentFormValidator = new FormValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        if (null!=EditStudentController.editStudent) {

            fillControls();
        }
    }
    public  static  void setEditStudent(Student student){
        EditStudentController.editStudent = student;
    }

     void fillControls (){
        cmbAddress.getItems().addAll(DBUtil.getAll(Address.class));
        txtFirstname.setText(editStudent.getStd_first_name());
        txtLastName.setText(editStudent.getLast_name());
        txtSSN.setText(String.format("%,d",editStudent.getSSN()));
//        txtSSN.setText(String.valueOf(Long.valueOf(editStudent.getSSN())));
        birthDate.setValue((editStudent.getBirth_date()));
        cmbAddress.setValue(editStudent.getAddress());

         birthDate.setDayCellFactory(datePicker -> new DateCell() {
             @Override
             public void updateItem(LocalDate date, boolean empty) {
                 super.updateItem(date, empty);

                 if (null != date && date.isAfter(LocalDate.now())) {
                     setDisable(true);
                 }
             }
         });

    };



    @FXML
     void saveBtnAction(ActionEvent event) {
        if(! studentFormValidator.isValid()){

            System.err.println("Student info is not Valid!!!");
            return;
        }
        editStudent.setStd_first_name(txtFirstname.getText());
        editStudent.setLast_name(txtLastName.getText());
        editStudent.setSSN(Integer.valueOf(txtSSN.getText()));
        editStudent.setBirth_date(birthDate.getValue());
        editStudent.setAddress(cmbAddress.getValue());
        DBUtil.update(editStudent);
        backBtnAction(event);
    }

    @FXML
     void backBtnAction(javafx.event.ActionEvent event) {
//        EditStudentController.editStudent=null;
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Student/StudentScreen.fxml"));
    }
    @FXML
     void cancelBtnAction(javafx.event.ActionEvent event) {
        backBtnAction(event);
    }
    void setRules (){

        studentFormValidator.addRule("firstName", new ValidationRule(
                txtFirstname, Pattern.compile("^[a-z]{3,30}$", Pattern.CASE_INSENSITIVE)));

        studentFormValidator.addRule("lastName", new ValidationRule(
                txtLastName, Pattern.compile("^[a-z]{0,30}$", Pattern.CASE_INSENSITIVE)));

//
        ValidationRule AddressValidationRule = new ValidationRule(cmbAddress, "");

        AddressValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox cmbAddress = (ComboBox) comboBox;

                return cmbAddress.getValue() != null;
            }

            return false;
        });

        ValidationRule dateValidationRule = new ValidationRule(birthDate, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && ! dp.getValue().isAfter(LocalDate.now());
            }

            return false;
        });
        studentFormValidator.addRule("BirthDate",dateValidationRule);
        studentFormValidator.addRule("SSN", new ValidationRule(txtSSN, "^\\d{1,11}$"));
        studentFormValidator.addRule("Address",AddressValidationRule);
    }

}



