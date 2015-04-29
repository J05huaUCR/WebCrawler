/*  =============================================================================
  Object Class to hold indexed values from the parsed HTML
  
  @version  0.1
  @author   Joshua Potter
  @SID      860159747
  @author   Ashwin Ramadevanahalli
  @SID      861186399
  @classID  CS242
  @title    Crawler & Indexer Project
  ========================================================================== */

package crawler_pkg;

public class Indexer_IndexedHTML {
  private String title = "";
  private String desc="";
  private String body = "";
  private String url = "";  
  private String keywords = "";
  private int id = 0;
  
  public Indexer_IndexedHTML() {
     // default constructor
  }
  
  public Indexer_IndexedHTML(String t, String b, String u) {
    // constructor with parameters
    this.setTitle(t);
    this.setBody(b);
    this.setURL(u);
  }
  
  public void setID(int i) {
    id = i;
    return;
  }
  
  public void setTitle(String t) {
    title = t;
    return;
  }
  
  public void setDesc(String d) {
    desc = d;
    return;
  }
  
  public void setBody(String b) {
    body = b;
    return;
  }
  
  public void appendBody(String b) {
    body = body + " " + b;
    return;
  }
  
  public void setKeywords(String k) {
    keywords = k;
    return;
  }
  
  public void appendKeywords(String k) {
    keywords = keywords + "," + k;
    return;
  }
  
  public void setURL(String u) {
    url = u;
    return;
  }
  
  public int getID() {
    return id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getDesc() {
    return desc;
  }
  
  public String getBody() {
    return body;
  }
  
  public String getKeywords() {
    return keywords;
  }
  
  public String getURL() {
    return url;
  }
}
