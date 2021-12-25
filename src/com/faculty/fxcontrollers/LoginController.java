package com.faculty.fxcontrollers;

import com.faculty.model.Users;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameFld;

    @FXML
    private PasswordField passwordFld;

    @FXML
    private Hyperlink forgotPasswordLnk;
    @FXML
    private BorderPane root;
    private static BorderPane staticRoot;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("Login initialized...");



    }

    private String getUsername() {
        return usernameFld.getText().trim();
    }

    private String getPassword() {
        return passwordFld.getText();
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        if (getUsername().isEmpty() || getPassword().isEmpty()) {
            System.err.println("Username and password must be provided");
        } else if (attempt()) {
             FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Dashboard.fxml"));
             DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Student/StudentScreen.fxml"));

        } else {
            System.err.println("Username of password is incorrect.");
        }
    }

    private boolean attempt() {
        return DBUtil.execute(sess -> {
            Query query = sess.createQuery("from Users u where " +
                    "u.username = :username or " +
                    "u.email = :username or " +
                    "u.phone = :username"
            );

            query.setParameter("username", getUsername());


            Users found = (Users) query.getSingleResult();
//            Users found = (Users) query.setMaxResults(1).uniqueResult();



            return (null == found ? false : (BCrypt.checkpw(getPassword(), found.getPassword())));
        });
    }

    @FXML
    private void forgotPasswordAction(ActionEvent event) {
        FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("ForgotPassword.fxml"));
    }
}
