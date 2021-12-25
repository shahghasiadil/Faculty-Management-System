package com.faculty.fxcontrollers.lecturers;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Address;
import com.faculty.model.Lecturer;
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

import javax.mail.internet.InternetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateLecturerController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private  TextField txtLastName;

    @FXML
    private  TextField txtPhone;

    @FXML
    private  TextField txtEmail;
    @FXML
    private ComboBox<Address> cmbAddress;
    private FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        setControls();
    }

    @FXML
   public void createBtnAction(javafx.event.ActionEvent event){
            if(!validator.isValid()){

                System.err.println("Lecturer info is not Valid!!!");
                return;
            }
        Lecturer lecturer =new Lecturer();
        lecturer.setFirst_name(txtFirstname.getText());
        lecturer.setLast_name(txtLastName.getText());
        lecturer.setEmail(txtEmail.getText());
        lecturer.setPhone_no(txtPhone.getText());
        lecturer.setAddress(cmbAddress.getSelectionModel().getSelectedItem());

        DBUtil.save(lecturer);
        backBtnAction(event);
    }


    @FXML
    void backBtnAction (ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("lecturers/LecturersScreen.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("lecturers/LecturersScreen.fxml"));
    }
    @FXML
    void cancelBtnAction(ActionEvent event ){
        backBtnAction(event);
    }

    void setControls(){
        cmbAddress.getItems().addAll(DBUtil.getAll(Address.class));
    }
    void setRules (){

        validator.addRule("firstName", new ValidationRule(
                txtFirstname, Pattern.compile("^[a-z\\s]{3,40}$", Pattern.CASE_INSENSITIVE)));

        validator.addRule("lastName", new ValidationRule(
                txtLastName, Pattern.compile("^[a-z]{0,30}$",
                Pattern.CASE_INSENSITIVE)));
        ValidationRule AddressValidationRule = new ValidationRule(cmbAddress, "");

        AddressValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox cmbAddress = (ComboBox) comboBox;

                return cmbAddress.getValue() != null;
            }

            return false;
        });



        ValidationRule emailValidator = new ValidationRule();

        emailValidator.setControl(txtEmail);
        emailValidator.setValidator(control -> {
            TextField textField = (TextField) control;
            String value = textField.getText();

            if (value.isEmpty()) {
                return false;
            }

            try {
                InternetAddress address = new InternetAddress(value);

                address.validate();
                return true;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return false;
            }
        });
        validator.addRule("phone", new ValidationRule(txtPhone, Pattern.compile("^07\\d{8}$")));
        validator.addRule("email", emailValidator);
        validator.addRule("Address",AddressValidationRule);

    }


}
