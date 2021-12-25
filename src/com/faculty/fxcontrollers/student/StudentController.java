package com.faculty.fxcontrollers.student;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Student;
import com.faculty.support.DBUtil;
import com.faculty.support.ViewUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class StudentController implements Initializable {
    @FXML
   private  Button btnAdd;
    @FXML
    private  Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtsearch;

    @FXML
    private TableView<Student> tbData;

    @FXML
   private TableColumn<Student,String> stdFirstName;

    @FXML
    private TableColumn<Student,String> stdLastName;

    @FXML
    private TableColumn<Student, String> stdBirthDate;

    @FXML
  private   TableColumn<Student,String>  stdSSN;
    @FXML
    private TableColumn<Student,String> clmAddress;
    @FXML
    private   AnchorPane students;


    private  ObservableList<Student> data= FXCollections.observableList(DBUtil.getAll(Student.class));
    private FilteredList<Student>filteredList;
    private ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>(this, "selectedStudent");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    setColumnValueFoctories();
    tbData.setItems(data);
    selectedStudent.bind(tbData.getSelectionModel().selectedItemProperty());
    btnUpdate.disableProperty().bind(selectedStudent.isNull());
    btnDelete.disableProperty().bind(btnUpdate.disableProperty());
    filteredList = new FilteredList<>(data,e-> true);
}

    @FXML
    public void onSearchAction(){
        txtsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Student student) -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (student.getStd_first_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (student.getLast_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }   else if (String.valueOf(student.getSSN()).toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches  with ssn
            } else if (String.valueOf(student.getBirth_date()).toLowerCase().contains(lowerCaseFilter)){

                    return true;
                }else if (student.getAddress().toString().toLowerCase().contains(lowerCaseFilter)){

                    return true;
                }
                else
                    return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Student> sortedData = new SortedList<>(filteredList);


        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tbData.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tbData.setItems(sortedData);

    }




    @FXML
     void addBtnAction(ActionEvent event) throws IOException {

        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Student/CreateStudent.fxml"));

    }
    private  void  setColumnValueFoctories(){
        stdFirstName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStd_first_name()) {
        });


        stdLastName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getLast_name()));

        stdSSN.setCellValueFactory(cdf-> new SimpleStringProperty(String.format("%,d", cdf.getValue().getSSN())));

//     stdBirthDate.setCellValueFactory(cdf-> new MonthSheetView.SimpleDateCell(cdf.getValue().getBirth_date()));
        stdBirthDate.setCellValueFactory( cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getBirth_date())));
        clmAddress.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getAddress())));


}

     @FXML
    void editBtnAction(ActionEvent event){
        EditStudentController.setEditStudent(selectedStudent.getValue());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("Student/EditStudent.fxml"));
     }


    @FXML
    void deleteBtnAction(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action = alert.showAndWait();
        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedStudent.get());
            tbData.getItems().remove(selectedStudent.get());
            tbData.refresh();

        }



    }





}
