package com.faculty.fxcontrollers;

import com.faculty.model.Users;
import com.faculty.support.AlertsUtil;
import com.faculty.support.ConcurrencyUtil;
import com.faculty.support.InternetConnectionUtil;
import com.faculty.support.ViewUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    @FXML
    private VBox formWrapper;

    @FXML
    private TextField usernameFld;

    @FXML
    private HBox actionsHbox;

    private static Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBox.setMargin(actionsHbox, new Insets(10, 0, 0, 0));
    }

    @FXML
    private void resetPasswordAction(ActionEvent event) {
        String username = null == usernameFld.getText() ? "" :usernameFld.getText().trim();

        if (username.isEmpty()) {
            return;
        }

        user = Users.getByUsername(usernameFld.getText().trim());

        if (user != null) {
            formWrapper.setVisible(false);

            ConcurrencyUtil.run(() -> {
                if (! InternetConnectionUtil.isConnected()) {
                    Platform.runLater(() -> {
                        formWrapper.setVisible(true);
                        AlertsUtil.retryAlert("There is no internet connection").show();
                    });
                } else {
                    Platform.runLater(() ->
                            FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("PasswordReset.fxml")));
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No user exits");
            alert.showAndWait();
        }
    }

    @FXML
    private void goBackAction(ActionEvent event) {
        FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Login.fxml"));
    }

    public static Users getUser() {
        Users tmpUser = user;
        user = null;
        return tmpUser;
    }
}
