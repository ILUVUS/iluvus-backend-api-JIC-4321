package iluvus.backend.api.dto;

import java.math.BigInteger;
import java.util.List;

public class PostDto {

    private String content;
    private String date;
    private String time;
    private List<String> images;
    private List<String> comments;
    private BigInteger uplift;
    private boolean visible;

    private UserDto author;
    private CommunityDto group;

    public PostDto() {
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

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public CommunityDto getGroup() {
        return group;
    }

    public void setGroup(CommunityDto group) {
        this.group = group;
    }
}

