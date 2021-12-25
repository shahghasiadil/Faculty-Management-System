package com.faculty.support;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


public class ViewUtil {
    private static ClassLoader loader = ViewUtil.class.getClassLoader();

    public static Parent loadView(String viewName) {
        Parent rootNode = new StackPane(new Label(String.format("%s view cannot be loaded.", viewName)));

        try {
            rootNode = FXMLLoader.load(loader.getResource(viewName));
//            rootNode = FXMLLoader.load(ViewUtil.class.getClassLoader().getResource(viewName));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }

        return rootNode;
    }

    public static FXMLLoader getFXMLLoader(String view) {
        return new FXMLLoader(loader.getResource(view));
    }

    public static Parent loadView(FXMLLoader fxmlLoader) {
        Parent rootNode = new StackPane(new Label(String.format("%s view cannot be loaded.",
                fxmlLoader.getLocation().getFile())));

        try {
            rootNode = fxmlLoader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }

        return rootNode;
    }
}
