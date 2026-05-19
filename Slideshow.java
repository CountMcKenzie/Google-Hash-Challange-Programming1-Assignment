import java.util.ArrayList;

public class Slideshow {

    public static ArrayList<Photo> pairRelevantPhotos(ArrayList<Photo> photos, int lookDepth) {
        // greedy look ahead algorithm
        sortPhotosByImportance(photos);

        for (int i = 0; i < photos.size() - 1; i++) {
            Photo current = photos.get(i);
            int currentTagSize = current.getTags().size();
            
            // tightened back to 2 to force the algorithm to look for better quality matches
            int earlyExitThreshold = currentTagSize / 2; 
            int searchLimit = Math.min(i + lookDepth, photos.size());
            
            int bestScore = -1;
            int bestIndex = -1;

            for (int j = i + 1; j < searchLimit; j++) {
                Photo nextCandidate = photos.get(j);
                int shared = current.countSharedTags(nextCandidate);
                
                // mathematical shortcut: skips calculations if candidate cannot physically beat the best score
                if (shared <= bestScore) {
                    continue;
                }
                
                int nextTagSize = nextCandidate.getTags().size();
                int score = Math.min(shared, 
                            Math.min(currentTagSize - shared, 
                            nextTagSize - shared));

                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = j;
                }
                
                if (bestScore >= earlyExitThreshold) { break; }
            }

            // swap the best candidate into the next position
            if (bestIndex != -1) {
                Photo temp = photos.get(i + 1);
                photos.set(i + 1, photos.get(bestIndex));
                photos.set(bestIndex, temp);
            }
        }
        return photos;
    }

    public static ArrayList<Photo> sortPhotosByImportance(ArrayList<Photo> photos) {
        // easy to implement o(n log(n)) sort
        photos.sort((photo1, photo2) ->
            Double.compare(photo1.getImportance(), photo2.getImportance())
        );
        return photos;
    }
}