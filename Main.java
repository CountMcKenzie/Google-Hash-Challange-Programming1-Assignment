import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int finalScore = 0;

    public static void main(String args[]) {

        // Loading photos

        loadPhotos(
            "a_example.txt", 
            "b_lovely_landscapes.txt", 
            "c_memorable_moments.txt", 
            "d_pet_pictures.txt", 
            "e_shiny_selfies.txt"
        );

        System.out.println("Final Score: "+ finalScore);
        
    }

    public static int countScore(ArrayList<Photo> photos) {
        // counts score per hash code challenge formula

        photos = initializePhotoData(photos);

        int score = 0;
        for (int i = 0; i < photos.size() - 1; i++) {
            Photo current = photos.get(i);
            Photo next = photos.get(i + 1);

            int shared = current.countSharedTags(next);

            int photo1Points = current.getTags().size() - shared;
            int photo2Points = next.getTags().size() - shared;
            
            score += Math.min(shared, Math.min(photo1Points, photo2Points));
        }

        // reset tag hashmap
        Util.countTags(new ArrayList<>(), true);

        System.out.println("score: " + score);
        return score;
    }

    public static ArrayList<Photo> initializePhotoData(ArrayList<Photo> photos) {
        // wrapper for external methods

        Util.setTagWeights();

        for (Photo photo : photos) {
            // assigns each photo its importance value
            photo.sumPhotoImportance();
        }
        
        photos = Photo.mergeVPhotos(photos);
        photos = Util.pairRelevantPhotos(photos,1000);

        return photos;
    }

    public static void loadPhotos(String ...filenames) {

        for (String filename: filenames) {
            System.out.println("Calculating " + filename);
            ArrayList<Photo> photos = null; 
            
            try (Scanner scanner = new Scanner(new File(filename))) {

                // Generating array of correct length
                if (scanner.hasNextInt()) {
                    int totalPhotos = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    photos = new ArrayList<>(totalPhotos); 
                } else {
                    // Return if empty list if file is empty
                    return; 
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.trim().isEmpty()) { continue; };

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

            photos = (photos != null) ? photos : new ArrayList<>();
            finalScore += countScore(photos);
        }
    }
}