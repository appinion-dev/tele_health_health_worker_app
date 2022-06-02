package com.aah.sftelehealthworker.models;

import androidx.annotation.Nullable;

public class Assessment {
    private String id;
    private String name;
    private String time;

    public Assessment() {
    }

    public Assessment(String id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
