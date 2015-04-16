/*  =============================================================================
  Primary Executable Java file for the Crawler
  
  @version  0.1
  @author   Joshua Potter
  @studentID  860159747
  @author   Ashwin
  @studentID  861------
  @classID  CS242
  @title    Crawler Project
  ========================================================================== */

package crawler_pkg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Crawl {

  public static void main(String[] args) {
    
    // Instantiate Environment
    Environment appEnv = new Environment(args);
    if (appEnv.init() < 0) {
      System.err.println("Errors setting up environment. Exiting.");
    }
    
    // Display Vars
    appEnv.printVars();

    Queue<CrawlURLObj> frontier = new LinkedList<CrawlURLObj>(); // frontier queue
    HashMap<String, Integer> visited = new HashMap<String, Integer>(); // need visited hashtable
    int numURLs = 0; // Count of URL's processed
    int hopCount = 1; // Hops from Source (starts @ 1 due to output naming convention)
    
    // Read inputFile and populate frontier
    try(BufferedReader seedReader = new BufferedReader( new FileReader( appEnv.getInputFileName() ))) {
      
        // Open input file and parse to Frontier queue
        String line = seedReader.readLine();
        while (line != null) {
          frontier.add( new CrawlURLObj(line,hopCount));  
          line = seedReader.readLine();
        }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    /*
     * Multithreading Section
     */
    CrawlerThread[] threads = new CrawlerThread[appEnv.getMaxThreads()]; // set up thread array
    
    // init thread array
    for (int i = 0; i < appEnv.getMaxThreads(); ++i) { 
      threads[i] = new CrawlerThread(("thread" + i));
    }
    
    while (!frontier.isEmpty()) {
      
      // traverse thread array looking for available threads
      for (int i = 0; i < appEnv.getMaxThreads(); ++i) { 
        
        // if there are available threads and files left to parse
        if ( !threads[i].alive() ) {
          
          // instantiated new threads if there are files remaining
          //threadName = "threadID: " + numURLs;
          threads[i] = new CrawlerThread("thread" + numURLs);
          try {
            threads[i].start(frontier.poll(), frontier, visited, appEnv);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          numURLs++;
          
          //WebCrawlerThread wcThread = new WebCrawlerThread("wcThread");
          //wcThread.start(frontier.poll(), frontier, visited, crawlerEnv);
          System.out.println("FRONTIER SIZE: " + frontier.size());
        }
      }
    }
    
    while (Thread.activeCount() > 1) {
      //wait until program thread last running
    }
    //System.out.println("ThreadCount at end: " + Thread.activeCount());
    
    // End.
    System.out.println("...done.");
  }

}
