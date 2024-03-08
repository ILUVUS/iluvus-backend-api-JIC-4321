package iluvus.backend.api.dto;

import java.util.UUID;
import iluvus.backend.api.model.User;

public class CommentDto {
    private String id;
    private String authorId;
    private String text;
    private String dateTime;

    public CommentDto() {
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public boolean setAuthorId(User author) {
        if (author == null) {
            return false;
        }
        this.authorId = author.getId();
        return true;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public boolean setText(String text) {
        if (text.strip().length() == 0) {
            return false;
        }
        this.text = text;
        return true;
    }

    public String getText() {
        return this.text;
    }

    public boolean setDateTime(String dateTime) {
        if (dateTime.strip().length() == 0) {
            return false;
        }
        this.dateTime = dateTime;
        return true;
    }

    public String getDateTime() {
        return this.dateTime;
    }
}
