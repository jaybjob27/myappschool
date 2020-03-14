package com.example.jobndirangu.schoolportalms.Model;

public class Fees {
    private String feeid,totalfee,feepaid,feebal,userId;

    public Fees()
    {

    }

    public Fees(String feeid, String totalfee, String feepaid, String feebal, String userId) {
        this.feeid = feeid;
        this.totalfee = totalfee;
        this.feepaid = feepaid;
        this.feebal = feebal;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(String totalfee) {
        this.totalfee = totalfee;
    }

    public String getFeepaid() {
        return feepaid;
    }

    public void setFeepaid(String feepaid) {
        this.feepaid = feepaid;
    }

    public String getFeebal() {
        return feebal;
    }

    public void setFeebal(String feebal) {
        this.feebal = feebal;
    }
}
