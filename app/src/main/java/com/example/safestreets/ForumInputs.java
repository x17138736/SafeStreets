package com.example.safestreets;

public class ForumInputs {

    private String name;
    private String email;
    private String streetname;
    private String experience;

    public ForumInputs(String name, String email, String streetname, String experience) {
        this.name = name;
        this.email = email;
        this.streetname = streetname;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetName) {
        this.streetname = streetName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
