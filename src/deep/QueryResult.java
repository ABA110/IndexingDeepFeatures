package deep;


public class QueryResult {
	  private int distance; 
	  private String id; 

	  public QueryResult(int distance, String id){
	      this.id = id;
	      this.distance = distance;
	  }

	  public int getDistance(){
		  return this.distance;
	  }
	  
	  public String getID(){
		  return this.id;
	  }
	    @Override
	  public boolean equals(Object v) {
	        boolean retVal = false;

	        if (v instanceof QueryResult){
	        	QueryResult ptr = (QueryResult) v;
	            retVal = ptr.getID().equals(this.id);
	        }

	     return retVal;
	  }

	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
	        return hash;
	    }
	}