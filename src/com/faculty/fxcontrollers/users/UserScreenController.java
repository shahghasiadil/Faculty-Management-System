package com.faculty.fxcontrollers.users;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Users;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserScreenController implements Initializable {

    @FXML
    private TableView<Users> tbData;

    @FXML
    private TableColumn<Users, String> clmFirstName;

    @FXML
    private TableColumn<Users,String> clmLastName;

    @FXML
    private TableColumn<Users,String> clmUsername;

    @FXML
    private TableColumn<Users,String> clmPhone;

    @FXML
    private TableColumn<Users,String> clmEmail;

    @FXML
    private TableColumn<Users,String> Password;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtSearch;
    private FilteredList<Users> usersFilteredList ;
    private ObservableList<Users> users = FXCollections.observableList(DBUtil.getAll(Users.class));
    private ObjectProperty<Users> selectedUser = new SimpleObjectProperty<>(this,"selectedUser");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnValuesFoctories();
        tbData.setItems(users);
        selectedUser.bind(tbData.getSelectionModel().selectedItemProperty());
        btnDelete.disableProperty().bind(selectedUser.isNull());
//        btnAdd.disableProperty().bind(btnDelete.disableProperty());
        usersFilteredList = new FilteredList<>(users);

    }

    @FXML
    void addBtnAction(ActionEvent event) {
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Users/CreateUsers.fxml"));

    }

    @FXML
    void deleteBtnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (ButtonType.OK.equals(optional.get())){
            DBUtil.delete(selectedUser.get());
            tbData.getItems().remove(selectedUser.get());
            tbData.refresh();

        }


    }

    @FXML
    void editBtnAction(ActionEvent event) {

    }

    @FXML
    void onSearchAction(KeyEvent event) {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            usersFilteredList.setPredicate(users1 -> {

                if (newValue== null|| newValue.isEmpty() ){

                    return true;
                }
                String toLowerCaseFilter = newValue.toLowerCase();

                if (users1.getFirstName().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }else  if (users1.getLastName().toLowerCase().contains(toLowerCaseFilter)){

                    return  true;
                }else if (users1.getEmail().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                } else if ( users1.getUsername().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }
                return false;
            });


            SortedList<Users> usersSortedList = new SortedList<>(usersFilteredList);

            usersSortedList.comparatorProperty().bind(tbData.comparatorProperty());

            tbData.setItems(usersSortedList);


        });

    }

    void setColumnValuesFoctories(){
        clmFirstName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getFirstName()));
        clmLastName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getLastName()));
        clmEmail.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEmail()));
        clmPhone.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getPhone()));
        clmUsername.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getUsername()));
        Password.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getPassword()));
    }
    @FXML
    void backBtnAction(ActionEvent actionEvent){

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Users/UsersScreen.fxml"));
    }
}
