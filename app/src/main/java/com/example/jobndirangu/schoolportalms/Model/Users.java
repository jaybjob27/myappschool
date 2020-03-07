package com.example.jobndirangu.schoolportalms.Model;

public class Users
{
    private String name,regno,password,address,image;

    public Users()
    {

    }

    public Users(String name, String regno, String password, String address, String image )
    {
        this.name = name;
        this.regno = regno;
        this.password = password;
        this.address = address;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRegno()
    {
        return regno;
    }

    public void setRegno(String regno)
    {
        this.regno = regno;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
