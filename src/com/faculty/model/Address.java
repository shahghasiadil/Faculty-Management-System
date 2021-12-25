package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "address")
public class Address  extends ModelBase<Address>{

    @Column(name = "city")
    private  String city;
    @Column(name = "district")
    private String district;
//    @OneToMany( mappedBy = "address")
//    private List<Users> user = new ArrayList<Users>();
    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
    private List<Lecturer> lecturers = new ArrayList<>();

    @OneToMany(mappedBy = "address" ,cascade = CascadeType.ALL)
    private  List<Student> students = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
//    private  List<Staff> staff = new ArrayList<>();

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }
//
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
//
//    public List<Staff> getStaff() {
//        return staff;
//    }
//
//    public void setStaff(List<Staff> staff) {
//        this.staff = staff;
//    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return city +"," +district;
    }
}
