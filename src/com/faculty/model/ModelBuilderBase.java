package com.faculty.model;

public abstract class ModelBuilderBase<T> {
    public abstract boolean isValid();
    public abstract T build();
}
