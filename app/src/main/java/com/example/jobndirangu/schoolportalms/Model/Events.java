package com.example.jobndirangu.schoolportalms.Model;

public class Events {

    private String evntname,evntdescription,evntdate,image,evntcategory,pid,date,tim;

    public Events()
    {

    }

    public Events(String evntname, String evntdescription, String evntdate, String image, String evntcategory, String pid, String date, String tim) {
        this.evntname = evntname;
        this.evntdescription = evntdescription;
        this.evntdate = evntdate;
        this.image = image;
        this.evntcategory = evntcategory;
        this.pid = pid;
        this.date = date;
        this.tim = tim;
    }

    public String getEvntname() {
        return evntname;
    }

    public void setEvntname(String evntname) {
        this.evntname = evntname;
    }

    public String getEvntdescription() {
        return evntdescription;
    }

    public void setEvntdescription(String evntdescription) {
        this.evntdescription = evntdescription;
    }

    public String getEvntdate() {
        return evntdate;
    }

    public void setEvntdate(String evntdate) {
        this.evntdate = evntdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEvntcategory() {
        return evntcategory;
    }

    public void setEvntcategory(String evntcategory) {
        this.evntcategory = evntcategory;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }
}
