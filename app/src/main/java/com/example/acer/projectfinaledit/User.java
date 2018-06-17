package com.example.acer.projectfinaledit;

public class User {

    private String username, lastname, email, birthday, gender, exlevel, diseases, foodallergy;
    private int id, weight, height;

    public User(int id, String username, String lastname, String email, String birthday, String gender, int weight, int height, String exlevel, String diseases, String foodallergy) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.exlevel = exlevel;
        this.diseases = diseases;
        this.foodallergy = foodallergy;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public String getLastName() { return lastname;}

    public String getEmail() {
        return email;
    }

    public String getBirthday() { return birthday; }

    public String getGender() {
        return gender;
    }

    public int getHeight() { return height; }

    public int getWeight() { return weight; }

    public String getDiseases() { return diseases; }

    public String getExlevel() { return exlevel; }

    public String getFoodallergy() { return foodallergy; }
}
