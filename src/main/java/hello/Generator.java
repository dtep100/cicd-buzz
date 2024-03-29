package hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generator {

    private static ArrayList<String> buzzList = new ArrayList<>(Arrays.asList(  "continuous testing",
                                                                                "continuous integration",
                                                                                "continuous deployment",
                                                                                "continuous improvement",
                                                                                "devops"));

    private static ArrayList<String> adjectiveList = new ArrayList<>(Arrays.asList(  "complete",
                                                                                     "modern",
                                                                                     "self-service",
                                                                                     "integrated",
                                                                                     "end-to-end"));

    private static ArrayList<String> adverbList = new ArrayList<>(Arrays.asList(  "remarkably",
                                                                                  "enormously",
                                                                                  "substantially",
                                                                                  "significantly",
                                                                                  "seriously"));

    private static ArrayList<String> verbList = new ArrayList<>(Arrays.asList(  "accelerates",
                                                                                "improves",
                                                                                "enhances",
                                                                                "revamps",
                                                                                "boosts"));

    private static ArrayList<String> cloneList(ArrayList<String> sourceList)
    {
        ArrayList<String> cloneList = new ArrayList<>();
        for(String inputItem: sourceList){
            cloneList.add(inputItem);
        }
        return(cloneList);
    }

    private static String makeTitleCase(String phrase)
    {
        String[] words = phrase.split(" ");
        String response = "";

        /* Capitalise the first letter of each word */
        for(String word: words){
            String titleCaseWord = word.substring(0,1).toUpperCase() + word.substring(1);
            response += titleCaseWord + " ";
        }
        /* Trim the last space */
        response = response.trim();

        return(response);
    }

    public static ArrayList<String> sample(ArrayList<String> stringList, int number) {
        ArrayList<String> responseList = new ArrayList<>();
        /* Clone the input list into a working copy that we can mess with */
        ArrayList<String> workingList = cloneList(stringList);

        Random random = new Random();

        /* Limit if number is bigger than the supplied list */
        number = Math.min(number, stringList.size() - 1);

        /* Create the required number of random items */
        for(int sampleIndex = 0; sampleIndex < number; sampleIndex++)
        {
            int randomIndex = random.nextInt(workingList.size());
            responseList.add(workingList.get(randomIndex));
            workingList.remove(randomIndex);
        }

        return (responseList);
    }

    public static String generateBuzz(){
        ArrayList<String> buzzTerms = sample(buzzList,2);
        String phrase = sample(adjectiveList,1).get(0) + " " +
                        buzzTerms.get(0) + " " +
                        sample(adverbList,1).get(0) + " " +
                        sample(verbList,1).get(0) + " " +
                        buzzTerms.get(1);

        return(makeTitleCase(phrase));
    }

}
