package iluvus.backend.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Document(collection = "skills")
public class Skill {

    @Id
    private int id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, String> getSkillType() {
        HashMap<Integer, String> skillTypeMap = new HashMap<>();
        skillTypeMap.put(this.getId(), this.getName());
        return skillTypeMap;
    }


    //A list of topics
    public static final List<String> skillList = Arrays.asList(
            "Programming Languages (e.g., Java, Python", 
            "Data Analysis",
            "Web Development (e.g., HTML, JavaScript",
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
            "Negotation",
            "Presentation",
            "Report Writing",
            "Cross Cultural Communication",
            "Market Research", 
            "Financial Analysis",
            "Statistical Analysis", 
            "Systems Thinking",
            "Data Vizualisation",
            "Graphic Design",
            "Video Editing",
            "Content Creation",
            "Copywriting",
            "Storyboarding",
            "UI/UX Design",
            "Search Engine Optimization (SEO)",
            "Social Media Marketing",
            "Advertising Campaign Movement",
            "Pricing Strategy",
            "Market Trend Analysis",
            "Sales Forecasting",
            "Healthcare Administration",
            "Legal Research",
            "Manufacturing Processes",
            "Supply Chain Management",
            "Educational Curriculum Design",
            "Real Estate Management",
            "Financial Audisting",
            "Creative Writing",
            "Sports Coaching",
            "Painting",
            "Woodworking",
            "Gardening",
            "Cooking/Baking", 
            "Playing a musical instrument (e.g., piano, violin",
            "First Aid",
            "Language Learning",
            "Parenting",
            "Personal Fitness & Wellness");
}
