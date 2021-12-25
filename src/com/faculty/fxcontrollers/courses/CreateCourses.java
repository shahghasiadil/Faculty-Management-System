package com.faculty.fxcontrollers.courses;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Course;
import com.faculty.model.Lecturer;
import com.faculty.model.Semester;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateCourses implements Initializable {

    @FXML
    private TextField txtCourseName;

    @FXML
    private  TextField txtCourseCredit;

    @FXML
    private ComboBox<Lecturer> cmbLecturer;
    @FXML
    private ComboBox<Semester> cmbSemester;

    private FormValidator validator = new FormValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        setControls();

    }

    void setControls(){
        cmbLecturer.getItems().addAll(DBUtil.getAll(Lecturer.class));
        cmbSemester.getItems().addAll(DBUtil.getAll(Semester.class));

    }

    @FXML
    void createBtnAction(ActionEvent event){
        if (!validator.isValid()){

            System.err.println("Course info is not valid...");
            return;
        }
        Course course = new Course();
        course.setCourse_name(txtCourseName.getText());
        course.setC_Credits(Long.parseLong(txtCourseCredit.getText()));
        course.setLecturers(cmbLecturer.getSelectionModel().getSelectedItem());
        course.setSemester(cmbSemester.getSelectionModel().getSelectedItem());
        DBUtil.save(course);
        backBtnAction(event);

    }

    @FXML
    void backBtnAction(ActionEvent event){

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("courses/CoursesScreen.fxml"));
//        FacultyManagementController.getRoot().setCenter();
    }

    @FXML
    void cancelBtnAction(ActionEvent event){
        backBtnAction(event);
    }

    void setRules(){

        ValidationRule lecturerValidationRule = new ValidationRule(cmbLecturer, "");

        lecturerValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox lecturerComboBox = (ComboBox) comboBox;

                return lecturerComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule semesterValidationRule = new ValidationRule(cmbSemester, "");

        semesterValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox semesterComboBox = (ComboBox) comboBox;

                return semesterComboBox.getValue() != null;
            }

            return false;
        });
        Pattern courseNamePattern = Pattern.compile("^[a-z 0-9]{2,30}$", Pattern.CASE_INSENSITIVE);
        Pattern CourseCreditsPattern = Pattern.compile("^[1-4]$", Pattern.CASE_INSENSITIVE);

        validator.addRule("credit",new ValidationRule(txtCourseCredit,CourseCreditsPattern));
        validator.addRule("coursename",new ValidationRule(txtCourseName,courseNamePattern));
        validator.addRule("lecturer",lecturerValidationRule);
        validator.addRule("semester",semesterValidationRule);

    }


}
