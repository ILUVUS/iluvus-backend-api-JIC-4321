package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "communities")
public class Community {
    @Id
    private String id;
    private String name;
    private String description;
    private String rule;

    private boolean isPublic;

    @DBRef
    private User owner;
    @DBRef
    private Set<User> members;

    public Community() {
    }

    public Community(String name, String description, String rule, boolean isPublic, User owner) {
        this.name = name;
        this.description = description;
        this.rule = rule;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = new HashSet<User>();
    }

}

