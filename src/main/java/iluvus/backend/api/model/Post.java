package iluvus.backend.api.model;

import iluvus.backend.api.dto.PostDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.*;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String text;

    @Field("datetime")
    private String dateTime;

    private BigInteger uplift;
    private String author_id;
    private String community_id;

    @Field("comments")
    private List<HashMap<String, String>> comments;
    
    public String getId() {
        return this.id;
    }

    public String getText() {
        return text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public BigInteger getUplift() {
        return uplift;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public List<HashMap<String, String>> getComments() {
        return comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setUplift(BigInteger uplift) {
        this.uplift = uplift;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public void setComments(List<HashMap<String, String>> comments) {
        this.comments = comments;
    }

    public Post() {
    }

    public Post(PostDto postDto) {
        this.text = postDto.getText();
        this.dateTime = postDto.getDateTime();
        this.uplift = postDto.getUplift();
        this.author_id = postDto.getAuthor_id();
        this.community_id = postDto.getCommunity_id();
        this.comments = new ArrayList<>();
    }


    public String getText() {
        return this.text;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public BigInteger getUplift() {
        return this.uplift;
    }

    public String getAuthor_id() {
        return this.author_id;
    }

    public String getCommunity_id() {
        return this.community_id;
    }

    public List<HashMap<String, String>> getComments() {
        return this.comments;
    }

    public void setAuthor_id(String fname, String lname) {
        this.author_id = lname + ", " + fname;
    }
    public void setAuthor_id(String fullname) {
        this.author_id = fullname;
    }
    

    /**
     * Comment hashmap inside a comment list
     * [
     * {
     * "text": "This is a comment",
     * "author_id": "123",
     * "datetime": "2021-08-01T12:00:00Z",
     * "id": "123"
     * },
     * {..}
     * ...
     * ]
     * 
     * @param text
     * @param author_id
     * @param dateTime
     */
    public void writeComment(String id, String text, String author_id, String dateTime) {
        HashMap<String, String> comment = new HashMap<>();
        comment.put("text", text);
        comment.put("author_id", author_id);
        comment.put("datetime", dateTime);
        comment.put("id", id);

        this.comments.add(comment);
    }

}
