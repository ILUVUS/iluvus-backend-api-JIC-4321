package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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
    private Location location;

    private String[] interests;
    private String[] education;
    private String[] work;
    private String[] skills;
    private String[] hobbies;

    @DBRef
    private List<Post> posts;
    @DBRef
    private List<User> friends;
    @DBRef
    private List<Community> groups;

    public User() {
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for isVerified
    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    // Getter and Setter for fname
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    // Getter and Setter for lname
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    // Getter and Setter for gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter and Setter for dob
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Getter and Setter for race
    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    // Getter and Setter for location
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Getter and Setter for interests
    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    // Getter and Setter for education
    public String[] getEducation() {
        return education;
    }

    public void setEducation(String[] education) {
        this.education = education;
    }

    // Getter and Setter for work
    public String[] getWork() {
        return work;
    }

    public void setWork(String[] work) {
        this.work = work;
    }

    // Getter and Setter for skills
    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    // Getter and Setter for hobbies
    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    // Getter and Setter for posts
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Getter and Setter for friends
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    // Getter and Setter for groups
    public List<Community> getGroups() {
        return groups;
    }

    public void setGroups(List<Community> groups) {
        this.groups = groups;
    }
}