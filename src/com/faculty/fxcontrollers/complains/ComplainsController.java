package com.faculty.fxcontrollers.complains;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Complain;
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

public class ComplainsController implements Initializable  {
    @FXML
    private TableView<Complain> tbData;

    @FXML
    private TableColumn<Complain,String> clmComplains;

    @FXML
    private TableColumn<Complain,String> clmStudent;

    @FXML
    private TableColumn<Complain,String> clmDate;

    @FXML
    private TableColumn<Complain,String>clmReply;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
            private TextField txtSearch;

    ObjectProperty<Complain>  selectedComplain = new SimpleObjectProperty<>(this,"selectedComplain");
//    private final ObservableList<Complain> complainObservableList = new FXCollections.observableList(DBUtil.getAll(Complain.class));
    private ObservableList<Complain> complainObservableList = FXCollections.observableList(DBUtil.getAll(Complain.class));
    private FilteredList<Complain> complainFilteredList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbData.setItems(complainObservableList);
        selectedComplain.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedComplain.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        setColoumnValuesFactories();
        complainFilteredList = new FilteredList<>(complainObservableList,e-> true);
    }

    void  setColoumnValuesFactories(){
        clmComplains.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getComplain()));
        clmDate.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getEnterDate()));
        clmStudent.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getStudents().toString()));
        clmReply.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getReply()));


    }

    @FXML
    void onSearchAction(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            complainFilteredList.setPredicate((Complain cmp ) ->{
                if (newValue.isEmpty()|| newValue==null){
                    return true;
                }

                String toLowerCaseFilter = newValue.toLowerCase();

                // Search by Student in Complains
                if (cmp.getStudents().toString().toLowerCase().contains(toLowerCaseFilter)){

                    return true;
                }
                return false;
            });

        });

        SortedList<Complain> sortedList = new SortedList<>(complainFilteredList);

        sortedList.comparatorProperty().bind(tbData.comparatorProperty());

        tbData.setItems(sortedList);
    }

    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("complains/CreateComplains.fxml"));
//        FacultyManagementController.getRoot().setCenter();
    }

    @FXML
    void  editBtnAction(ActionEvent event){
        EditComplainsController.setEditComplain(selectedComplain.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("complains/EditComplains.fxml"));
//        FacultyManagementController.getRoot().setCenter();


    }

    @FXML
    void deleteBtnAction(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action =alert.showAndWait();

        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedComplain.get());
            tbData.getItems().remove(selectedComplain.get());
            tbData.refresh();
        }
    }


}
