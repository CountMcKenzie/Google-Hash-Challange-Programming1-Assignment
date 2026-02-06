import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    private static HashMap<String, Double> tagFrequencies = new HashMap<>();
    private static HashMap<String, Double> tagWeights = new HashMap<>();

    // Getters 
    public static HashMap<String, Double> getTagWeights() {
        return tagWeights;
    }
    public static HashMap<String, Double> getTagFrequencies() {
        return tagFrequencies;
    }

    // Setters
    public static void setTagWeights() {
        weighValues();
    }

    // Methods
    public static void countTags(ArrayList<String> tags) {
        // simply counts frequency of each tag and adds it to a hashmap where key is tag and value is frequency
        for(String tag : tags) {
            tagFrequencies.put(tag, tagFrequencies.getOrDefault(tag, 0.0) + 1);
        };
    };
    
    private static void weighValues() {
        // must be invoked from the setter method for tagWeights

        for (String tag : tagFrequencies.keySet()) {
            tagWeights.put(tag, Math.log(tagFrequencies.get(tag)/(double) tagFrequencies.size())*-1);
        }

    };

    public static ArrayList<Photo> optimizePhotoArray(ArrayList<Photo> photos) {
        // greedy look ahead algorithm

        sortPhotosByImportance(photos);

        for (int i = 0; i < photos.size() - 1; i++) {

            int lookDepth = 700;
            Photo current = photos.get(i);
            int bestScore = -1;
            int bestIndex = -1;

            for (int j = i + 1; j < Math.min(i+lookDepth, photos.size()); j++) {
                Photo nextCandidate = photos.get(j);
                int shared = current.countSharedTags(nextCandidate);
                
                int score = Math.min(shared, 
                            Math.min(current.getTags().size() - shared, nextCandidate.getTags().size() - shared));

                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = j;
                }
                
                if (bestScore >= current.getTags().size() / 2) { break; }
            }

            // Swap the best candidate into the next position
            if (bestIndex != -1) {
                Photo temp = photos.get(i + 1);
                photos.set(i + 1, photos.get(bestIndex));
                photos.set(bestIndex, temp);
            }
        }
        return photos;
    }
    public static ArrayList<Photo> sortPhotosByImportance(ArrayList<Photo> photos) {
        // Easy to implement O(n log(n)) sort
        photos.sort((photo1, photo2) ->
            Double.compare(photo1.getImportance(), photo2.getImportance())
        );
        return photos;
    }

}
