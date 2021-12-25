package com.faculty.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class ModelBase<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    protected Long id;

//    @Column(name = "created_at")
//    protected LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "updated_at")
//    protected LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    protected void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    @Override
    public boolean equals(Object object) {
        if (null == object) {
            return false;
        }

        if (! (object instanceof ModelBase)) {
            return false;
        }

        ModelBase trans = (ModelBase) object;
        String className = trans.getClass().getName();

        if (getClass().getName().equals(className) && (getId() != null && getId().equals(trans.getId()))) {
            return true;
        } else if (null == getId()) {
            return super.equals(trans);
        }

        return false;
    }
}
