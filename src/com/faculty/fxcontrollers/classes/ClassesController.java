package com.faculty.fxcontrollers.classes;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Classes;
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



public class ClassesController implements Initializable {

    @FXML
     private TableView<Classes>tbData;
    
    @FXML
     private   TableColumn<Classes,String> clmClassName;

    @FXML
    private TableColumn<Classes,String> clmTime;

    @FXML
   private TableColumn<Classes,String> clmRoomNo;

    @FXML
            private Button btnUpdate;

    @FXML
            private Button btnDelete;
    @FXML
            private TextField txtSearch;

   private ObservableList<Classes> classesData = FXCollections.observableList(DBUtil.getAll(Classes.class));
   private FilteredList<Classes> classesFilteredList ;
  private   ObjectProperty<Classes> selectedClass = new SimpleObjectProperty<>(this,"selectedClass");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    setColumnValueFactories();
    tbData.setItems(classesData);
    selectedClass.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedClass.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        classesFilteredList = new FilteredList<Classes>(classesData,e-> true);
    }
    @FXML
    void onSearchAction(){

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            classesFilteredList.setPredicate((Classes c) ->{
                if(newValue.isEmpty()|| newValue==null){

                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                // Search by Class Name
                if (c.getName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(c.getRoom_no().toLowerCase().contains(lowerCaseFilter)){

                    return true;
                }else if (c.getTime().toLowerCase().contains(lowerCaseFilter)){

                    return true;
                }else
                return false;
            });

        }));

        SortedList<Classes> sortedList = new SortedList<>(classesFilteredList);
        sortedList.comparatorProperty().bind(tbData.comparatorProperty());

        tbData.setItems(sortedList);

    }
    void setColumnValueFactories(){

        clmClassName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getName()));
        clmRoomNo.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getRoom_no())));
//        clmRoomNo.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getSemesterName())));
        clmTime.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getTime())));
    }

    @FXML
    void  addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/CreateClasses.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("classes/CreateClasses.fxml"));

    }

    @FXML
    void editBtnAction (ActionEvent event){
        EditClassesController.setEditClasses(selectedClass.getValue());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("classes/EditClasses.fxml"));
//        FacultyManagementController.getRoot().setCenter();

    }
    @FXML
    void deleteBtnAction(ActionEvent event){

        Alert alert =new Alert(Alert.AlertType.CONFIRMATION," Are you sure?");

        Optional<ButtonType> action = alert.showAndWait();
        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedClass.get());
            tbData.getItems().remove(selectedClass.get());
            tbData.refresh();
        }

    }







}
