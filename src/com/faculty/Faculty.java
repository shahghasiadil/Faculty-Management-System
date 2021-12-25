package com.faculty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

* @Author : Shahghasi "Adil"
* @Project : Faculty Management System
* @Frameworks : JavaFx, Hibernate, Java
* @Database : Mysql 5.7
* @Date : 2020

*/
public class Faculty extends Application {

    public static void main(String[] args) {
    Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent   root = FXMLLoader.load(getClass().getClassLoader().getResource("FacultyManagementScreen.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Faculty");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
//        primaryStage.setFullScreen(true);
        primaryStage.show();





    }


}
