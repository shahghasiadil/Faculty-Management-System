package com.faculty.fxcontrollers.student;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Address;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateStudentController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private  TextField txtLastName;

    @FXML
    private JFXDatePicker birthDate;

    @FXML
    private TextField txtSSN;

    @FXML
    private   ComboBox<Address> cmbAddress;


    FormValidator studentFormValidator = new FormValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setControlls();
        setRules();

    }

    @FXML
    public void backBtnAction(javafx.event.ActionEvent actionEvent) throws Exception{
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Student/StudentScreen.fxml"));
    }
    @FXML
    void cancelBtnAction(ActionEvent event) throws Exception {
        backBtnAction(event);
    }

    @FXML
    void createBtnAction(ActionEvent event) throws Exception {

        if (!studentFormValidator.isValid()){

            System.err.println("Student info is not Valid!!!");
            return;
        }
        Student student = new Student();
        student.setStd_first_name(txtFirstname.getText());
        student.setLast_name(txtLastName.getText());
        student.setBirth_date((birthDate.getValue()));
        student.setSSN(Integer.parseInt(txtSSN.getText()));
        student.setAddress(cmbAddress.getSelectionModel().getSelectedItem());

        DBUtil.save(student);

        backBtnAction(event);

    }


    private  void  resetControlls (){
        txtFirstname.clear();
        txtLastName.clear();
        txtSSN.clear();
    }

    void setControlls(){
        cmbAddress.getItems().addAll(DBUtil.getAll(Address.class));
        birthDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
    }
    void setRules (){

        studentFormValidator.addRule("firstName", new ValidationRule(
                txtFirstname, Pattern.compile("^[a-z\\s]{3,30}$", Pattern.CASE_INSENSITIVE)));

        studentFormValidator.addRule("lastName", new ValidationRule(
                txtLastName, Pattern.compile("^[a-z]{0,30}$", Pattern.CASE_INSENSITIVE)));
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




