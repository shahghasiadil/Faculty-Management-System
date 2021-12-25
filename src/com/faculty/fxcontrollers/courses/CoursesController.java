package com.faculty.fxcontrollers.courses;

import com.faculty.fxcontrollers.DashboardController;
import com.faculty.model.Course;
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

public class CoursesController implements Initializable {

    @FXML
    private TableView<Course> tbData;

    @FXML
    private TableColumn<Course,String> clmCourseName;
    @FXML
   private TableColumn<Course,String > clmCourseCredit;

    @FXML
    private TableColumn<Course,String> clmLecturer;

    @FXML
   private TableColumn<Course,String>clmSemester;

    @FXML
    private Button btnAdd;

    @FXML
    private  Button btnDelete;

    @FXML
    private  Button btnUpdate;

    @FXML
    private TextField txtSearch;

    private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<>(this,"selectedCourse");

    private ObservableList<Course> coursesObservableList = FXCollections.observableList(DBUtil.getAll(Course.class));
    private FilteredList<Course> courseFilteredList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    setColumnValueFactories();
    tbData.setItems(coursesObservableList);

    selectedCourse.bind(tbData.getSelectionModel().selectedItemProperty());
        btnUpdate.disableProperty().bind(selectedCourse.isNull());
        btnDelete.disableProperty().bind(btnUpdate.disableProperty());
        courseFilteredList = new FilteredList<>(coursesObservableList,e->true);
    }

    @FXML
    // Fast Searching on every column
    void onSearchAction(){

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            courseFilteredList.setPredicate((Course course) ->{
                if (newValue==null|| newValue.isEmpty()){

                    return true;
                }

                String toLowerCaseFilter = newValue.toLowerCase();
                // Search by Course name;
                if(course.getCourse_name().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by Course Credits
                }else if(String.valueOf(course.getC_Credits()).toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by Course Lecturer
                }else if (course.getLecturers().toString().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                    // Search by Semester name
                }else if (course.getSemester().getSemester_name().toLowerCase().contains(toLowerCaseFilter)){
                    return true;
                }
                return false;
            });

        }));

        SortedList<Course> sortedList = new SortedList<>(courseFilteredList);

        sortedList.comparatorProperty().bind(tbData.comparatorProperty());

        tbData.setItems(sortedList);

    }
    @FXML
    void addBtnAction(ActionEvent event){
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("courses/CreateCourses.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("courses/CreateCourses.fxml"));

    }
    @FXML
    void editBtnAction (ActionEvent event){
        EditCourses.setEditCourse(selectedCourse.get());
        DashboardController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("courses/EditCourses.fxml"));
//        FacultyManagementController.getRoot().setCenter(ViewUtil.loadView("courses/EditCourses.fxml"));


    }

    @FXML
    void deleteBtnAction (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> action =alert.showAndWait();

        if(ButtonType.OK.equals(action.get())){

            DBUtil.delete(selectedCourse.get());
            tbData.getItems().remove(selectedCourse.get());
            tbData.refresh();
        }

    }


    void setColumnValueFactories(){

        clmCourseName.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getCourse_name()));
        clmCourseCredit.setCellValueFactory(cdf-> new SimpleStringProperty(String.valueOf(cdf.getValue().getC_Credits())));
        clmLecturer.setCellValueFactory(cdf-> new SimpleStringProperty(cdf.getValue().getLecturers().toString()));
        clmSemester.setCellValueFactory(cdf->new SimpleStringProperty(cdf.getValue().getSemester().getSemester_name()));
    }


}
