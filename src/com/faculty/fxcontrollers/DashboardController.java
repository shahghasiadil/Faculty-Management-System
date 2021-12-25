package com.faculty.fxcontrollers;

import com.faculty.support.ViewUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private JFXButton btnStudents;

    @FXML
    private JFXButton btnLecturers;

    @FXML
    private JFXButton btnClasses;
    @FXML
    private JFXButton btnExaminations;
    @FXML
    private JFXButton btnSemesters;

    @FXML
    private JFXButton btnCourses;

    @FXML
    private JFXButton btnRegisteration;

    @FXML
    private JFXButton btnAttendance;

    @FXML
    private JFXButton btnMarks;

    @FXML
    private JFXButton btnComplains;

    @FXML
    private JFXButton btnUsers;

    @FXML
    private  JFXButton btnSchedules;

    @FXML
    private JFXButton btnLogout;

    private static AnchorPane staticRoot;
    @FXML
    private AnchorPane homeRoot;
//    private BorderPane pane=FacultyManagementController.getRoot();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        staticRoot=homeRoot;
    }

    @FXML
     void handleButtonClicks(ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource()==btnStudents) {

            homeRoot.getChildren().setAll(ViewUtil.loadView("Student/StudentScreen.fxml"));

        }else  if (mouseEvent.getSource()==btnLecturers){

            homeRoot.getChildren().setAll(ViewUtil.loadView("lecturers/LecturersScreen.fxml"));
        }else if (mouseEvent.getSource()==btnUsers){

            homeRoot.getChildren().setAll(ViewUtil.loadView("Users/UsersScreen.fxml"));
        }
        else if (mouseEvent.getSource()==btnCourses){

             homeRoot.getChildren().setAll(ViewUtil.loadView("courses/CoursesScreen.fxml"));
        }else if (mouseEvent.getSource()==btnAttendance){
            homeRoot.getChildren().setAll( ViewUtil.loadView("Attendence/AttendenceScreen.fxml"));
//
        }else if (mouseEvent.getSource()==btnClasses){
            homeRoot.getChildren().setAll(ViewUtil.loadView("classes/ClassesScreen.fxml"));

        }else if (mouseEvent.getSource()==btnMarks){
                homeRoot.getChildren().setAll(ViewUtil.loadView("Marks/MarksScreen.fxml"));
        }else if (mouseEvent.getSource()==btnSemesters){
                homeRoot.getChildren().setAll(ViewUtil.loadView("classes/Semesters/SemesterScreen.fxml"));
        }else if (mouseEvent.getSource()==btnExaminations){
                homeRoot.getChildren().setAll(ViewUtil.loadView("Examination/Exam_ScheduleScreen.fxml"));
        }else if (mouseEvent.getSource()==btnUsers){

//            content.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/UserRegistraionScreen.fxml")));

        }else if(mouseEvent.getSource()==btnRegisteration){
                homeRoot.getChildren().setAll(ViewUtil.loadView("Registration/RegistrationScreen.fxml"));
//            content.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/Registration/RegistrationScreen.fxml")));

        }else if(mouseEvent.getSource()==btnSchedules) {
            homeRoot.getChildren().setAll(ViewUtil.loadView("schedule/ScheduleScreen.fxml"));

        } else if (mouseEvent.getSource()==btnLogout){

            FacultyManagementController.getStaticRoot().getChildren().setAll(ViewUtil.loadView("login.fxml"));
        }
        else if (mouseEvent.getSource()==btnComplains){

            homeRoot.getChildren().setAll(ViewUtil.loadView("complains/ComplainsScreen.fxml"));
//            content.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/complains/ComplainsScreen.fxml")));
        }


    }

    public static AnchorPane getStaticRoot(){

        return staticRoot;
    }



}
