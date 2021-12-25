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
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EditExamSchedule implements Initializable {
    @FXML
    private ComboBox<Course> cmbCourse;

    @FXML
    private DatePicker dtDate;



    @FXML
    private JFXTimePicker dtStartTime;

    @FXML
    private JFXTimePicker dtEndTime;

    private  static Exam_Schedule editExamSchedule;

    public  static  void setEditExamSchedule(Exam_Schedule editExamSchedule){

        EditExamSchedule.editExamSchedule=editExamSchedule;
    }
    private FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    fillControls();
    setRules();
    }
    @FXML void saveBtnAction(ActionEvent event){
        if (!validator.isValid()|| dtEndTime.getValue()==null ||dtStartTime.getValue()==null){
            System.err.println("Exam-Schedule info is not valid...");
            return;
        }
        editExamSchedule.setEnd_time(dtEndTime.getValue().toString());
        editExamSchedule.setStart_time(dtStartTime.getValue().toString());
        editExamSchedule.setDate(dtDate.getValue().toString());
        editExamSchedule.setCourse(cmbCourse.getSelectionModel().getSelectedItem());
        DBUtil.update(editExamSchedule);
        backBtnAction(event);
    }

    @FXML
    void backBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Examination/Exam_ScheduleScreen.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Examination/Exam_ScheduleScreen.fxml"));
    }
    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    void fillControls(){
        cmbCourse.getItems().addAll(DBUtil.getAll(Course.class));
        dtStartTime.setValue(LocalTime.parse(editExamSchedule.getStart_time()));
        dtEndTime.setValue(LocalTime.parse(editExamSchedule.getEnd_time()));
        dtDate.setValue(LocalDate.parse(editExamSchedule.getDate()));
        cmbCourse.setValue(editExamSchedule.getCourse());
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

                return null != dp.getValue() &&  !dp.getValue().isBefore(LocalDate.now());
            }

            return false;
        });

        validator.addRule("date",dateValidationRule);
        validator.addRule("courses", coursesValidationRule);
    }

}
