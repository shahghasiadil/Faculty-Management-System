package com.faculty.fxcontrollers.semesters;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Classes;
import com.faculty.model.Semester;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditSemesterController implements Initializable {
    @FXML
    private TextField txtSemesterName;

    @FXML
    private JFXDatePicker dtStart;

    @FXML
    private JFXDatePicker dtEnd;

    @FXML
    private ComboBox<Classes> cmbClasses;

    private FormValidator validator = new FormValidator();
    private static Semester editSemester;

    public static void setEditSemester(Semester editSemester){
        EditSemesterController.editSemester=editSemester;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRules();
        fillControls();
        cmbClasses.getItems().addAll(DBUtil.getAll(Classes.class));
    }

    @FXML
    void saveBtnAction(ActionEvent event){
        if (!validator.isValid()){

            System.err.println("Semester info is not valid...");
            return;
        }
        editSemester.setSemester_name(txtSemesterName.getText());
        editSemester.setStart_date(dtStart.getValue().toString());
        editSemester.setEnd_date(dtEnd.getValue().toString());
        editSemester.setClasses(cmbClasses.getSelectionModel().getSelectedItem());

        DBUtil.saveOrUpdate(editSemester);
        backBtnAction(event);


    }

    @FXML
    void backBtnAction(ActionEvent event){

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/Semesters/SemesterScreen.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("classes/Semesters/SemesterScreen.fxml"));
    }

    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }

    void fillControls(){
        txtSemesterName.setText(editSemester.getSemester_name());
        dtStart.setValue(LocalDate.parse(editSemester.getStart_date()));
        dtEnd.setValue(LocalDate.parse(editSemester.getEnd_date()));
        cmbClasses.setValue(editSemester.getClasses());
    }
    void setRules(){
        ValidationRule dateValidationRule = new ValidationRule(dtEnd, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && ! dp.getValue().isBefore(LocalDate.now());
            }

            return false;
        });
        ValidationRule startdateValidationRule = new ValidationRule(dtStart, "");

        startdateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() &&!dp.getValue().isBefore(LocalDate.now());
            }

            return false;
        });

        ValidationRule classesValidationRule = new ValidationRule(cmbClasses, "");

        classesValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox classesComboBox = (ComboBox) comboBox;

                return classesComboBox.getValue() != null;
            }

            return false;
        });

        dtStart.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
        dtEnd.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });


        Pattern namessPattern= Pattern.compile("^[a-z 0-9]{2,15}$",Pattern.CASE_INSENSITIVE);

        validator.addRule("semesterName",new ValidationRule(txtSemesterName,namessPattern));
        validator.addRule("class",classesValidationRule);
        validator.addRule("endDate",dateValidationRule);
        validator.addRule("startDate",startdateValidationRule);
    }

}
