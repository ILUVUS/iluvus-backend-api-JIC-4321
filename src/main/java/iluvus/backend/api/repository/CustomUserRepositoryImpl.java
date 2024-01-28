package iluvus.backend.api.repository;

import iluvus.backend.api.model.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateUserGroups(String userId, String communityId) {
        Query query = Query.query(Criteria.where("_id").is(userId));
        Update update = new Update();
        update.addToSet("groups", communityId);
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
