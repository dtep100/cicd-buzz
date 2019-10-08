package hello;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GeneratorUT {

    private boolean findInList(ArrayList<String> searchList, String searchItem)
    {
        boolean foundEntry = false;
        for(String sample: searchList){
            if(searchItem.equals(sample)){
                foundEntry = true;
            }
        }
        return (foundEntry);
    }

    @Test
    public void testSampleSingleWord(){

        /* Precondition */
        ArrayList<String> sampleList = new ArrayList<String>(Arrays.asList("foo", "bar", "foobar"));


        /* Action */
        ArrayList<String> responseList = Generator.sample(sampleList, 1);

        /* Assertion */
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertTrue(findInList(sampleList, responseList.get(0)));
        System.out.println(responseList.get(0));

    }

    @Test
    public void testSampleMultipleWords() throws Exception {

        /* Precondition */
        ArrayList<String> sampleList = new ArrayList<String>(Arrays.asList("foo", "bar", "foobar"));


        /* Action */
        ArrayList<String> responseList = Generator.sample(sampleList, 2);

        /* Assertion */
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertTrue(findInList(sampleList, responseList.get(0)));
        assertTrue(findInList(sampleList, responseList.get(1)));
        assertNotEquals(responseList.get(0), responseList.get(1));
        System.out.println(responseList.get(0) + ", " + responseList.get(1));


    }

    @Test
    public void testGenerateBuzzOfAtLeastFiveWords() throws Exception {

        /* Precondition */

        /* Action */
        String phrase = Generator.generateBuzz();

        /* Assertion */
        assertTrue(phrase.split(" ").length >= 5);
        System.out.println(phrase);
    }
}