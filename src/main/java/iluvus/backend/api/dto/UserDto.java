package iluvus.backend.api.dto;

import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.Post;
import iluvus.backend.api.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserDto {
    private String username;
    private String email;
    private String password;
    private boolean isVerified;
    private String proEmail;
    private String fname;
    private String lname;
    private String gender;
    private Date dob;
    private String race;
    private int verifyCode;
    // private LocationDto location;

    private List<String> interests;
    private List<String> education;
    private List<String> work;
    private List<String> skills;
    private List<String> hobbies;
    
    private List<User> friends;
    private List<String> groups;

    public UserDto() {
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(String mm, String dd, String yyyy) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dob = formatter.parse(yyyy + "-" + mm + "-" + dd);
        } catch (ParseException e) {
            this.dob = null;
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
    }

    public void setDob(String dob) { // yyyy-MM-dd
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dob = formatter.parse(dob);
        } catch (ParseException e) {
            this.dob = null;
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getVerifyCode() {
         return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
         this.verifyCode = verifyCode;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public List<String> getWork() {
        return work;
    }

    public void setWork(List<String> work) {
        this.work = work;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }


    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getProEmail() {
        return proEmail;
    }

    public void setProEmail(String proEmail) {
        this.proEmail = proEmail;
    }
}