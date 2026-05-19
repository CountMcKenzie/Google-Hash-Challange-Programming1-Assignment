import java.util.ArrayList;
import java.util.HashSet;

public class Photo {   

    private HashSet<String> tags;
    private double importance;
    private String orientation;
    // orientation -> String chosen for readability

    // Constructor
    public Photo(ArrayList<String> tags, String orientation, boolean isNewFromFile) {
        this.tags = new HashSet<>(tags);
        this.orientation = orientation;
        this.importance = 0;

        // ensures tag count is updated
        if (isNewFromFile) {
            TagStats.countTags(tags, false);
        }
    }

    // Getters
    public String getOrientation() { return this.orientation; };
    public HashSet<String> getTags() { return this.tags; };
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
    public void setTags(HashSet<String> newTags) {
        if (newTags != null) { this.tags = newTags; }
    }

    // Methods
    public void concatenateTags(HashSet<String> newTags) {
        // merges 2 photos sets of tags - for use with merging V photos
        if (newTags != null) {
            this.tags.addAll(newTags);
        }
    }

    public void sumPhotoImportance() {
        for (String tag : this.tags) {
            this.importance += TagStats.getTagWeights().get(tag);
        }
    }

    public int countSharedTags(Photo photo2) {
        // returns the amount of tags that are shared between 2 photos
        int count = 0;

        HashSet<String> smaller = this.tags.size() < photo2.tags.size() ? this.tags : photo2.tags;
        HashSet<String> larger = (smaller == this.tags) ? photo2.tags : this.tags;

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
        
        Slideshow.sortPhotosByImportance(photos);

        ArrayList<Photo> sortedPhotos = new ArrayList<>();

        for (int i=0; i < photos.size(); i++) {

            if (lIndex >= rIndex) {
                break;
            }

            if (photos.get(lIndex).getOrientation().equals(photos.get(rIndex).getOrientation()) 
                && photos.get(lIndex).getOrientation().equals("V")) {
                
                Photo newVVPhoto = new Photo(new ArrayList<>(photos.get(lIndex).getTags()), "VV", false);
                newVVPhoto.concatenateTags(photos.get(rIndex).getTags());

                sortedPhotos.add(newVVPhoto);

                lIndex++;
                rIndex--;

                continue;
            }

            if (photos.get(lIndex).getOrientation().equals("H")) {
                sortedPhotos.add(photos.get(lIndex));
                lIndex++;
            }
            if (photos.get(rIndex).getOrientation().equals("H")) {
                sortedPhotos.add(photos.get(rIndex));
                rIndex--;
            }
        }
        return sortedPhotos;

    }
};