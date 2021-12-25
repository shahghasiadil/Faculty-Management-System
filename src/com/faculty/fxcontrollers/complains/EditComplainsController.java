package com.faculty.fxcontrollers.complains;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Complain;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import com.faculty.validator.FormValidator;
import com.faculty.validator.ValidationRule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.time.LocalDate.parse;

public class EditComplainsController implements Initializable {
    @FXML
    private ComboBox<Student> cmbStudent;

    @FXML
    private TextArea txtComplains;

    @FXML
    private TextArea txtReply;

    @FXML
    private DatePicker dtDate;
    private  static Complain editComplain;

    public  static void  setEditComplain(Complain editComplain){

        EditComplainsController.editComplain=editComplain;
    }
    FormValidator validator = new FormValidator();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillControls();
        setRules();

    }
    void fillControls(){

        cmbStudent.getItems().addAll(DBUtil.getAll(Student.class));

        cmbStudent.setValue(editComplain.getStudents());
        txtComplains.setText(editComplain.getComplain());
        txtReply.setText(editComplain.getReply());
        dtDate.setValue(parse(editComplain.getEnterDate()));

    }


    @FXML
    void saveBtnAction(ActionEvent event){
        if (!validator.isValid()){
            System.err.println("Complains info is not valid...");
            return;
        }
        editComplain.setComplain(txtComplains.getText());
        editComplain.setEnterDate(dtDate.getValue().toString());
        editComplain.setStudents(cmbStudent.getSelectionModel().getSelectedItem());
        editComplain.setReply(txtReply.getText());

        DBUtil.update(editComplain);
        backBtnAction(event);

    }
    @FXML
    void backBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("complains/ComplainsScreen.fxml"));

//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("complains/ComplainsScreen.fxml"));
    }
    @FXML
    void cancelBtnAction(ActionEvent event){

        backBtnAction(event);
    }
    void setRules(){
        ValidationRule studentValidationRule = new ValidationRule(cmbStudent, "");

        studentValidationRule.setValidator(comboBox -> {
            if (comboBox instanceof ComboBoxBase) {
                ComboBox studentComboBox = (ComboBox) comboBox;

                return studentComboBox.getValue() != null;
            }

            return false;
        });

        ValidationRule dateValidationRule = new ValidationRule(dtDate, "");

        dateValidationRule.setValidator(datePicker -> {
            if (datePicker instanceof DatePicker) {
                DatePicker dp = (DatePicker) datePicker;

                return null != dp.getValue() && ! dp.getValue().isAfter(LocalDate.now());
            }

            return false;
        });


        dtDate.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (null != date && date.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
        Pattern complainPattern = Pattern.compile("^[a-z 0-9]{2,100}$", Pattern.CASE_INSENSITIVE);
        Pattern replyPattern = Pattern.compile("^[a-z 0-9]{2,100}$", Pattern.CASE_INSENSITIVE);
        validator.addRule("student",studentValidationRule);
        validator.addRule("date",dateValidationRule);
        validator.addRule("complain",new ValidationRule(txtComplains,complainPattern));
        validator.addRule("reply",new ValidationRule(txtReply,replyPattern));

    }



}
