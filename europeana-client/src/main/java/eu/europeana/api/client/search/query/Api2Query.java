package eu.europeana.api.client.search.query;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.connection.BaseApiConnection;
import eu.europeana.api.client.search.common.EuropeanaFields;

/**
 * 
 * @author Sergiu Gordea
 * 
 */
public class Api2Query extends EuropeanaQuery implements Api2QueryInterface {

	private List<String> queryRefinements;
	private String collectionName;
	
	/**
	 * This attribute is used to set directly the aggregated query parameters, possibly by reusing a portal query
	 *  This must start with query= string as it has the same semantic like the query parameter in the portal search url. 
	 */
	private String queryParams;
    
	/**
	 * Constructor that supports searching objects within a collection
	 * @param collectionName
	 */
	public Api2Query(String collectionName) {
		this.setCollectionName(collectionName);
	}

	public Api2Query() {
		super();
	}
	
	@Override
	public List<String> getQueryRefinements() {
		return queryRefinements;
	}

	@Override
	public void addQueryRefinement(String qf) {
		if(queryRefinements == null)
			queryRefinements = new ArrayList<String>(3);
		
		queryRefinements.add(qf);
	}

	@Override
	public String getQueryUrl(BaseApiConnection connection, long offset) throws UnsupportedEncodingException {
		StringBuilder url = buildBaseSearchUrl(connection);
		appendSearchQueryParams(url);

		if (offset > 0)
			url.append("&start=").append(offset);

		return url.toString();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see eu.europeana.api.client.EuropeanaQuery#getQueryUrl(eu.europeana.api.client.EuropeanaConnection, long, long)
	 */
	public String getQueryUrl(BaseApiConnection connection, long limit,
							  long offset) throws UnsupportedEncodingException {

		StringBuilder url = buildBaseSearchUrl(connection);
		appendSearchQueryParams(url);

		if (limit > 0)
			url.append("&rows=").append(limit);
		if (offset > 0)
			url.append("&start=").append(offset);

		return url.toString();
	}

	private void appendSearchQueryParams(StringBuilder url) throws UnsupportedEncodingException {
		if(getQueryParams() != null){
			url.append(queryParams);
		}else{
			String searchTerms = getSearchTerms();
			url.append("query=").append(searchTerms);
			appendQueryRefinements(url);			
		}
	}

	private StringBuilder buildBaseSearchUrl(BaseApiConnection connection) {
		StringBuilder url = new StringBuilder();
		url.append(connection.getServiceUri());
        url.append(ClientConfiguration.getInstance().getSearchUrn());
        url.append("?wskey=").append(connection.getApiKey());
		
        if(getProfile()!= null)
			url.append("&profile=").append(getProfile());
		
        url.append("&");
		return url;
	}
	
	
	@Override
	public String getQueryUrl(BaseApiConnection connection, String cursor, int rows) throws UnsupportedEncodingException {
		 
		 StringBuilder url = buildBaseSearchUrl(connection);
		 appendSearchQueryParams(url);
		
		 url.append("&cursor=").append(cursor);
		 if(rows >= 0)
			 url.append("&rows=").append(rows);
		 
		 //url.append("&sort=id asc");
				
		return url.toString();
	}
	 
	void appendQueryRefinements(StringBuilder url) throws UnsupportedEncodingException {
		
		if(getQueryRefinements() == null)
			return;
		
		for (String qf : getQueryRefinements()) {
			appendQueryRefinement(url, qf);
		}
		
	}
	
	private void appendQueryRefinement(StringBuilder buf, String qf) throws UnsupportedEncodingException {
    	if (qf == null) {
            return;
        }

    	qf = qf.trim();
        if (qf.length() == 0) {
            return;
        }
        
        buf.append("&qf=");
        
//        if(facetField != null && !facetField.trim().isEmpty())
//        	buf.append(facetField).append(IS);
        
        buf.append(encodeSearchValue(qf));
	}

	@Override
	protected void buildSearchQueryString(StringBuffer buf) {
		super.buildSearchQueryString(buf);
		
		if(getCollectionName() !=null)
			addSearchField(buf, EuropeanaFields.EUROPEANA_COLLECTION_NAME, collectionName);
	}
	
	@Override
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	
}
