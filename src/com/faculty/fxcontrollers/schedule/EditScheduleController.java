package com.faculty.fxcontrollers.schedule;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Classes;
import com.faculty.model.Course;
import com.faculty.model.Schedule;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import com.jfoenix.controls.JFXDatePicker;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.LocalDate.parse;

public class EditScheduleController implements Initializable  {

    @FXML
    private ComboBox<Classes> cmbClass;

    @FXML
    private ComboBox<Course> cmbCourse;

    @FXML
    private  ComboBox<String> cmbDay;

    @FXML
    private DatePicker dtDate;

    @FXML
    private JFXTimePicker dtStartTime;

    @FXML JFXTimePicker dtEndTime;

    private  static Schedule editSchedule;
    private FormValidator validator = new FormValidator();
    public  static  void setEditSchedule(Schedule editSchedule){
        EditScheduleController.editSchedule=editSchedule;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    fillControls();
    setRules();
    }


    void fillControls(){

        List<String> DaysOfWEEK = new ArrayList<>();
        DaysOfWEEK.add("Saturday");
        DaysOfWEEK.add("Sunday");
        DaysOfWEEK.add("Monday");
        DaysOfWEEK.add("Tuesday");
        DaysOfWEEK.add("Wednesday");
        DaysOfWEEK.add("Thursday");
        DaysOfWEEK.add("Friday");
        cmbClass.getItems().addAll(DBUtil.getAll(Classes.class));
        cmbCourse.getItems().addAll(DBUtil.getAll(Course.class));
        cmbDay.getItems().addAll(DaysOfWEEK);

        cmbClass.setValue(editSchedule.getClasses());
        cmbCourse.setValue(editSchedule.getCourses());
        cmbDay.setValue(editSchedule.getDay());
//        txtEndTime.setText(editSchedule.getEnd_time());
//        txtStartTime.setText(editSchedule.getStart_time());
        dtEndTime.setValue(LocalTime.parse(editSchedule.getEnd_time()));
        dtStartTime.setValue(LocalTime.parse(editSchedule.getStart_time()));

        dtDate.setValue(parse(editSchedule.getDate()));
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
    void saveBtnAction(ActionEvent event){
        if (!validator.isValid() || dtStartTime.getValue()==null ||dtEndTime.getValue()==null){

            System.err.println("Schedule info is not valid...");
            return;
        }
        editSchedule.setClasses(cmbClass.getSelectionModel().getSelectedItem());
        editSchedule.setDay(cmbDay.getSelectionModel().getSelectedItem());
        editSchedule.setDate(dtDate.getValue().toString());
        editSchedule.setStart_time(dtStartTime.getValue().toString());
        editSchedule.setEnd_time(dtEndTime.getValue().toString());
        editSchedule.setCourses(cmbCourse.getSelectionModel().getSelectedItem());
        DBUtil.update(editSchedule);
        backBtnAction(event);
    }

    @FXML
    void backBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("schedule/ScheduleScreen.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("schedule/ScheduleScreen.fxml"));

    }
    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }
    void setRules(){

        ValidationRule ClasstValidationRule = new ValidationRule(cmbClass, "");
        ClasstValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox classComboBox = (ComboBox) comboBox;

                return classComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule CourseValidationRule = new ValidationRule(cmbCourse, "");
        CourseValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox courseComboBox = (ComboBox) comboBox;

                return courseComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule DayValidationRule = new ValidationRule(cmbDay, "");
        DayValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox dayComboBox = (ComboBox) comboBox;

                return dayComboBox.getValue() != null;
            }

            return false;
        });



        ValidationRule dateValidationRule = new ValidationRule(dtDate, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof JFXDatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && !dp.getValue().isBefore(LocalDate.now());
            }

            return false;
        });
        ValidationRule endTimeValidationRule = new ValidationRule();
        endTimeValidationRule.setValidator(timePicker -> {
            if (timePicker instanceof JFXTimePicker) {
                JFXTimePicker dp = (JFXTimePicker) timePicker;

                return null != dp.getValue()&& dp.getValue().isAfter(LocalTime.now());
            }

            return false;
        });
        ValidationRule startTimeValidationRule = new ValidationRule();
        startTimeValidationRule.setValidator(timePicker -> {
            if (timePicker instanceof JFXTimePicker) {
                JFXTimePicker dp = (JFXTimePicker) timePicker;

                return null != dp.getValue() && dp.getValue().isAfter(LocalTime.now());
            }

            return false;
        });


        validator.addRule("daterule",dateValidationRule);
        validator.addRule("courses",CourseValidationRule);
        validator.addRule("students",ClasstValidationRule);
        validator.addRule("day",DayValidationRule);
//        validator.addRule("startTimedt",startTimeValidationRule);
//        validator.addRule("endTimedt",endTimeValidationRule);

    }



}
