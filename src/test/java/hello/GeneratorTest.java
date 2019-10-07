package hello;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GeneratorTest {

    ArrayList<String> sampleList;

    @Before
    public void setUp() throws Exception {
        sampleList = new ArrayList<String>(Arrays.asList("foo", "bar", "foobar"));
    }

    @After
    public void tearDown() throws Exception {
    }

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
    public void test_sample_single_word(){

        /* Precondition */

        /* Action */
        ArrayList<String> responseList = Generator.sample(sampleList, 1);

        /* Assertion */
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        boolean foundEntry = false;
        for(String sample: sampleList){
            if(responseList.get(0).equals(sample)){
                foundEntry = true;
            }
        }
        assertEquals(true, foundEntry);

    }

    @Test
    public void test_sample_multiple_words() throws Exception {

        /* Precondition */

        /* Action */
        ArrayList<String> responseList = Generator.sample(sampleList, 2);

        /* Assertion */
        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        for(String response : responseList){
            boolean foundEntry = false;
            for(String sample: sampleList){
                if(response.equals(sample)){
                    foundEntry = true;
                }
            }
            assertEquals(true, foundEntry);
        }

        assertNotEquals(responseList.get(0), responseList.get(1));

    }

    @Test
    public void test_generate_buzz_of_at_least_five_words() throws Exception {

        /* Precondition */

        /* Action */
        String phrase = Generator.generateBuzz();

        /* Assertion */
        assertTrue(phrase.split(" ").length >= 5);
    }
}