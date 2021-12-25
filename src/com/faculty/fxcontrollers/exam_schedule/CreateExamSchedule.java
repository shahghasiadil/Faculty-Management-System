package com.faculty.fxcontrollers.exam_schedule;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Course;
import com.faculty.model.Exam_Schedule;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateExamSchedule implements Initializable  {
    @FXML
    private ComboBox<Course> cmbCourse;

    @FXML
    private DatePicker dtDate;

    @FXML
    private JFXTimePicker dtStartTime;


    @FXML
    private JFXTimePicker dtEndTime;



    private FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillControls();
        setRules();
    }

    void fillControls(){
        cmbCourse.getItems().addAll(DBUtil.getAll(Course.class));
        dtDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

    }

    @FXML
    void createBtnAction(ActionEvent event ){

        if (!validator.isValid()|| dtStartTime.getValue()==null||dtEndTime.getValue()==null) {
            System.err.println("Exam-schedule info is not valid");
            return;
        }
        Exam_Schedule exam_schedule = new Exam_Schedule();
        exam_schedule.setCourse(cmbCourse.getSelectionModel().getSelectedItem());
        exam_schedule.setDate(dtDate.getValue().toString());
        exam_schedule.setStart_time(dtStartTime.getValue().toString());
        exam_schedule.setEnd_time(dtEndTime.getValue().toString());
        DBUtil.save(exam_schedule);
        backBtnAction(event);

    }

    @FXML
    void backBtnAction(ActionEvent event){

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Examination/Exam_ScheduleScreen.fxml"));
//        FacultyManagementController.getRoot().setCenter();
    }
    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }
    void setRules(){

        ValidationRule coursesValidationRule = new ValidationRule(cmbCourse, "");

        coursesValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox coursesComboBox = (ComboBox) comboBox;

                return coursesComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule dateValidationRule = new ValidationRule(dtDate, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && !dp.getValue().isBefore(LocalDate.now());
            }

            return false;
        });

        validator.addRule("date",dateValidationRule);
        validator.addRule("courses", coursesValidationRule);
    }



}
