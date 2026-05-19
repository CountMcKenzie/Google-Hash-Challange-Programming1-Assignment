import java.util.ArrayList;
import java.util.HashMap;

public class TagStats {

    private static HashMap<String, Double> tagFrequencies = new HashMap<>();
    private static HashMap<String, Double> tagWeights = new HashMap<>();

    // getters 
    public static HashMap<String, Double> getTagWeights() {
        return tagWeights;
    }
    public static HashMap<String, Double> getTagFrequencies() {
        return tagFrequencies;
    }

    // methods
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
}