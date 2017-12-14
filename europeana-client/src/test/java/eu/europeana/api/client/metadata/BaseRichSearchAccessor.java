package eu.europeana.api.client.metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import eu.europeana.api.client.dataset.EuClientDatasetUtil;
import eu.europeana.api.client.model.search.CommonMetadata;
import eu.europeana.api.client.search.query.Api2QueryBuilder;
import eu.europeana.api.client.search.query.Api2QueryInterface;

/**
 * This class is computing intensive it must be run manually when needed.
 * It aggregates metadata for given collection query string, stores its metadata in JSON files and generates
 * CSV file comprising Europeana ID, creator, title, description and subject.
 * 
 * Sample query is:
 * 
 * http://www.europeana.eu/portal/search?q=europeana_collectionName:(2059216_Ag_EU_eSOUNDS_1001_ONB)&view=grid
 * 
 * @author Roman Graf
 *
 */
public class BaseRichSearchAccessor extends EuClientDatasetUtil {

//	String searchOnbDatasetFodler = "./src/test/resources/onb";

	String EUROPEANA_ID_LIST_CSV = "overview.csv"; 
	

	/**
	 * This method uploads collection metadata, stores them in JSON files in the folder defined in 
	 * configuration file and creates overview file in CSV format. 
	 * @param
	 * @param maxSize
	 * @throws Throwable
	 */
	public void uploadAndSaveDatasetContentMap(String queryStr, int maxSize) throws Throwable {
		
		try {

			Api2QueryBuilder queryBuilder = new Api2QueryBuilder();
			String portalUrl = "http://www.europeana.eu/portal/search?query="
					+ URLEncoder.encode(queryStr, "UTF-8");
			Api2QueryInterface apiQuery = queryBuilder.buildQuery(portalUrl);
			apiQuery.setProfile("rich");
			MetadataAccessor ma = new MetadataAccessor(apiQuery, null);
			ma.setStoreItemsAsJson(true);
			ma.setBlockSize(100);
			ma.setStoreBlockwiseAsJson(true);
			
			File toFile = (new MetadataAccessor()).getDatasetFile(EUROPEANA_ID_LIST_CSV);

			// create parent dirs
			toFile.getParentFile().mkdirs();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(toFile.getAbsolutePath()), "UTF-8"));            

			writer.write("Europeana ID;Title;Description;Creator;\n"); // header
			
			Map<String, String> contentMap = ma.getContentMap(CommonMetadata.FIELD_TITLE, 1, maxSize,
					MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapTitle = new HashMap<String,String>(contentMap);
			contentMap = ma.getContentMap(CommonMetadata.FIELD_DC_DESCRIPTION, 1, maxSize, MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapDcDescription = new HashMap<String,String>(contentMap);
			contentMap = ma.getContentMap(CommonMetadata.FIELD_DC_CREATOR, 1, maxSize, MetadataAccessor.ERROR_POLICY_CONTINUE);
			Map<String, String> contentMapDcCreator = new HashMap<String,String>(contentMap);

			for (Map.Entry<String, String> pair : contentMapTitle.entrySet()) {
		    	String europeanaId = pair.getKey();
		    	String title = pair.getValue();
		    	String description = contentMapDcDescription.get(europeanaId);
		    	String creator = contentMapDcCreator.get(europeanaId);
		    	
				writer.write(europeanaId);
				writer.write(";");
				writer.write(title.replace(";", "\t"));
				writer.write(";");
				writer.write(description.replace(";", "\t"));
				writer.write(";");
				writer.write(creator.replace(";", "\t"));
				writer.write(";");
				writer.write("\n");
	    	}		

			writer.flush();
			writer.close();

		} catch (Throwable th) {
			th.printStackTrace();
			throw th;
		}
	}


}
