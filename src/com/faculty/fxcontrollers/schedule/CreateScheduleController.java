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

public class CreateScheduleController implements Initializable {

    @FXML
    private  ComboBox<Classes> cmbClass;

    @FXML
    private ComboBox<Course> cmbCourse;

    @FXML
    private  ComboBox<String> cmbDay;

    @FXML
    private JFXDatePicker dtDate;

    @FXML
    private JFXTimePicker dtStartTime;

    @FXML
    private JFXTimePicker dtEndTime;


    private FormValidator validator = new FormValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        fillControls();
    }


    void fillControls(){

        List <String> DaysOfWEEK = new ArrayList<>();
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
        dtDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date &&  date.isBefore(LocalDate.now())){
                    setDisable(true);
                }
            }
        });


    }

    @FXML
    void createBtnAction(ActionEvent event){
        if (!validator.isValid()||dtEndTime.getValue()==null||dtStartTime.getValue()==null){

            System.err.println("Schedule info is not valid...");
            return;
        }

        Schedule schedule = new Schedule();
        schedule.setCourses(cmbCourse.getSelectionModel().getSelectedItem());
        schedule.setClasses(cmbClass.getSelectionModel().getSelectedItem());
        schedule.setDay(cmbDay.getSelectionModel().getSelectedItem());
        schedule.setStart_time((dtStartTime.getValue().toString()));
        schedule.setEnd_time(dtEndTime.getValue().toString());
        schedule.setDate(dtDate.getValue().toString());

        DBUtil.saveOrUpdate(schedule);
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
