import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    private int tagCount = 0; 
    private HashMap<String, Integer> hashedTags = new HashMap<>();

    public void hashWords(ArrayList<String> tags) {
        /*
        Modifies hashedTags HashMap object of tags with their unique hash as the value and a tag from the input array as the key
        Hash is simple an increment assigning each new tag a number 1 greater than the last
        chosen due to no need for security and guarantees 0 collisions 
        */

        for(String tag : tags) {

            if (hashedTags.containsKey(tag)) {
                continue;
            } 
            else {
                // If not in the hashedTags hashmap
                hashedTags.put(tag, tagCount);
                tagCount += 1;
            }
        };
        
    };
    
    public static void SortTags() {

    };
}
