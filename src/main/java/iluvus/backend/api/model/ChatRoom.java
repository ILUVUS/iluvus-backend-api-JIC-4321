package iluvus.backend.api.model;

//need to add dto here
import iluvus.backend.api.dto.ChatRoom.dto
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.List;


//import java.time.LocalDateTime;

import java.util.List;

@Document(collection = "chats")
public class ChatRoom {
    @Id
    private String id;
    private String type;
    private List<String> chatUsers;
    private String lastMessage; //not sure if needed
    private String 

    @Field("datetime")
    private String dateTime;
}

