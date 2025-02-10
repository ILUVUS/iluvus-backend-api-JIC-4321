package iluvus.backend.api.dto;

import java.util.HashMap;
import java.util.List;

public class PostDto {

    private String text;
    private String dateTime;
    private String author_id;
    private String community_id;
    private List<HashMap<String, String>> comments;
    private List<String> likedBy;
    private List<String> reportedBy;
    private List<String> tagged;
    private List<String> medias;

    private Integer topicId;

    private String sourceLink;  

    public PostDto() {
    }

    public List<String> getReportedBy() {
        return reportedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public List<String> getLikedBy() {
        return likedBy;
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

    public List<String> getMedias() {
        return this.medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }

    public void setTagged(List<String> tagged) {
        this.tagged = tagged;
    }

    public List<String> getTagged() {
        return tagged;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

}