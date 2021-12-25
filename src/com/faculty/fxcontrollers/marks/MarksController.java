package com.faculty.fxcontrollers.marks;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Marks;
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

public class MarksController implements Initializable {
    @FXML
     private TableView<Marks> tbData;
    @FXML
    private TableColumn<Marks,String>clmClass;
    @FXML
    private  TableColumn<Marks,String>clmSemester;

    @FXML
    private TableColumn<Marks,String> clmCourse;

    @FXML
    private TableColumn<Marks,String> clmStudent;

    @FXML
    private  TableColumn<Marks,String > clmLecturer;

    @FXML
    private TableColumn<Marks,String> clmResult;

    @FXML
    private  TableColumn<Marks,String> clmMidTerm;

    @FXML
    private  TableColumn<Marks,String> clmFinal;

    @FXML
    private TableColumn<Marks,String>clmTotal;

    @FXML
    private Button btnAdd;

    @FXML
    private  Button btnUpdate;

    @FXML
    private  Button btnDelete;

    @FXML
            private  TextField txtSearch;


    ObjectProperty<Marks> selectedMarks = new SimpleObjectProperty<>(this, "selectedMarks");

    private ObservableList<Marks> marksObservableList = FXCollections.observableList(DBUtil.getAll(Marks.class));

    private FilteredList<Marks> marksFilteredList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnValuesFactories();
        tbData.setItems(marksObservableList);
        selectedMarks.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedMarks.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        marksFilteredList = new FilteredList<>(marksObservableList, e-> true);
    }


    void setColumnValuesFactories(){
        clmClass.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getAttendence().getCourse().getSemester().getClasses().getName()));
        clmSemester.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getAttendence().getCourse().getSemester().getSemester_name()));
        clmCourse.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getAttendence().getCourse().getCourse_name()));
        clmStudent.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStudent().toString()));
        clmLecturer.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getAttendence().getCourse().getLecturers().toString()));
        clmMidTerm.setCellValueFactory(cdf-> new SimpleStringProperty(String.format("%,d",cdf.getValue().getMid_term())));
        clmFinal.setCellValueFactory(cdf-> new SimpleStringProperty(String.format("%,d",cdf.getValue().getFinal_m())));
        clmTotal.setCellValueFactory(cdf-> new SimpleStringProperty(String.format("%,d",cdf.getValue().getTotal())));
        clmResult.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getResult()));
    }
    @FXML
    void onSearchAction(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            marksFilteredList.setPredicate((Marks m) -> {

                if(newValue.isEmpty()||newValue==null){

                    return true;
                }

                String toLowerCaseFilter =newValue.toLowerCase();
                // search by course name
                if (m.getAttendence().getCourse().getCourse_name().toLowerCase().contains(toLowerCaseFilter)){
                    return  true;
                    // Search by student
                }else if (m.getStudent().toString().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by total marks
                }else if(String.valueOf(m.getTotal()).toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by result of marks
                }else if(m.getResult().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by course name
                }else if(m.getAttendence().getCourse().toString().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                    // Search by Semester name
                }else if (m.getAttendence().getCourse().getSemester().getSemester_name().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                    // Search by Class name
                }else if (m.getAttendence().getCourse().getSemester().getClasses().getName().toLowerCase().contains(toLowerCaseFilter)){
                    return true;

                    // Search by Lecturers Name
                }else if (m.getAttendence().getCourse().getLecturers().toString().toLowerCase().contains(toLowerCaseFilter)){

                    return true;

                }

                return false;
            });



        });

        SortedList<Marks> marksSortedList = new SortedList<>(marksFilteredList);

        marksSortedList.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(marksSortedList);

    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Marks/CreateMarks.fxml"));
//        FacultyManagementController.getRoot().setCenter();
    }

    @FXML
    void editBtnAction(ActionEvent event){
        EditMarksController.setEditSelectedMarks(selectedMarks.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Marks/EditMarks.fxml"));
//        FacultyManagementController.getRoot().setCenter();

    }

    @FXML
    void deleteBtnAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action = alert.showAndWait();
        if (ButtonType.OK.equals(action.get())){
            DBUtil.delete(selectedMarks.get());
            tbData.getItems().remove(selectedMarks.get());
            tbData.refresh();

        }



    }
}
