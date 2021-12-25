package com.faculty.fxcontrollers.exam_schedule;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Exam_Schedule;
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

public class ExamScheduleController implements Initializable {
    @FXML
    private TableView<Exam_Schedule> tbData;

    @FXML
    private TableColumn<Exam_Schedule,String> clmCourse;

    @FXML
    private TableColumn<Exam_Schedule,String> clmLecturer;

    @FXML
    private  TableColumn<Exam_Schedule,String> clmDate;

    @FXML
    private TableColumn<Exam_Schedule,String>clmStartTime;

    @FXML
    private  TableColumn<Exam_Schedule,String> clmEndTime;

    @FXML
    private Button btnAdd;

    @FXML
    private  Button btnUpdate;

    @FXML
    private  Button btnDelete;

    @FXML
            private  TableColumn<Exam_Schedule,String> clmClass;
    @FXML
            private TableColumn<Exam_Schedule,String> clmSemester;
    @FXML
            private TextField txtSearch;

    private ObservableList<Exam_Schedule> exam_schedules = FXCollections.observableList(DBUtil.getAll(Exam_Schedule.class));
    private FilteredList<Exam_Schedule> filteredList ;
    private ObjectProperty<Exam_Schedule> selectedExamSchedule = new SimpleObjectProperty<>(this,"selectedExamSchedule");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnValuesFactories();
        tbData.setItems(exam_schedules);
        selectedExamSchedule.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedExamSchedule.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        filteredList = new FilteredList<>(exam_schedules);

    }



    void setColumnValuesFactories(){
        clmClass.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getSemester().getClasses().getName()));
        clmSemester.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getSemester().getSemester_name()));
        clmCourse.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getCourse_name()));
        clmLecturer.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse().getLecturers().toString()));
        clmDate.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getDate()));
        clmStartTime.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStart_time()));
        clmEndTime.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEnd_time()));


    }
        @FXML
        void onSearchAction(){
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(exam_schedule -> {
                    if (newValue.isEmpty()||newValue==null){
                        return true;
                    }

                    String toLowerCaseFilter = newValue.toLowerCase();

                    if (exam_schedule.getCourse().getCourse_name().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else if (exam_schedule.getCourse().getLecturers().toString().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else if (exam_schedule.getDate().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else if (exam_schedule.getCourse().getSemester().getClasses().getName().contains(toLowerCaseFilter)){
                        return true;
                    }else if (exam_schedule.getCourse().getSemester().getSemester_name().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }


                        return false;
                });
            });

            SortedList<Exam_Schedule> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tbData.comparatorProperty());
            tbData.setItems(sortedList);
        }

        @FXML
        void addBtnAction(ActionEvent event){
            DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Examination/CreateExam_Schedule.fxml"));
//            FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Examination/CreateExam_Schedule.fxml"));

        }

        @FXML
        void editBtnAction(ActionEvent event){
        EditExamSchedule.setEditExamSchedule(selectedExamSchedule.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Examination/EditExam_Schedule.fxml"));

//        FacultyManagementController.getRoot().setCenter();

        }
        @FXML
        void deleteBtnAction(ActionEvent event){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");

            Optional<ButtonType> action = alert.showAndWait();

            if(ButtonType.OK.equals(action.get())){
                DBUtil.delete(selectedExamSchedule.get());
                tbData.getItems().remove(selectedExamSchedule.get());
                tbData.refresh();
            }

        }


}
