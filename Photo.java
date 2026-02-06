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
    public String getOrientation() { return this.orientation; };
    public ArrayList<String> getTags() { return this.tags; };
    public double getImportance() { return this.importance; };

    // Setters
    public void setOrientation(String newOrientation) {
        newOrientation = newOrientation.toLowerCase().trim();
        if (!newOrientation.equals("H") && !newOrientation.equals("V") && !newOrientation.equals("VV")) {
            System.out.println("Could not update orientation value - Use values H, V or VV");
            return;
        };
        this.orientation = newOrientation;
    };
    
    // Methods
    public void concatenateTags(ArrayList<String> newTags) {
        if (newTags != null) {
            for (String tag : newTags) {
                if (!this.tags.contains(tag)) {
                    this.tags.add(tag);
                };
            }
        }
    }

    public void sumPhotoImportance() {
        for (String tag : this.tags) {
            this.importance += Util.getTagWeights().get(tag);
        }
        this.importance /= this.getTags().size();
    }

    public int countSharedTags(Photo photo2) {
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

    public static ArrayList<Photo> mergeVPhotos(ArrayList<Photo> photos) {
        // Pairs Vertical photos of high importance with photos of low importance
        int lIndex = 0;
        int rIndex = photos.size()-1;
        Util.sortPhotosByImportance(photos);

        for (int i=0; i < photos.size(); i++) {

            if (lIndex >= rIndex) {
                break;
            }

            if (photos.get(lIndex).getOrientation().equals(photos.get(rIndex).getOrientation()) 
                && photos.get(lIndex).orientation.equals("V")) {
                
                Photo newVVPhoto = new Photo(photos.get(i).getTags(), "VV");
                newVVPhoto.concatenateTags(photos.get(rIndex).getTags());

                photos.set(lIndex, newVVPhoto);

                photos.remove(rIndex);
                lIndex+= 1;
                rIndex-= 1;

                continue;
            }
            if (photos.get(lIndex).orientation.equals("V")) {
                rIndex--;
                continue;
            }
            if (photos.get(rIndex).orientation.equals("V")) {
                lIndex++;
                continue;
            }
        }
        for(int i = 0; i < photos.size() - 1; i++) {
            if (photos.get(i).orientation.equals("V")) {
                photos.remove(i);
                break;
            }
        }

        return photos;

    }
};