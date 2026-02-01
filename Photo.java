import java.util.ArrayList;

public class Photo {   

    private ArrayList<String> tags;
    private String orientation;
    // orientation -> String chosen for readability

    // Constructor
    public Photo(ArrayList<String> tags, String orientation) {
        this.tags = new ArrayList<>(tags);
        this.orientation = orientation;
    }

    // Getters
    public String getOrientation() { return orientation; };
    public ArrayList<String> getTags() { return tags; };

    // Setters
    public void setOrientation(String newOrientation) {
        if (newOrientation.toLowerCase().trim() != "h" && newOrientation.toLowerCase().trim() != "w") {
            System.out.println("Could not update orientation value - Use values h or w");
            return;
        };
        this.orientation = newOrientation;
    };
    public void setTags(ArrayList<String> newTags) {
        this.tags = newTags;
    }

};