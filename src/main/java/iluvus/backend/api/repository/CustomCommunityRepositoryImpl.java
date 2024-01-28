package iluvus.backend.api.repository;

import iluvus.backend.api.model.Community;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Component
public class CustomCommunityRepositoryImpl implements CustomCommunityRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateCommunityMembers(String userId, String communityId) {
        Query query = Query.query(Criteria.where("_id").is(communityId));
        Update update = new Update();
        update.addToSet("members", userId);
        mongoTemplate.updateFirst(query, update, Community.class);
    }
}
