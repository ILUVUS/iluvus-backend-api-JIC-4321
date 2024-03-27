package iluvus.backend.api.model;

import iluvus.backend.api.dto.LocationDto;
import iluvus.backend.api.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;

    private boolean isVerified;

    private String fname;
    private String lname;
    private String gender;
    private Date dob;
    private String race;
    private List<HashMap<String, String>> notification;
    private LocationDto location;

    private List<Integer> interests;
    private List<String> education;
    private List<String> work;
    private List<String> skills;
    private List<String> hobbies;

    @Field("friends")
    private List<User> friends;

    @Field("groups")
    private List<String> groups;

    public User() {
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.isVerified = userDto.isVerified();

        this.fname = userDto.getFname();
        this.lname = userDto.getLname();
        this.gender = userDto.getGender();
        this.dob = userDto.getDob();
        this.race = userDto.getRace();
        this.notification = userDto.getNotification();
        // this.location = userDto.getLocation();

        this.interests = userDto.getInterests();
        this.education = userDto.getEducation();
        this.work = userDto.getWork();
        this.skills = userDto.getSkills();
        this.hobbies = userDto.getHobbies();

        this.friends = userDto.getFriends();
        this.groups = userDto.getGroups();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
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

    public boolean addGroup(String groupId) {
        return this.groups.add(groupId);
    }

    public List<HashMap<String, String>> getNotification() {
        return notification;
    }

    public void setNotification(List<HashMap<String, String>> notification) {
        this.notification = notification;
    }

}
