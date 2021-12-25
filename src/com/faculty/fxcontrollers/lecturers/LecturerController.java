package com.faculty.fxcontrollers.lecturers;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Lecturer;
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

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LecturerController implements Initializable {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtSearch;
    @FXML
    private  Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Lecturer> tbData;

    @FXML
    private TableColumn<Lecturer,String> clmFirstName;

    @FXML
    private TableColumn<Lecturer,String> clmLastName;

    @FXML
    private TableColumn<Lecturer,String> clmPhone;

    @FXML
    private TableColumn<Lecturer,String> clmEmail;

    @FXML
    private TableColumn<Lecturer,String> clmAddress;

//    private ObjectProperty<Lecturer> selectedLecturer = new SimpleObjectProperty <>(this,"selectedLecturer");
    private ObjectProperty<Lecturer> selectedLecturer = new SimpleObjectProperty<>(this, "selectedLecturer");
    private ObservableList<Lecturer> lecturerObservableList = FXCollections.observableList(DBUtil.getAll(Lecturer.class));
    private FilteredList<Lecturer> lecturerFilteredList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            tbData.setItems(lecturerObservableList);
            selectedLecturer.bind(tbData.getSelectionModel().selectedItemProperty());
            btnUpdate.disableProperty().bind(selectedLecturer.isNull());
            btnDelete.disableProperty().bind(btnUpdate.disableProperty());
            setColumnValueFactories();
            lecturerFilteredList = new FilteredList<>(lecturerObservableList,e-> true);
    }

    void setColumnValueFactories(){

        clmFirstName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getFirst_name()));
        clmLastName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getLast_name()));
        clmPhone.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getPhone_no()));
        clmEmail.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEmail()));
        clmAddress.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getAddress())));

    }
    @FXML
    void onSearchAction(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            lecturerFilteredList.setPredicate(lecturer -> {

                if(newValue.isEmpty()|| newValue==null){

                    return true;
                }
                String  toLowerCaseFilter = newValue.toLowerCase();

                if (lecturer.getAddress().toString().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                }else if(lecturer.getFirst_name().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                } else if (lecturer.getLast_name().toLowerCase().contains(toLowerCaseFilter)) {
                    return true;
                }else if (lecturer.getEmail().toLowerCase().contains(toLowerCaseFilter)){

                    return true;

                }

                return false;
            });

        });

        SortedList<Lecturer> lecturerSortedList = new SortedList<>(lecturerFilteredList);

        lecturerSortedList.comparatorProperty().bind(tbData.comparatorProperty());

        tbData.setItems(lecturerFilteredList);

        }
    @FXML
   void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("lecturers/CreateLecturers.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("lecturers/CreateLecturers.fxml"));
    }
    @FXML
    void editBtnAction(ActionEvent event){
        EditLecturerController.setEditlecturers(selectedLecturer.getValue());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("lecturers/EditLecturers.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("lecturers/EditLecturers.fxml"));
    }

    @FXML
   void  deleteBtnAction (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();

        if (ButtonType.OK.equals(optional.get())){

            DBUtil.delete(selectedLecturer.get());
            tbData.getItems().remove(selectedLecturer.get());
            tbData.refresh();
        }
    }


}
