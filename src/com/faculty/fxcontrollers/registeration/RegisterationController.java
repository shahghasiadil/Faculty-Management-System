package com.faculty.fxcontrollers.registeration;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Registration;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
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

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterationController implements Initializable {

    @FXML
    private TableView<Registration> tbData;

    @FXML
    private TableColumn<Registration,String> clmCourse;

    @FXML
    private  TableColumn<Registration,String>clmDate;

    @FXML
    private  TableColumn<Registration,String> clmStudent;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnDelete;

    private FilteredList<Registration> registrationFilteredList;
    private ObservableList<Registration> registrationObservableList = FXCollections.observableList(DBUtil.getAll(Registration.class));
    private ObjectProperty<Registration> selectedRegistration = new SimpleObjectProperty<>(this,"selectedRegistration");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    setColumnValueFactories();
    tbData.setItems(registrationObservableList);
    selectedRegistration.bind(tbData.getSelectionModel().selectedItemProperty());
    btnUpdate.disableProperty().bind(selectedRegistration.isNull());
    btnDelete.disableProperty().bind(btnUpdate.disableProperty());
    registrationFilteredList = new FilteredList<>(registrationObservableList,e->true);
    }

    void setColumnValueFactories(){
        clmCourse.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getCourse_name()));
        clmStudent.setCellValueFactory(cdf->new SimpleStringProperty(cdf.getValue().getStudent().toString()));
        clmDate.setCellValueFactory(cdf->new SimpleStringProperty(cdf.getValue().getDate()));

    }

    @FXML
    void onSearchAction(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            registrationFilteredList.setPredicate(registration -> {
                if (newValue.isEmpty()||newValue==null){

                    return true;
                }
                String toLowerCaseFilter = newValue.toLowerCase();

                if (registration.getStudent().toString().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                }else if(registration.getCourse().getCourse_name().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }


                return false;
            });


        });

        SortedList<Registration> sortedList = new SortedList<>(registrationFilteredList);

        sortedList.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(sortedList);
    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Registration/CreateRegistration.fxml"));
//        FacultyManagementController.getRoot().setCenter();

    }
    @FXML
    void editBtnAction(ActionEvent event){
        EditRegistrationController.setEditRegistration(selectedRegistration.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Registration/EditRegistration.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Registration/EditRegistration.fxml"));


    }
    @FXML
    void deleteBtnAction(ActionEvent event){
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action = alert.showAndWait();

        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedRegistration.get());
            tbData.getItems().remove(selectedRegistration.get());
            tbData.refresh();
        }
    }

}
