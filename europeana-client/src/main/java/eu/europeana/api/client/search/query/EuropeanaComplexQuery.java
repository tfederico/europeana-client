package eu.europeana.api.client.search.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import eu.europeana.api.client.config.ClientConfiguration;
import eu.europeana.api.client.connection.BaseApiConnection;
import eu.europeana.api.client.search.query.adv.EuropeanaSearchTerm;

/**
 * This class is used to work with EuropeanaSearchTerm
 * 
 * @author Cosmin Coman
 */
public class EuropeanaComplexQuery implements EuropeanaQueryInterface {
    
    private EuropeanaSearchTerm searchTerms = null;
    private String type = null;
    
    public static class TYPE {

        public static final String TEXT = "TEXT";
        public static final String VIDEO = "VIDEO";
        public static final String SOUND = "SOUND";
        public static final String IMAGE = "IMAGE";
        public static final String _3D = "3D";
    }
    
    public static final long DEFAULT_OFFSET = 1;
    
    /**
     * Returns the Europeana search terms query string
     * 
     * @param sreachTerms 
     */
    public EuropeanaComplexQuery(EuropeanaSearchTerm sreachTerms) {
        this.searchTerms = sreachTerms;
    }
    
    /**
     * 
     * @param searchTerms
     * @param queryType IMAGE, TEXT, etc. Defined in TYPE
     */
    public EuropeanaComplexQuery(EuropeanaSearchTerm searchTerms, String queryType) {
        this.searchTerms = searchTerms;
        this.type = queryType;
    }
    
    public String getSearchTerms() {
        return searchTerms.toString();
    }

    public String getQueryUrl(BaseApiConnection connection) throws UnsupportedEncodingException {
        return getQueryUrl(connection, EuropeanaComplexQuery.DEFAULT_OFFSET);
    }

    public String getQueryUrl(BaseApiConnection connection, long offset) throws UnsupportedEncodingException {
        return getQueryUrl(connection, 12, offset);
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueryUrl(BaseApiConnection connection, long limit, long offset) throws UnsupportedEncodingException {
        String seachTerms = this.searchTerms.toString();
        StringBuilder url = new StringBuilder();
        url.append(connection.getServiceUri());
        url.append(ClientConfiguration.getInstance().getSearchUrn());
        url.append("?query=").append(URLEncoder.encode(seachTerms, "UTF-8"));
        url.append("&rows=").append(limit);
        //given the fact that normal programmers use 0 as default offset, we make a check
        if (offset<=0) {
            offset = 1;
        }
        if (this.type!=null) {
            url.append("&qf=TYPE:").append(this.type);
        }
        url.append("&start=").append(offset);
        url.append("&wskey=").append(connection.getApiKey());
        //System.err.println(url);
        return url.toString();
    }
    
    public String getQueryUrl(BaseApiConnection connection, String cursor, int rows) throws UnsupportedEncodingException {
        String seachTerms = this.searchTerms.toString();
        StringBuilder url = new StringBuilder();
        url.append(connection.getServiceUri());
        url.append(ClientConfiguration.getInstance().getSearchUrn());
        url.append("?query=").append(URLEncoder.encode(seachTerms, "UTF-8"));
	    url.append("&rows=").append(rows);
        if (this.type!=null) {
            url.append("&qf=TYPE:").append(this.type);
        }
	    if (cursor == null || cursor.isEmpty())
	    	cursor = "*";
	    url.append("&cursorMark=").append(cursor);
	    url.append("&sort=id asc");
        url.append("&wskey=").append(connection.getApiKey());
        //System.err.println(url);
        return url.toString();
    }
    

	@Override
	public void setWhatTerms(String what) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGeneralTerms(String generalTerms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCreator(String creator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProvider(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDataProvider(String dataProvider) {
		// TODO Auto-generated method stub
		
	}

	public String getWholeSubQuery() {
		return null;
	}

	public void setWholeSubQuery(String wholeSubQuery) {
	}

	@Override
	public void addSubQuery(SubQuery subQuery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SubQuery> getSubQueries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProfile(String profile) {
		// TODO Auto-generated method stub
		//TODO: implement support for profile
		
	}

}
