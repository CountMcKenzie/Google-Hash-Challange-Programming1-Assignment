import java.util.ArrayList;
import java.util.HashSet;

public class Photo {   

    private ArrayList<String> tags;
    private double importance;
    private String orientation;
    // orientation -> String chosen for readability

    // Constructor
    public Photo(ArrayList<String> tags, String orientation) {
        this.tags = new ArrayList<>(tags);
        this.orientation = orientation;
        this.importance = 0;

        // Ensures tag count is updated
        Util.countTags(tags);
    }

    // Getters
    public String getOrientation() { return orientation; };
    public ArrayList<String> getTags() { return tags; };
    public double getImportance() { return importance; };

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
            this.importance += Util.getTagWeights().get(tag);
        }
    }

    public int sharedTags(Photo photo2) {
        int count = 0;

        HashSet<String> set1 = new HashSet<>(this.tags);
        HashSet<String> set2 = new HashSet<>(photo2.tags);

        HashSet<String> smaller = set1.size() < set2.size() ? set1 : set2;
        HashSet<String> larger = (smaller == set1) ? set2 : set1;

        for (String tag : smaller) {
            if (larger.contains(tag)) {
                count += 1;
            }
        }
        return count;
    }

};