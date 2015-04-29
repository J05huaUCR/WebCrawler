/*  =============================================================================
  Crawler Class contains instance of a single crawler to crawl a URL document
  for links to other documents.
  
  @version  0.1
  @author   Joshua Potter
  @studentID  860159747
  @author   Ashwin
  @studentID  861------
  @classID  CS242
  @title    Crawler Project
  ========================================================================== */

package crawler_pkg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
//import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler {
  
  //Class Variables
  public static int crawlerID;
  public static URL crawlerURL;
  public static int crawlerHopCount;
  public static String crawlerDateTime;
  //public static Queue< CrawlURLObj > spiderFrontier = new LinkedList< CrawlURLObj >(); // frontier queue
 
  // Constructor
  public Crawler(int id, String url, int hop, HashMap<String, Integer> visited, Environment appEnv) throws MalformedURLException{
    crawlerID = id;
    crawlerURL = new URL(url);
    crawlerHopCount = hop;
     
    // Get Datetime
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    Date date = new Date();
    crawlerDateTime = dateFormat.format(date);
    outputVars();
  };

 /* ==========================================================
  * Outputs the variables in this Crawler
  * ==========================================================
  */
 public void outputVars() {
   System.out.println("Crawler Variables:");  
   System.out.println("---------------------------");
   System.out.println("ID: " + crawlerID); 
   System.out.println("URL: " + crawlerURL.toString()); 
   System.out.println("Hop #: " + crawlerHopCount); 
   System.out.println("DateTime: " + crawlerDateTime); 
 }
 
 /* ==========================================================
  * Downloads the URL passed in and writes to disk
  * extracts and cleans links and puts them in the frontier
  * ========================================================== 
  */
 public static void downloadHTML(Queue< CrawlURLObj > frontier, Environment appEnv, HashMap<String, Integer> visited) throws IOException {
 
   // Seed Crawler Frontier with URL passed in
   String tempCrawlerURL = crawlerURL.toString();
   try {
     Document doc = Jsoup.connect( tempCrawlerURL ).timeout(3000).get();
     
     // Place url in visited HashMap
     visited.put(tempCrawlerURL, tempCrawlerURL.hashCode() );
     
     // Get the HTML content
     String htmlContent = doc.html();
     
     // extract links and add them to the URL Queue (if not crawled yet)
     Elements urlLinks = doc.select("a[href]");  
     for (int i = 0; i < urlLinks.size(); i++) {
       
       // Instantiate URL object
       URL tempURL = new URL(urlLinks.get(i).attr("abs:href"));
       
       // Clean and validate URLs
       String url = cleanURL(tempURL);
       
       /* Check for same host
        * Check if already visited and only 
        * Add to frontier and with an increased hop if not visited
        */
       if ( url.length() > 0 && !visited.containsKey(url) ) {
         
         /*
          * Check robots.txt?
          */
         if ( appEnv.listenToRobotsTxt() ) {
           if ( RobotExclusionUtil.robotsShouldFollow(url) ) {
             System.out.println("NO BOTS ALLOWED FOR THIS URL: " + url);
           } else {
             frontier.add(new CrawlURLObj(url, crawlerHopCount + 1));
             System.out.println("URL added to Frontier: " + url);
           }
         } else {
           frontier.add(new CrawlURLObj(url, crawlerHopCount + 1));
           System.out.println("URL added to Frontier: " + url);
         }

       } else {
         System.err.println("Already visited: " + url);
       }
     }
     
     // Write to Disk
     // <<<<<<<<<<<<<<<<<<<<<<<<<< NEED TO ADD IN URL of DOC in the DOC
     /*
      * String html = "<root><child></child><child></chidl></root>";
      * Document doc = Jsoup.parse(html);
    doc.select("root").first().children().first().before("<newChild></newChild>");
    System.out.println(doc.body().html());
      */
     writeHTMLdoc(htmlContent, appEnv);
     
   } catch (IOException| NullPointerException ee) {
     //ee.printStackTrace();
     System.err.println("unable to download /write: " + tempCrawlerURL);
   }
 }
 
   /* ==========================================================
    * Normalizing with java.net.URL
    * http://docs.oracle.com/javase/7/docs/api/java/net/URL.html
    * ==========================================================
    */
   public static String cleanURL(URL url) {
     
     String urlCheck = url.toString();
     
     // Check for HTTPS
     if (url.getProtocol().equals("https")) {
       return "";
     } 
     
     /* Check for non-web pages */
     String extension = urlCheck.substring(urlCheck.lastIndexOf(".") + 1, urlCheck.length());
     if (extension.equals("pdf") ||
         extension.equals("doc") ||
         extension.equals("mp3") ||
         extension.equals("mp4") ||
         extension.equals("mpeg") ||
         extension.equals("avi") ||
         extension.equals("swf") ||
         extension.equals("css") ||
         extension.equals("java") ||
         extension.equals("jar") ||
         extension.equals("c") ||
         extension.equals("cc") ||
         extension.equals("cpp") ||
         extension.equals("exe") ||
         extension.equals("xls") ||
         extension.equals("docx") ||
         extension.equals("xlsx") ||
         extension.equals("ppt") ||
         extension.equals("pptx") ||
         extension.equals("pps")     ) {
       System.err.println("THIS URL FLAGGED: " + extension);
       return "";
     }
    
     return urlCheck;
   }
   
   /* ==========================================================
    * Saves HTML file to output folder 
    * ==========================================================
    */
   public static int writeHTMLdoc(String htmlDoc, Environment appEnv){
     BufferedWriter fileWriter = null;
     try {
       fileWriter = new BufferedWriter( 
              new FileWriter( 
                  appEnv.getOutputPath() + 
                  crawlerDateTime + "_" + 
                  (crawlerHopCount * 100000 + crawlerID) +
                    "_output.html")); // file name
       fileWriter.write(htmlDoc);
     } catch ( IOException e) {
       System.err.println("Error in writeHTMLdoc: " + e);
     } finally {
         try {
             if ( fileWriter != null)
               fileWriter.close( );
         } catch ( IOException e) {
           System.err.println("Error when closing writeHTMLdoc: " + e);
         }
     }
     return 0;
   } // end writeHtmlDoc
}
