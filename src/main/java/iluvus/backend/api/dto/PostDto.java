package iluvus.backend.api.dto;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class PostDto {

    private String text;
    private String dateTime;
    private String author_id;
    private String community_id;
    private List<HashMap<String, String>> comments;
    private List<String> likedBy;
    private List<String> reportedBy;

    public List<String> getReportedBy() {
        return reportedBy;
    }

    public void setLikedBy(List<String> likedBy) {this.likedBy = likedBy; }

    public List<String> getLikedBy() { return likedBy;}

    public PostDto() {
    }

    public void setReportedBy(List<String> reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor_id() {
        return this.author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getCommunity_id() {
        return this.community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public List<HashMap<String, String>> getComments() {
        return this.comments;
    }

    public void setComments(List<HashMap<String, String>> comments) {
        this.comments = comments;
    }
}