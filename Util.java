import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    private HashMap<String, Integer> tagFrequencies = new HashMap<>();

    public void countTags(ArrayList<String> tags) {
        // simply counts frequency of each tag and adds it to a hashmap where key is tag and value is frequency
        for(String tag : tags) {
            tagFrequencies.put(tag, tagFrequencies.getOrDefault(tag, 0) + 1);
        };
    };
    
    public static ArrayList<Double> normalDistribute(ArrayList<Integer> frequencyValues) {

        ArrayList<Double> normalValues = new ArrayList<>();

        // Calculating Mean
        double sum = 0;
        for (Integer frequency : frequencyValues) {
            sum += frequency;
        }
        double mean = sum / frequencyValues.size();

        // Calculating Standard Deviation
        sum = 0;
        for (Integer frequency : frequencyValues) {
            sum += Math.pow(frequency-mean, 2);
        }
        double standardDeviation = Math.sqrt( sum / frequencyValues.size());

        // Division by 0 Check (if all values are 0)
        if (standardDeviation == 0) {
            for (int i = 0; i < frequencyValues.size(); i++) {
                normalValues.add(0.0);
            }
            return normalValues;
        }       

        //Calculating standard values
        for (int frequency: frequencyValues) {
            normalValues.add((1/(standardDeviation*Math.sqrt(2*Math.PI) ) )*Math.pow(Math.E, -0.5*Math.pow((frequency-mean)/standardDeviation, 2)) );
        }
        return normalValues;

    };
}
