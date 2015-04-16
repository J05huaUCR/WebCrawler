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
  CrawlerThread( String name){
      threadName = name;
      //System.out.println("Creating " +  threadName );
      
  }
  
  // Runnable
  public void run() {
     //System.out.println("Running " +  threadName );
     try {
       /*
        * Desired actions for the Thread
        */
      threadSpider.outputVars();
      
     Thread.sleep(3000);

    } catch (InterruptedException e) {
        System.err.println("Thread " +  threadName + " interrupted.");
    }
     
     // Will exit thread here
   
  }
  
  // Starter
  public void start (CrawlURLObj url, Queue< CrawlURLObj > frontier, HashMap<String, Integer> visited, Environment appEnv) throws IOException {
     //System.out.println("Starting " +  threadName );
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
