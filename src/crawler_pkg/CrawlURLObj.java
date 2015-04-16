/*  =============================================================================
  Class to hold URL with hop number for Frontier Queue
  
  @version  0.1
  @author   Joshua Potter
  @studentID  860159747
  @classID  CS172
  @title    Web Crawler Project
  ========================================================================== */

package crawler_pkg;

public class CrawlURLObj {

  String url = "";
  int hop = 0;;
  
  public CrawlURLObj(String URL, int HOP) {
    url = URL;
    hop = HOP;
  }
  
  public void print() {
    System.out.println( url + ", " + hop);
  }
  
  public String getURL() {
    return url;
  }
  
  public int getHop(){
    return hop;
  }
  
}