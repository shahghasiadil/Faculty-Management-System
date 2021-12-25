package com.faculty.fxcontrollers.schedule;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Schedule;
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

public class ScheduleController implements Initializable {
    @FXML
    private  TableView<Schedule> tbData;

    @FXML
    private TableColumn<Schedule,String> clmClass;

    @FXML
    private  TableColumn<Schedule,String> clmLecturer;

    @FXML
    private TableColumn<Schedule,String>  clmDay;

    @FXML
    private  TableColumn<Schedule,String> clmStartTime;

    @FXML
    private  TableColumn<Schedule,String> clmEndTime;

    @FXML
    private TableColumn<Schedule,String> clmDate;

    @FXML
    private  TableColumn<Schedule,String> clmCourse;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private  Button btnDelete;
    @FXML
    private TextField txtSearch;
    private ObservableList<Schedule> scheduleObservableList = FXCollections.observableList(DBUtil.getAll(Schedule.class));
    private FilteredList<Schedule> scheduleFilteredList;
   private ObjectProperty<Schedule> selectedSchedule = new SimpleObjectProperty<>(this , "selectedSchedule");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbData.setItems(scheduleObservableList);
        selectedSchedule.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedSchedule.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        setColumnValuesFactories();
        scheduleFilteredList= new FilteredList<>(scheduleObservableList,e-> true);
    }

    void setColumnValuesFactories(){
        clmClass.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getClasses().getName()));
        clmLecturer.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourses().getLecturers().toString()));
        clmDay.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getDay()));
        clmStartTime.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStart_time()));
        clmEndTime.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEnd_time()));
        clmDate.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getDate()));
        clmCourse.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourses().getCourse_name()));

    }
    @FXML
    void onSearchAction(){

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            scheduleFilteredList.setPredicate(schedule -> {
                if (newValue.isEmpty()|| newValue==null){
                    return true;
                }

                String toLowerCaseFilter = newValue.toLowerCase();

                if (schedule.getDay().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }else if (schedule.getCourses().getCourse_name().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }else if (schedule.getCourses().getLecturers().toString().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }if (schedule.getClasses().getName().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }else if (schedule.getDate().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }
                return false;
            });

        });

        SortedList<Schedule> scheduleSortedList = new SortedList<>(scheduleFilteredList);
        scheduleSortedList.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(scheduleSortedList);
    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("schedule/CreateSchedule.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("schedule/CreateSchedule.fxml"));


    }


    @FXML
    void deleteBtnAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action = alert.showAndWait();
        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedSchedule.get());
            tbData.getItems().remove(selectedSchedule.get());
            tbData.refresh();
        }

    }



    @FXML
    void editBtnAction(ActionEvent event){
        EditScheduleController.setEditSchedule(selectedSchedule.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("schedule/EditSchedule.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("schedule/EditSchedule.fxml"));

    }
}
