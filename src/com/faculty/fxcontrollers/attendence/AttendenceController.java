package com.faculty.fxcontrollers.attendence;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Attendence;
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

public class AttendenceController  implements Initializable {

    @FXML
    TableView<Attendence> tbData;

    @FXML
    TableColumn<Attendence,String > clmStudent;

    @FXML
    TableColumn<Attendence,String> clmCourseName;

    @FXML
    TableColumn<Attendence,String> clmDate;
//   private KaryalDateTableColumn<Attendence>clmDate;

    @FXML
   public TableColumn<Attendence,String> clmStatus;

    @FXML
    private Button btnAdd;

    @FXML
    private  Button btnUpdate;

    @FXML
    private  Button btnDelete;

    @FXML
            private  TextField txtSearch ;

    private ObservableList<Attendence> attendenceObservableList = FXCollections.observableList(DBUtil.getAll(Attendence.class));
    private FilteredList<Attendence> attendenceFilteredList;
    ObjectProperty<Attendence> selectedAttendence = new SimpleObjectProperty<>(this,"selectedAttendence");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbData.setItems(attendenceObservableList);
        selectedAttendence.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedAttendence.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        setColoumnValueFactories();
        attendenceFilteredList = new FilteredList<>(attendenceObservableList,e-> true);
    }

    void setColoumnValueFactories(){
        clmCourseName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getCourse_name()));
        clmStudent.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStudents().toString()));
        clmDate.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getDate()));
        clmStatus.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getStatus()));


    }


    @FXML
    void onSearchAction(){

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            attendenceFilteredList.setPredicate(attendence -> {
                if (newValue.isEmpty()|| newValue==null){

                    return true;
                }
                String  toLowercaseFilter = newValue.toLowerCase();
                // search by student name in attendance
                if (attendence.getStudents().toString().toLowerCase().contains(toLowercaseFilter)){
                    return true;
                }else if (attendence.getCourse().getCourse_name().toLowerCase().contains(toLowercaseFilter)){
                    return true;
                }else if (attendence.getStatus().toLowerCase().contains(toLowercaseFilter)){
                    return true;
                }else if (attendence.getDate().toLowerCase().contains(toLowercaseFilter)){
                    return true;
                }
                return false;
            });

        });
        SortedList<Attendence> attendenceSortedList = new SortedList<>(attendenceFilteredList);

        attendenceSortedList.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(attendenceSortedList);
    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Attendence/CreateAttendence.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Attendence/CreateAttendence.fxml"));
    }

    @FXML
    void editBtnAction(ActionEvent event){
        EditAttendenceController.setEditAttendence(selectedAttendence.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Attendence/EditAttendence.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Attendence/EditAttendence.fxml"));

    }

    @FXML
    void deleteBtnAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");

        Optional<ButtonType> action = alert.showAndWait();
        if(ButtonType.OK.equals(action.get())){
            DBUtil.delete(selectedAttendence.get());
            tbData.getItems().remove(selectedAttendence.get());
            tbData.refresh();
        }

    }

}
