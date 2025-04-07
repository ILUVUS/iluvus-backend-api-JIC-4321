package iluvus.backend.api.model;

import iluvus.backend.api.dto.LocationDto;
import iluvus.backend.api.dto.UserDto;
import iluvus.backend.api.resources.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;

    private boolean isVerified;
    
    //-------NEW----------
    //holds list of blockedUser id's based on who the user has blocked
    @Field("blockedUsers")
    private List<String> blockedUsers; 

    private String fname;
    private String lname;
    private String gender;
    private Date dob;
    private String race;
    private String image;
    private String bio;
    private List<HashMap<String, Object>> notification;
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
        this.image = userDto.getImage();
        this.bio = userDto.getBio();
        this.notification = userDto.getNotification();
        // this.location = userDto.getLocation();

        this.interests = userDto.getInterests();
        this.education = userDto.getEducation();
        this.work = userDto.getWork();
        this.skills = userDto.getSkills();
        this.hobbies = userDto.getHobbies();

        this.friends = userDto.getFriends();
        this.groups = userDto.getGroups();
        
        //---added new reference here for blocked users----------
        this.blockedUsers = userDto.getBlockedUsers();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        String truncated = bio.length() > 150 ? bio.substring(0, 150) : bio;
        this.bio = truncated;
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

    public List<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }


    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public boolean addGroup(String groupId) {
        return this.groups.add(groupId);
    }

    public List<HashMap<String, Object>> getNotification() {
        return notification;
    }

    public void setNotification(List<HashMap<String, Object>> notification) {
        this.notification = notification;
    }

    public void createNotification(String senderId, NotificationType type, String message, String dateTime) {
        if (this.notification == null) {
            this.notification = new ArrayList<>();
        }
        HashMap<String, Object> notification = new HashMap<>();
        notification.put("id", UUID.randomUUID().toString());
        notification.put("senderId", senderId);
        notification.put("type", type);
        notification.put("message", message);
        notification.put("datetime", dateTime);
        this.notification.add(notification);
    }

}
