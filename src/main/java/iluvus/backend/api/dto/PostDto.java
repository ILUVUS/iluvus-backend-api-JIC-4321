package iluvus.backend.api.dto;

import iluvus.backend.api.model.Community;
import iluvus.backend.api.model.User;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostDto {

    private String content;
    private Date date;
    private String time;
    private List<String> images;
    private List<String> comments;
    private BigInteger uplift;
    private boolean visible;

    private User author;
    private Community group;

    public PostDto() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    // yyyy-MM-dd hh:mm:ss
    public void setDateTime(String mm, String dd, String yyyy, String h, String m, String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            this.date = formatter.parse(yyyy + "-" + mm + "-" + dd + " " + h + ":" + m + ":" + s);
        } catch (ParseException e) {
            this.date = null;
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
    }

    public void setDateTime(String dob) { // yyyy-MM-dd hh:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            this.date = formatter.parse(dob);
        } catch (ParseException e) {
            this.date = null;
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public BigInteger getUplift() {
        return uplift;
    }

    public void setUplift(BigInteger uplift) {
        this.uplift = uplift;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Community getGroup() {
        return group;
    }

    public void setGroup(Community group) {
        this.group = group;
    }
}
