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

}
