package com.faculty.fxcontrollers.attendence;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Attendence;
import com.faculty.model.Course;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditAttendenceController implements Initializable {
    @FXML
    private ComboBox<Course> cmbCourse;
    @FXML

    private ComboBox<Student> cmbStudent;
    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private DatePicker dtDate;

    private  static Attendence editAttendence;
    private FormValidator validator= new FormValidator();

    public  static  void setEditAttendence(Attendence editAttendence){

        EditAttendenceController.editAttendence=editAttendence;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        fillControls();
    }


    void fillControls(){
        List<String> StatusList = new ArrayList<>();
        StatusList.add("Present");
        StatusList.add("Absent");
        cmbCourse.getItems().addAll(DBUtil.getAll(Course.class));
        cmbStudent.getItems().addAll(DBUtil.getAll(Student.class));
        cmbStatus.setItems(FXCollections.observableList(StatusList));

        cmbStatus.setValue(editAttendence.getStatus());
        cmbStudent.setValue(editAttendence.getStudents());
        cmbCourse.setValue(editAttendence.getCourse());
        dtDate.setValue(LocalDate.parse(editAttendence.getDate()));
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
        if (!validator.isValid()){

            System.err.println("Attendance info is not Valid!!! ");
            return;
        }
        editAttendence.setCourse(cmbCourse.getSelectionModel().getSelectedItem());
        editAttendence.setStatus(cmbStatus.getSelectionModel().getSelectedItem());
        editAttendence.setStudents(cmbStudent.getSelectionModel().getSelectedItem());
        editAttendence.setDate(dtDate.getValue().toString());

        DBUtil.update(editAttendence);
        backBtnAction(event);
    }
    @FXML
    void backBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Attendence/AttendenceScreen.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Attendence/AttendenceScreen.fxml"));
    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }
    void setRules(){
        ValidationRule dateValidationRule = new ValidationRule(dtDate, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && ! dp.getValue().isAfter(LocalDate.now());
            }

            return false;
        });

        ValidationRule CourseValidationRule = new ValidationRule(cmbCourse, "");

        CourseValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox CourseComboBox = (ComboBox) comboBox;

                return CourseComboBox.getValue() != null;
            }

            return false;
        });


        ValidationRule StudentValidationRule = new ValidationRule(cmbStudent, "");

        StudentValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox studentComboBox = (ComboBox) comboBox;

                return studentComboBox.getValue() != null;
            }

            return false;
        });


        ValidationRule StatusValidationRule = new ValidationRule(cmbStatus, "");

        StatusValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox statusComboBox = (ComboBox) comboBox;

                return statusComboBox.getValue() != null;
            }

            return false;
        });

        validator.addRule("student",StudentValidationRule);
        validator.addRule("course",CourseValidationRule);
        validator.addRule("status",StatusValidationRule);
    }


}
