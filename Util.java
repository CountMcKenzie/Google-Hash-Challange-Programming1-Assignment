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


    // Methods
    public static void countTags(ArrayList<String> tags, boolean flush) {
        // simply counts frequency of each tag and adds it to a hashmap where key is tag and value is frequency
        if (flush) { 
            tagFrequencies.clear(); 
            tagWeights.clear();
            return;
        };
        for (String tag : tags) {
            tagFrequencies.put(tag, tagFrequencies.getOrDefault(tag, 0.0) + 1);
        };
    };
    
    public static void setTagWeights() {

        int totalTags = tagFrequencies.size();
        for (String tag : tagFrequencies.keySet()) {
            tagWeights.put(tag, Math.log(tagFrequencies.get(tag) / (double) totalTags) *-1);
        }
    };

    public static ArrayList<Photo> pairRelevantPhotos(ArrayList<Photo> photos, int lookDepth) {
        // greedy look ahead algorithm
        sortPhotosByImportance(photos);

        for (int i = 0; i < photos.size() - 1; i++) {
            Photo current = photos.get(i);
            int currentTagSize = current.getTags().size();
            int earlyExitThreshold = currentTagSize / 2;
            int searchLimit = Math.min(i + lookDepth, photos.size());
            
            int bestScore = -1;
            int bestIndex = -1;

            for (int j = i + 1; j < searchLimit; j++) {
                Photo nextCandidate = photos.get(j);
                int nextTagSize = nextCandidate.getTags().size();
                int shared = current.countSharedTags(nextCandidate);
                
                int score = Math.min(shared, 
                            Math.min(currentTagSize - shared, 
                            nextTagSize - shared));

                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = j;
                }
                
                if (bestScore >= earlyExitThreshold) { break; }
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
