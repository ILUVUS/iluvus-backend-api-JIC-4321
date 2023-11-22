package iluvus.backend.api.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String content;
    private String date;
    private String time;
    private List<String> images;
    private List<String> comments;
    private String[] uplift;
    private String[] downvote;
    private boolean visible;

    @DBRef
    private User author;
    @DBRef
    private Community group;

    public Post() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String[] getUplift() {
        return uplift;
    }

    public void setUplift(String[] uplift) {
        this.uplift = uplift;
    }

    public String[] getDownvote() {
        return downvote;
    }

    public void setDownvote(String[] downvote) {
        this.downvote = downvote;
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

