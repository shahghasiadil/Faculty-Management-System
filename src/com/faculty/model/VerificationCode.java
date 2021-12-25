package com.faculty.model;

import java.util.Random;

public class VerificationCode {
    private int code;
    private long createdAt;
    private int age;

    public enum AgeType {MILLIS, SECONDS, MINUTES}

    public VerificationCode() {
        this(10, AgeType.MINUTES);
    }

    public VerificationCode(int age, AgeType ageType) {
        setAge(age, ageType);
        generate();
    }

    public int asInt() {
        update();
        return code;
    }

    public String asString() {
        return Integer.toString(asInt());
    }

    public boolean matches(int code) {
        return code == this.code;
    }

    public boolean matches(String code) {
        return code.trim().equals(asString());
    }

    public int validFor(AgeType ageType) {
        Long till = System.currentTimeMillis() - createdAt;
        Integer intTill = till.intValue();

        switch (ageType) {
            case MILLIS:
                return intTill;
            case SECONDS:
                return intTill / 1000;
            case MINUTES:
                return intTill / 1000 / 60;
            default:
                return intTill;
        }
    }

    public boolean expired() {
        return System.currentTimeMillis() - createdAt >= age;
    }

    private void generate() {
        Random rand = new Random();

        int min = 100000;
        int max = 999999;
        int tmpCode = rand.nextInt((max - min) + 1) + min;

        while (Integer.toString(tmpCode).matches("0{3,}")) {
            tmpCode = rand.nextInt((max - min) + 1) + min;
        }

        createdAt = System.currentTimeMillis();
        code = tmpCode;
    }

    private void update() {
        if (expired()) {
            generate();
        }
    }

    public int getCode() {
        update();
        return code;
    }

    public int getAge() {
        return age;
    }

    public int getAge(AgeType ageType) {
        switch (ageType) {
            case MILLIS:
                return age;
            case SECONDS:
                return getAgeBySeconds();
            case MINUTES:
                return getAgeByMinutes();
            default:
                return age;
        }
    }

    public int getAgeBySeconds() {
        return age / 1000;
    }

    public int getAgeByMinutes() {
        return age / 1000 / 60;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAge(int age, AgeType ageType) {
        switch (ageType) {
            case MILLIS: setAge(age); break;
            case SECONDS: setAgeBySeconds(age); break;
            case MINUTES: setAgeByMinutes(age); break;
        }
    }

    public void setAgeBySeconds(int age) {
        this.age = age * 1000;
    }

    public void setAgeByMinutes(int age) {
        this.age = age * 60 * 1000;
    }
}
