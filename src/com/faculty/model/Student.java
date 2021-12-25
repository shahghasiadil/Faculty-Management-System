package com.faculty.model;

import com.faculty.support.DBUtil;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends ModelBase<Student>{


    @Column(name = "first_name")
    private  String std_first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "ssn")
    private  long SSN;
    @Column(name = "birth_date")
    private LocalDate birth_date;
//    @OneToOne(cascade = CascadeType.PERSIST ,orphanRemoval = true)
////    @JoinColumn(name = "user_id")
//    private Users users;
    @OneToMany(mappedBy = "students",cascade = CascadeType.ALL)
    private List<Complain> complains = new ArrayList<>();
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private  List<Registration> registrations = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "students")
//    @JoinColumn(name = "attendence_id")
    private List<Attendence>  attendences= new ArrayList<>();
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private  List<Marks> marks =new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private  Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }



    public void addMarks(Marks mark){

        marks.add(mark);
        mark.setStudent(this);
    }



    public  void addRegistration(Registration registration){
            registrations.add(registration);
            registration.setStudents(this);
    }

    public  void addComplain(Complain complain){

        complains.add(complain);
        complain.setStudents(this);
    }

    public  void addAttendence(Attendence attendence){

        attendences.add(attendence);
        attendence.setStudents(this);
    }
    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    public List<Attendence> getAttendence() {
        return attendences;
    }

    public void setAttendence(List<Attendence> attendence) {
        this.attendences = attendence;
    }

    public List<Complain> getComplains() {
        return complains;
    }

    public void setComplains(List<Complain> complains) {
        this.complains = complains;
    }



    public  String  getStd_first_name() {
        return std_first_name;
    }


    public void setStd_first_name(String std_first_name) {
        this.std_first_name = std_first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public long getSSN() {
        return SSN;
    }

    public void setSSN(long SSN) {
        this.SSN = SSN;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

//    public Users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Users users) {
//        this.users = users;
//    }
public static List<Student> getByStudentname(String std_first_name) {
    final String hql = "FROM Student where " +
            "std_first_name = :std_first_name " ;

    return DBUtil.execute(session -> {
        List<Student>  students= new ArrayList<>();
        Query<Student> usersQuery = session.createQuery(hql, Student.class);
        usersQuery.setParameter("std_first_name", std_first_name);
        List<Student> allStudent = usersQuery.list();
        return allStudent;
    });
//}
//
//    public static List<Student> getItemsWithStock(String std_first_name) {
//        return DBUtil.execute(sess -> {
//            List<Student> students = new ArrayList<>();
//
//            Query query = sess.createQuery("from Student  where std_first_name=:std_first_name");
//            query.setParameter(std_first_name,query);
//            try {
//                students = query.list();
//            } catch (Exception ex) {
//                System.err.println(ex.getMessage());
//            }
//
//            return students;
//        });

    }

    @Override
    public String toString() {
        return  std_first_name+" "+last_name;
    }
}
