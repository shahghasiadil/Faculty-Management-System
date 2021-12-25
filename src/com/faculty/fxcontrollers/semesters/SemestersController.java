package com.faculty.fxcontrollers.semesters;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Semester;
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

public class SemestersController implements Initializable {
    @FXML
    private TableView<Semester> tbData;

    @FXML
    private TableColumn<Semester,String>  clmSemesterName;

    @FXML
    private TableColumn<Semester,String> clmStartDate;

    @FXML
    private TableColumn<Semester,String >clmEndDate;

    @FXML
    private TableColumn<Semester,String > clmClasses;


    @FXML
            private Button btnAdd;
    @FXML
            private Button btnUpdate;
    @FXML
            private Button btnDelete;
    @FXML
            private TextField txtSearch;

    private ObservableList<Semester> semesterObservableList =FXCollections.observableList(DBUtil.getAll(Semester.class));
    private FilteredList<Semester> semesterFilteredList;
    private ObjectProperty<Semester> selectedSemester = new SimpleObjectProperty<>(this,"selectedSemester");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setControls();
        tbData.setItems(semesterObservableList);
        selectedSemester.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedSemester.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        semesterFilteredList = new FilteredList<>(semesterObservableList);

    }
    void setControls(){
        clmSemesterName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getSemester_name()));
        clmStartDate.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStart_date()));
        clmEndDate.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEnd_date()));
        clmClasses.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getClasses())));

    }
    @FXML
    void onSearchAction(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            semesterFilteredList.setPredicate(semester -> {

                if (newValue.isEmpty()||newValue==null){

                    return true;
                }

                String toLowerCaseFilter = newValue.toLowerCase();

                if (semester.getSemester_name().toLowerCase().contains(toLowerCaseFilter)){

                    return  true;
                }else if(semester.getClasses().getName().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                }
                return false;
            });

        });

        SortedList<Semester> semesterSortedList = new SortedList<>(semesterFilteredList);

        semesterSortedList.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(semesterSortedList);
    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/Semesters/CreateSemesters.fxml"));
//        FacultyManagementController.getRoot().setCenter();

    }

    @FXML
    void editBtnAction(ActionEvent event){
        EditSemesterController.setEditSemester(selectedSemester.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/Semesters/EditSemesters.fxml"));
//        FacultyManagementController.getRoot().setCenter();

    }

    @FXML
    void deleteBtnAction(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action = alert.showAndWait();
        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedSemester.get());
            tbData.getItems().remove(selectedSemester.get());
            tbData.refresh();
        }

    }


}
