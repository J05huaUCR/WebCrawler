/*  =============================================================================
  Class to hold URL with hop number for Frontier Queue
  
  @version  0.1
  @author   Joshua Potter
  @SID      860159747
  @author   Ashwin Ramadevanahalli
  @SID      861186399
  @classID  CS242
  @title    Crawler & Indexer Project
  ========================================================================== */

package crawler_pkg;

public class CrawlURLObj {

  String url = "";
  int hop = 0;;
  
  public CrawlURLObj(String URL, int HOP) {
    url = URL;
    hop = HOP;
  }
  
  public String getURL() {
    return url;
  }
  
  public int getHop(){
    return hop;
  }
  
  public void print() {
    System.out.println( url + ", " + hop);
  }
  
}