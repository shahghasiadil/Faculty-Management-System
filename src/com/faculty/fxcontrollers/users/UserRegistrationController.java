package com.faculty.fxcontrollers.users;


import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Users;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.internet.InternetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserRegistrationController implements Initializable {
    @FXML
    private TextField firstNameFld;

    @FXML
    private TextField lastNameFld;

    @FXML
    private TextField phoneFld;

    @FXML
    private TextField usernameFld;

    @FXML
    private TextField emailFld;

    @FXML
    private PasswordField passwordFld;

    private FormValidator validator = new FormValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
    }

    @FXML
    void cancelUserFormAction(ActionEvent event) {

        backBtnAction(event);
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("LoginScreen.fxml"));
    }

    @FXML
    void createUserAction(ActionEvent event) {
        if (! validator.isValid()) {
            System.err.println("User info is not valid...");
            return;
        }

        Users newUser = new Users.UserBuilder()
                .withFirstName(firstNameFld.getText())
                .withLastName(lastNameFld.getText())
                .withPhone(phoneFld.getText())
                .withUsername(usernameFld.getText())
                .withEmail(emailFld.getText())
                .withPassword(passwordFld.getText())
                .build();

        DBUtil.save(newUser);
        backBtnAction(event);
    }

    private void resetControls() {
        firstNameFld.clear();
        lastNameFld.clear();
        phoneFld.clear();
        usernameFld.clear();
        emailFld.clear();
        passwordFld.clear();
    }

    private void setRules() {
        validator.addRule("firstName", new ValidationRule(
                firstNameFld, Pattern.compile("^[a-z]{3,30}$", Pattern.CASE_INSENSITIVE)));

        validator.addRule("lastName", new ValidationRule(
                lastNameFld, Pattern.compile("^[a-z]{0,30}$", Pattern.CASE_INSENSITIVE)));

        validator.addRule("phone", new ValidationRule(phoneFld, Pattern.compile("^07\\d{8}$")));
        validator.addRule("username", new ValidationRule(usernameFld, Pattern.compile("^\\w{5,20}$")));

        ValidationRule emailValidator = new ValidationRule();

        emailValidator.setControl(emailFld);
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

        validator.addRule("email", emailValidator);
        validator.addRule("password", new ValidationRule(passwordFld, Pattern.compile("^.{5,20}$")));
    }

    @FXML
    void backBtnAction(ActionEvent actionEvent){

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Users/UsersScreen.fxml"));
    }


}


