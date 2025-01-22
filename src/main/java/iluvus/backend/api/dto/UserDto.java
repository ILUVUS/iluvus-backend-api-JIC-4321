package iluvus.backend.api.dto;

import iluvus.backend.api.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    private String image;
    private int verifyCode;
    private List<HashMap<String, Object>> notification;

    private List<Integer> interests;
    private List<String> education;
    private List<String> work;

    //testing this, need to check if it will work
    private List<Integer> skills;

    private List<String> hobbies;

    private List<User> friends;
    private List<String> groups;

    private User user;

    public UserDto() {
    }

    public UserDto(User user) {
        this.user = user;
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

            e.printStackTrace();
        }
    }

    public void setDob(String dob) { // yyyy-MM-dd
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dob = formatter.parse(dob);
        } catch (ParseException e) {
            this.dob = null;

            e.printStackTrace();
        }
    }

    public void setNotification(List<HashMap<String, Object>> notification) {
        this.notification = notification;
    }

    public List<HashMap<String, Object>> getNotification() {
        return notification;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public List<Integer> getInterests() {
        return interests;
    }

    public void setInterests(List<Integer> interests) {

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

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
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

    public HashMap<String, Object> getPublicUserInfo() {
        HashMap<String, Object> publicInfo = new HashMap<>();
        publicInfo.put("id", user.getId());
        publicInfo.put("username", user.getUsername());
        publicInfo.put("email", user.getEmail());
        publicInfo.put("isVerified", user.isVerified());
        publicInfo.put("fname", user.getFname());
        publicInfo.put("lname", user.getLname());
        publicInfo.put("dob", user.getDob());
        publicInfo.put("gender", user.getGender());
        publicInfo.put("image", user.getImage());
        return publicInfo;
    }
}
