package eu.europeana.api.client.search;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import eu.europeana.api.client.search.query.adv.EuropeanaSearchTerm;
import org.junit.Test;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.search.query.Api2Query;
import eu.europeana.api.client.search.query.EuropeanaComplexQuery;

/**
 * Test Class generated from the SimpleSearch example
 * @author GordeaS
 *
 */
public class SimpleSearchTest extends BaseSearchUtils{

	@Test
	public void testSimpleSearch() throws IOException, EuropeanaApiProblem{

	    ArrayList<String> noResults = new ArrayList<>();
        ArrayList<String> museum = new ArrayList<>();
        ArrayList<String> painter = new ArrayList<>();
        ArrayList<String> painting = new ArrayList<>();
        String csvFile = "/home/federico/Downloads/database.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use semi-colon as separator
                String[] data = line.split(cvsSplitBy);
                museum.add(data[0]);
                painter.add(data[1]);
                painting.add(data[2]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        museum.remove(0);
        painter.remove(0);
        painting.remove(0);


		long ms0 = System.currentTimeMillis();

		boolean tryingRijks = false;

        for(int i = 0; i < painting.size(); i++){
            //create the query object
            Api2Query europeanaQuery = new Api2Query();

            europeanaQuery.setTitle(painting.get(i));

            //europeanaQuery.setCreator("Johannes");
            //europeanaQuery.setCountry("Netherlands");
            if(Objects.equals(museum.get(i), "Mauritshuis"))
                europeanaQuery.setCollectionName("2021672_Ag_NL_DigitaleCollectie_Mauritshuis");
            else
                europeanaQuery.setCollectionName("90402_M_NL_Rijksmuseum");
            //europeanaQuery.setNotProvider("Netherlands");

            //perform search
            EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();

            EuropeanaApi2Results res = europeanaClient.searchApi2(europeanaQuery, -1, 0);

            //print out response time
            long t = System.currentTimeMillis() - ms0;
            //System.out.println("response time (client+server): " + (t / 1000d) + " seconds");

            //check item count
            final int DEFAULT_PAGE_SIZE = 12;
            //assertTrue(res.getItemCount() ==  DEFAULT_PAGE_SIZE);

            //check total items. Should be more that one page for sure
            //assertTrue(res.getTotalResults() >  DEFAULT_PAGE_SIZE);


            //print out search results
            //System.out.println("Query: " + europeanaQuery.getSearchTerms());
            //System.out.println("Query url: " + europeanaQuery.getQueryUrl(europeanaClient));

            int count = 0;
            for (EuropeanaApi2Item item : res.getAllItems()) {
                    System.out.println();
                    System.out.println("**** " + (count + 1));
                    System.out.println("Title: " + item.getTitle());
                    System.out.println("Europeana URL: " + item.getObjectURL());
                    System.out.println("Type: " + item.getType());
                    System.out.println("Creator(s): " + item.getDcCreator());
                    System.out.println("Thumbnail(s): " + item.getEdmPreview());
                    System.out.println("Data provider: " + item.getDataProvider());
                System.out.println("Description: " + item.getDcDescription());
                count++;

            }

            if (count == 0)
                noResults.add("No results for: "+painting.get(i));
        }

        for(String noRes : noResults)
            System.out.println(noRes);

        System.out.println(noResults.size());
    }

	/*@Test
	public void testSearchInTitle() throws IOException, EuropeanaApiProblem{
	
		long ms0 = System.currentTimeMillis();

        //create the query object
		Api2Query europeanaQuery = new Api2Query();
        europeanaQuery.setTitle("girl with pearl earring");
        europeanaQuery.setCreator("vermeer");
        
        //perform search
        EuropeanaApi2Client europeanaClient = new EuropeanaApi2Client();
        final int RESULTS_SIZE = 2;
		EuropeanaApi2Results res = europeanaClient.searchApi2(europeanaQuery, RESULTS_SIZE, 0);

      //print out response time
        long t = System.currentTimeMillis() - ms0;
        System.out.println("response time (client+server): " + (t / 1000d) + " seconds");

        assertTrue(res.getItemCount() ==  RESULTS_SIZE);
        
        int count = 0;
        for (EuropeanaApi2Item item : res.getAllItems()) {
        	 System.out.println();
             System.out.println("**** " + (count++ + 1));
             System.out.println("Title: " + item.getTitle());
             System.out.println("Europeana URL: " + item.getObjectURL());
             System.out.println("Type: " + item.getType());
             System.out.println("Creator(s): " + item.getDcCreator());
             System.out.println("Thumbnail(s): " + item.getEdmPreview());
             System.out.println("Data provider: "
                     + item.getDataProvider());
		}
	}*/
}
