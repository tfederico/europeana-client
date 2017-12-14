package eu.europeana.api.client.search;

import java.io.IOException;

import org.junit.Test;

import eu.europeana.api.client.EuropeanaApi2Client;
import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.connection.HttpConnector;
import eu.europeana.api.client.exception.EuropeanaApiProblem;
import eu.europeana.api.client.model.EuropeanaApi2Results;
import eu.europeana.api.client.model.search.EuropeanaApi2Item;
import eu.europeana.api.client.model.search.EuropeanaObject;
import eu.europeana.api.client.search.query.Api2Query;

public class ObjectRetrievalTest {

	/*@Test
	public void test() throws IOException, EuropeanaApiProblem {
		EuropeanaApi2Client ec = new EuropeanaApi2Client();
		Api2Query query = new Api2Query();
		String collection = "";
		String record = "";
		
		query.setCollectionName("08515*");
        EuropeanaApi2Results results = ec.searchApi2(query, 10, 0);
        
        for(EuropeanaApi2Item result : results.getAllItems()) {
        	EuropeanaObject eo = ec.getObject(result.getId());
        	System.out.println(eo.toString());
        }
	}	
	
	@Test
	public void testCompleteObject() throws IOException, EuropeanaApiProblem {
		EuropeanaApi2Client ec = new EuropeanaApi2Client();
		Api2Query query = new Api2Query();
		query.setGeneralTerms("europeana_completeness:10");
		
		EuropeanaApi2Results results = ec.searchApi2(query, 3, 0);
        
        for(EuropeanaApi2Item result : results.getAllItems()) {
        	EuropeanaObject eo = ec.getObject(result.getId());
        	System.out.println(eo.toString());
        }
	}*/
}
