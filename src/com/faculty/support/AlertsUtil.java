package com.faculty.support;

import javafx.scene.Scene;
import javafx.scene.control.*;

public class AlertsUtil {

    public static Alert retryAlert(String msg) {
        return alert("Try again", msg);
    }

    public static Alert alert(String msg) {
        return alert("App Name", msg);
    }

    public static Alert alert(String title, String msg) {
        return build(title, null, msg, Alert.AlertType.NONE);
    }

    public static Alert confirmAlert(String title, String headerText, String msg, Alert.AlertType type) {
        return build(title, headerText, msg, type, ButtonType.YES, ButtonType.NO);
    }

    public static Alert okAlert(String title, String headerText, String msg, Alert.AlertType type) {
        return build(title, headerText, msg, type, ButtonType.OK);
    }

    public static Alert build(
            String title, String headerText, String msg, Alert.AlertType type, ButtonType ...buttons) {
        Alert alert = new Alert(type, msg);

        if (buttons.length > 0) {
            alert.getButtonTypes().clear();
        } else if (type.equals(Alert.AlertType.NONE)) {
            alert.getButtonTypes().add(ButtonType.OK);
        }

        for (ButtonType btnType : buttons) {
            alert.getButtonTypes().add(btnType);
        }

        DialogPane dialogPane = alert.getDialogPane();
        Scene scene = dialogPane.getScene();
        Label contentLabel = (Label) scene.getRoot().lookup(".content");
        ButtonBar buttonBar = (ButtonBar) scene.getRoot().lookup(".button-bar");

        title = null == title ? "App Name" : title;
        scene.getRoot().setStyle("-fx-font-family: Calibri; -fx-font-size: 18px;");
        alert.setHeaderText(headerText);
        alert.setTitle(title);

        if (null != contentLabel) {
            contentLabel.setStyle("-fx-font-size: 15px");
        }

        if (null != buttonBar) {
            buttonBar.setStyle("-fx-font-size: 16px");
        }

        return alert;
    }

}
