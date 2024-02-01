package iluvus.backend.api.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostDto {

    private String text;
    private String dateTime;
    private BigInteger uplift;
    private String author_id;
    private String community_id;

    public PostDto() {
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public BigInteger getUplift() {
        return this.uplift;
    }

    public void setUplift(BigInteger uplift) {
        this.uplift = uplift;
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

}
