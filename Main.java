import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        // Loading photos
        ArrayList<Photo> photos = loadPhotos("a_example.txt");
        initializePhotoData(photos);

        
    }

    public static void initializePhotoData(ArrayList<Photo> photos) {
        Util.setTagWeights();
        for (Photo photo : photos) {
            photo.weighPhotos();
        }
    }

    public static ArrayList<Photo> loadPhotos(String filename) {
        ArrayList<Photo> photos = null; // Initialize as null
        
        try (Scanner scanner = new Scanner(new File(filename))) {
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
                
                // Grab tags starting from index 2
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