package iluvus.backend.api.model;

import iluvus.backend.api.dto.PostDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String content;
    private Date dateTime;
    private List<String> images;
    private List<String> comments;
    private BigInteger uplift;
    private boolean visible;

    @DBRef
    private User author;
    @DBRef
    private Community group;

    public Post() {
    }

    public Post(PostDto postDto) {
        this.content = postDto.getContent();
        this.dateTime = postDto.getDate();
        this.images = postDto.getImages();
        this.comments = postDto.getComments();
        this.uplift = postDto.getUplift();
        this.visible = postDto.isVisible();
        this.author = postDto.getAuthor();
        this.group = postDto.getGroup();
    }

    // get id
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateTime() { return dateTime; }

    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

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

