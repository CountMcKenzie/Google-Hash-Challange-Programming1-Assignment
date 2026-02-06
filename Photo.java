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
    public Photo setTags(ArrayList<String> newTags) {
        if (newTags != null) {
            for (String tag : newTags) {
                if (!this.tags.contains(tag)) {
                    this.tags.add(tag);
                };
            }
        }
        return this;
    }

    // Methods
    public void weighPhotos() {
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

        ArrayList<Photo> vPhotoArray = new ArrayList<>();
        
        for (Photo photo : photos) {
            if (photo.orientation == "V") {
                vPhotoArray.add(photo);
                photos.remove(photo);
            }
        }

        vPhotoArray = Util.pairOptimalPhotos(vPhotoArray, 1300);

        Photo mergedVPhoto;

        for (int i = 0; i < vPhotoArray.size(); i++) {
            if (i % 2 == 0 && i+1 != vPhotoArray.size()) {

                Photo vPhoto1 = vPhotoArray.get(i);
                Photo vPhoto2 = vPhotoArray.get(i+1);

                mergedVPhoto = vPhoto1.setTags(vPhoto2.getTags());
                mergedVPhoto.orientation = "VV";

                photos.add(mergedVPhoto);

            }
        }

        Util.sortPhotosByImportance(photos);
        
        return photos;

    }
}