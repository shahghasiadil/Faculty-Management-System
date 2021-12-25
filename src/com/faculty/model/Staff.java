package com.faculty.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Staff")
public class Staff  extends  ModelBase<Staff>{


    @Column(name = "first_name" )
    private  String first_name;
    @Column(name = "last_name")
    private  String last_name;
    @Column(name = "phone_no")
    private String phone_no;
    @Column(name = "email")
    private String email;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Users users;
//    @OneToMany(mappedBy = "staffs",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
//    private List<Complain> cmp = new ArrayList<>();
    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Exam_Schedule> exam_schedules = new ArrayList<>();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Address address ;
    public List<Exam_Schedule> getExam_schedules() {
        return exam_schedules;
    }

    public void setExam_schedules(List<Exam_Schedule> exam_schedules) {
        this.exam_schedules = exam_schedules;
    }


    public void addExam_Schedule(Exam_Schedule exam_schedule){

        exam_schedules.add(exam_schedule);
        exam_schedule.setStaff(this);
    }

//    public  void addComplain(Complain complain){
//
//        cmp.add(complain);
//        complain.setStaffs(this);
//    }

//
//    public List<Complain> getCmp() {
//        return cmp;
//    }
//
//    public void setCmp(List<Complain> cmp) {
//        this.cmp = cmp;
//    }



    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", email='" + email + '\'' +
                ", users=" + users +
                '}';
    }
}
