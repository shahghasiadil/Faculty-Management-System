package com.faculty.fxcontrollers;
import com.faculty.fxcontrollers.email.PasswordResetEmail;
import com.faculty.model.Users;
import com.faculty.model.VerificationCode;
import com.faculty.support.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.hibernate.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordResetController implements Initializable {
    @FXML
    private VBox formWrapper;

    @FXML
    private VBox passwordResetRoot;

    @FXML
    private Text userFullName, userEmailAddress;

    @FXML
    private TextField verificationCode;

    @FXML
    private TextField password;

    @FXML
    private TextField passwordAgain;

    @FXML
    private HBox actions;

    private ResourceBundle resourceBundle;
    private Users user;
    private VerificationCode vCode;
    private PasswordResetEmail resetEmail;
    private long emailSentAt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        //VBox.setMargin(actions, new Insets(15, 0, 0, 0));

        user = ForgotPasswordController.getUser();

        if (null == user) {
            FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Login.fxml"));
        }

        vCode = new VerificationCode();
        userFullName.setText(user.getFirstName());
        userEmailAddress.setText(user.getEmail());

        // Send email
        formWrapper.setVisible(false);
        ConcurrencyUtil.run(() -> {
            resetEmail = new PasswordResetEmail(user, vCode);
            try {
                resetEmail.send();
                emailSentAt = System.currentTimeMillis();
            } catch(Exception e) {
                Platform.runLater(() -> showNoNetworkAlert());
            } finally {
                Platform.runLater(() -> formWrapper.setVisible(true));
            }

        });
    }

    @FXML
    private void resetAction(ActionEvent event) {
        if (! verifyCode()) {
            return;
        }

        if (password.getText().equals(passwordAgain.getText())) {
            user.setPassword(password.getText());

            Session session = SessionFactoryUtil.getSession();
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            session.close();

            showResetSuccessAlert();
        } else {
            showInvalidValuesAlert();
        }
    }

    @FXML
    private void resendAction(ActionEvent event) {
        long sinceEmailSent = System.currentTimeMillis() - emailSentAt;

        if (sinceEmailSent < 30_000L) {
            showWaitingAlert(sinceEmailSent);
        } else {
            formWrapper.setVisible(false);
            ConcurrencyUtil.run(() -> {
                if (! InternetConnectionUtil.isConnected()) {
                    Platform.runLater(() -> {
                        formWrapper.setVisible(true);
                        showNoNetworkAlert();
                    });
                } else {
                    try {
                        resetEmail.send();
                        emailSentAt = System.currentTimeMillis();
                    } catch(Exception e) {
                        Platform.runLater(() -> showNoNetworkAlert());
                    } finally {
                        Platform.runLater(() -> formWrapper.setVisible(true));
                    }

                }
            });
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Login.fxml"));
    }

    private boolean verifyCode() {
        boolean verified = true;
        String writtenCode = null == verificationCode.getText() ? "" : verificationCode.getText().trim();

        if (writtenCode.isEmpty()) {
            return false;
        }

        if (vCode.expired()) {
            AlertsUtil.retryAlert("Code expired").show();
            verified = false;
        } else if (! vCode.matches(writtenCode)) {
            AlertsUtil.retryAlert("Code mismatch").show();
            verified = false;
        }

        return verified;
    }

    private void showWaitingAlert(long sinceEmailSent) {
        long seconds = sinceEmailSent / 1000;

        String msg = "Wait for 30 seconds before sending next email";
        msg = String.format(msg, seconds, 30 - seconds);

        AlertsUtil.retryAlert(msg).show();
    }

    private void showNoNetworkAlert() {
        AlertsUtil.retryAlert("No internet connection").show();
    }

    private void showResetSuccessAlert() {
        AlertsUtil.alert("Password successfully reset.").show();
        FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Login.fxml"));
    }

    private void showInvalidValuesAlert() {
        AlertsUtil.retryAlert("Passwords were invalid").show();
    }
}
