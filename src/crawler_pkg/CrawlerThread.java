/*  =============================================================================
  Thread creation to instantiate each Crawler
  
  @version  0.1
  @author   Joshua Potter
  @studentID  860159747
  @author   Ashwin
  @studentID  861------
  @classID  CS242
  @title    Crawler Project
  ========================================================================== */

package crawler_pkg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;

public class CrawlerThread extends Thread {
  private Thread t;
  private String threadName;
  public String threadURL;
  public int threadHopCount;
  public Crawler threadSpider;
  private boolean threadAlive = false;
  
  // Constructor
  CrawlerThread( String name ){
      threadName = name;
  }
  
  // Runnable
  public void run() {
    try {
       threadSpider.outputVars();
       Thread.sleep(3000);
    } catch (InterruptedException e) {
        System.err.println("Thread " +  threadName + " interrupted.");
    }
  }
  
  // Starter
  public void start (
        CrawlURLObj url, 
        Queue< CrawlURLObj > frontier, 
        HashMap<String, Integer> visited, 
        Environment appEnv
        ) throws IOException {
     threadURL = url.getURL();
     threadHopCount = url.getHop();
     appEnv.increasePageCount();
     threadSpider = new Crawler(appEnv.getPageCount(), threadURL, threadHopCount, visited, appEnv);
     Crawler.downloadHTML(frontier, appEnv, visited);
     if (t == null) {
        t = new Thread (this, threadName);
        t.start();
     }
  }
  
  public boolean alive() {
    return threadAlive;
  }

}
