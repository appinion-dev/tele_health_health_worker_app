package com.aah.sftelehealthworker.models.newPatient;

import com.aah.sftelehealthworker.models.ResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportsModel extends ResponseModel {
    @SerializedName("reports")
    @Expose
    private List<Report> reports = null;

    public ReportsModel() {
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
