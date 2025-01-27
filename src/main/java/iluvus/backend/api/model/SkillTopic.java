package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Document(collection = "skills")  // Mirror of "interests" collection
public class SkillTopic {

    @Id
    private int id;
    private String name;

    // A static list of skill names - each can be stored in Mongo with an ID, 
    // just like InterestTopic does with topicList.
    public static final List<String> skillList = Arrays.asList(
            "Programming Languages (e.g., Java, Python)",
            "Data Analysis",
            "Web Development (e.g., HTML, JavaScript)",
            "Database Management",
            "Cloud Computing",
            "Artificial Intelligence",
            "Machine Learning",
            "Cybersecurity",
            "Networking and IT",
            "Software Development Lifecycle",
            "Project Management",
            "Product Management",
            "Risk Management",
            "Budgeting & Financial Planning",
            "Stakeholder Management",
            "User Research",
            "Team Leadership",
            "Public Speaking",
            "Negotiation",  // (Corrected spelling)
            "Presentation",
            "Report Writing",
            "Cross Cultural Communication",
            "Market Research",
            "Financial Analysis",
            "Statistical Analysis",
            "Systems Thinking",
            "Data Visualization", // (Corrected spelling)
            "Graphic Design",
            "Video Editing",
            "Content Creation",
            "Copywriting",
            "Storyboarding",
            "UI/UX Design",
            "Search Engine Optimization (SEO)",
            "Social Media Marketing",
            "Advertising Campaign Movement", // Kept as-is, though “Management” is common
            "Pricing Strategy",
            "Market Trend Analysis",
            "Sales Forecasting",
            "Healthcare Administration",
            "Legal Research",
            "Manufacturing Processes",
            "Supply Chain Management",
            "Educational Curriculum Design",
            "Real Estate Management",
            "Financial Auditing", // (Corrected spelling)
            "Creative Writing",
            "Sports Coaching",
            "Painting",
            "Woodworking",
            "Gardening",
            "Cooking/Baking",
            "Playing a musical instrument (e.g., piano, violin)",
            "First Aid",
            "Language Learning",
            "Parenting",
            "Personal Fitness & Wellness"
    );

    public SkillTopic() {
    }

    public SkillTopic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters/Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Mirror getInterestTopic() in InterestTopic.java
    // Returns a small map: { id -> name }
    public HashMap<Integer, String> getSkillTopic() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(this.id, this.name);
        return map;
    }
}
