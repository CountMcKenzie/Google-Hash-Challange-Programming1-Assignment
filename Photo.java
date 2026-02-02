import java.util.ArrayList;

public class Photo {   

    private ArrayList<String> tags;
    private double weight;
    private String orientation;
    // orientation -> String chosen for readability

    // Constructor
    public Photo(ArrayList<String> tags, String orientation) {
        this.tags = new ArrayList<>(tags);
        this.orientation = orientation;
        this.weight = 0;

        // Ensures tag count is updated
        Util.countTags(tags);
    }

    // Getters
    public String getOrientation() { return orientation; };
    public ArrayList<String> getTags() { return tags; };
    public double getWeight() { return weight; };

    // Setters
    public void setOrientation(String newOrientation) {
        newOrientation = newOrientation.toLowerCase().trim();
        if (!newOrientation.equals("h") && !newOrientation.equals("w")) {
            System.out.println("Could not update orientation value - Use values h or w");
            return;
        };
        this.orientation = newOrientation;
    };
    public void setTags(ArrayList<String> newTags) {
        if (newTags != null) {
            this.tags = new ArrayList<>(newTags); 
        }
    }

    // Methods
    public void weighPhotos() {
        for (String tag : this.tags) {
            this.weight += Util.getTagWeights().get(tag);
        }
    }

};