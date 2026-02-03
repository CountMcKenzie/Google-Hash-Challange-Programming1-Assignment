import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        // Loading photos
        ArrayList<Photo> photos = loadPhotos("a_example.txt");
        initializePhotoData(photos);

        //photos = sortPhotosByImportance(photos);

        // Counting Score
        int score = 0;
        for (int i = 0; i < photos.size() - 1; i++) {
            Photo current = photos.get(i);
            Photo next = photos.get(i + 1);

            int shared = current.sharedTags(next);

            int photo1Size = current.getTags().size() - shared;
            int photo2Size = next.getTags().size() - shared;
            
            score += Math.min(shared, Math.min(photo1Size, photo2Size));
        }
        System.out.println("final score: "+score);
        
    }

    public static void initializePhotoData(ArrayList<Photo> photos) {
        Util.setTagWeights();
        for (Photo photo : photos) {
            photo.weighPhotos();
        }
    }

    public static ArrayList<Photo> sortPhotosByImportance(ArrayList<Photo> photos) {
        photos.sort((p1, p2) ->
            Double.compare(p2.getImportance(), p1.getImportance())
        );
        return photos;
    }

    public static ArrayList<Photo> loadPhotos(String filename) {
        ArrayList<Photo> photos = null; 
        
        try (Scanner scanner = new Scanner(new File(filename))) {

            // Generating array of correct length
            if (scanner.hasNextInt()) {
                int totalPhotos = scanner.nextInt();
                scanner.nextLine(); 
                
                photos = new ArrayList<>(totalPhotos); 
            } else {
                return new ArrayList<>(); // Return empty list if file is empty
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" ");
                String orientation = parts[0];
                
                // Index 2 skips tag count and orientation
                ArrayList<String> tags = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    tags.add(parts[i]);
                }

                photos.add(new Photo(tags, orientation));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }

        return (photos != null) ? photos : new ArrayList<>();
    }
}