package com.faculty.fxcontrollers.registeration;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Course;
import com.faculty.model.Registration;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
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

public class CreateRegistrationController implements Initializable {
    @FXML
    private ComboBox<Student> cmbStudents;

    @FXML
    private ComboBox<Course>  cmbCourses;

    @FXML
    private DatePicker dtDate;

    private FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        setControls();
    }


    @FXML
    void createBtnAction(ActionEvent event){
        if (!validator.isValid()){

            System.err.println("Registration info is not valid...");
            return;
        }
        Registration registration = new Registration();
        registration.setDate(dtDate.getValue().toString());
        registration.setCourse(cmbCourses.getSelectionModel().getSelectedItem());
        registration.setStudent(cmbStudents.getSelectionModel().getSelectedItem());

        DBUtil.save(registration);
        backBtnAction(event);


    }

    @FXML
    void backBtnAction(ActionEvent event){

//        FacultyManagementController.getRoot().setCenter();

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Registration/RegistrationScreen.fxml"));
    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    void setControls(){

        cmbCourses.getItems().addAll(DBUtil.getAll(Course.class));
        cmbStudents.getItems().addAll(DBUtil.getAll(Student.class));

    }

    void setRules(){
        dtDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isBefore(LocalDate.now()))
                    setDisable(true);
            }

        });



        ValidationRule StudentValidationRule = new ValidationRule(cmbStudents, "");
        StudentValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox studentComboBox = (ComboBox) comboBox;

                return studentComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule CourseValidationRule = new ValidationRule(cmbCourses, "");
        CourseValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox courseComboBox = (ComboBox) comboBox;

                return courseComboBox.getValue() != null;
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


        validator.addRule("daterule",dateValidationRule);
        validator.addRule("courses",CourseValidationRule);
        validator.addRule("students",StudentValidationRule);

    }

}
