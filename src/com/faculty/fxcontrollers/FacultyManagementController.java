package com.faculty.fxcontrollers;

import com.faculty.support.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FacultyManagementController implements Initializable {
    @FXML
    private AnchorPane homeRoot;
    private  static  AnchorPane staticRoot;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticRoot=homeRoot;
        homeRoot.getChildren().setAll(ViewUtil.loadView("login.fxml"));

    }
    public static AnchorPane getStaticRoot(){
        return staticRoot;
    }
}
