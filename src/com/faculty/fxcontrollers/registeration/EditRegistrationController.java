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

public class EditRegistrationController implements Initializable {
    @FXML
    private ComboBox<Student> cmbStudents;

    @FXML
    private ComboBox<Course>  cmbCourses;

    @FXML
    private DatePicker dtDate;

    private FormValidator validator = new FormValidator();

    private  static Registration editRegistration;

    public  static void setEditRegistration(Registration editRegistration){
        EditRegistrationController.editRegistration=editRegistration;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillControls();
        setRules();
    }

    @FXML
    void saveBtnAction(ActionEvent event){
        if (!validator.isValid()){

            System.err.println("Registration info is not valid...");
            return;
        }
        editRegistration.setStudent(cmbStudents.getSelectionModel().getSelectedItem());
        editRegistration.setCourse(cmbCourses.getSelectionModel().getSelectedItem());
        editRegistration.setDate(dtDate.getValue().toString());
        DBUtil.update(editRegistration);
        backBtnAction(event);

    }
    @FXML
    void backBtnAction(ActionEvent event){
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("Registration/RegistrationScreen.fxml"));
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Registration/RegistrationScreen.fxml"));

    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    void fillControls(){

        cmbCourses.getItems().addAll(DBUtil.getAll(Course.class));
        cmbStudents.getItems().addAll(DBUtil.getAll(Student.class));
        cmbStudents.setValue(editRegistration.getStudent());
        cmbCourses.setValue(editRegistration.getCourse());
        dtDate.setValue(LocalDate.parse(editRegistration.getDate()));
        dtDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && (date.isEqual(LocalDate.now()) || date.isBefore(LocalDate.now()))) {
                    setDisable(true);
                }
            }
        });
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
