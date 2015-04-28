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
    // constructor with parameters passed in
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
