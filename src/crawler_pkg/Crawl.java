/*  =============================================================================
  Primary Executable Java file for the Crawler
  
  @version  0.1
  @author   Joshua Potter
  @SID      860159747
  @author   Ashwin Ramadevanahalli
  @SID      861186399
  @classID  CS242
  @title    Crawler & Indexer Project
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
    
    /* 
     * ===================================================================
     * Instantiate Application Environment
     * ===================================================================
     */
    int numURLs = 0; // Count of URL's processed
    int hopCount = 1; // Hops from Source (starts @ 1 due to output naming convention)
    Queue<CrawlURLObj> crawlerFrontier = new LinkedList<CrawlURLObj>(); // frontier queue
    HashMap<String, Integer> visitedURLs = new HashMap<String, Integer>(); // need visited hashtable
    Environment appEnv = new Environment(args);
    
    if (appEnv.init() < 0) {
      
      System.err.println("Errors setting up environment. Exiting.");
      System.exit(appEnv.init());
      
    } else {
      
      // Display Vars
      appEnv.printVars();
      //System.exit(0);

      // Read inputFile and populate frontier
      try(BufferedReader seedReader = new BufferedReader( new FileReader( appEnv.getInputFileName() ))) {
        
          // Open input file and parse to Frontier queue
          String line = seedReader.readLine();
          while (line != null) {
            crawlerFrontier.add( new CrawlURLObj(line,hopCount));  
            line = seedReader.readLine();
          }
      } catch (FileNotFoundException e) {
        System.err.println("Errors:");
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println("Errors:");
        e.printStackTrace();
      }
      
    }
    
    /* 
     * ===================================================================
     * Multithreading Section
     * ===================================================================
     */
    
    // init thread array
    CrawlerThread[] threads = new CrawlerThread[ appEnv.getMaxThreads() ];
    for (int i = 0; i < appEnv.getMaxThreads(); ++i) { 
      threads[i] = new CrawlerThread(("thread" + i));
    }
    
    // Main Processing of frontier
    while (!crawlerFrontier.isEmpty()) {
      
      // traverse thread array looking for available threads
      for (int i = 0; i < appEnv.getMaxThreads(); ++i) { 
        
        // if there are available threads and files left to parse
        if ( !threads[i].alive() ) {
          
          // instantiate new threads if there are files remaining
          threads[i] = new CrawlerThread("thread" + numURLs);
          try {
            threads[i].start(crawlerFrontier.poll(), crawlerFrontier, visitedURLs, appEnv);
          } catch (IOException e) {
            System.err.println("Errors:");
            e.printStackTrace();
          }
          numURLs++;
          
          //System.out.println("FRONTIER SIZE: " + frontier.size());
        }
      }
    }
    
    // Poll living threads until all completed.
    while (Thread.activeCount() > 1) {
      //wait until program thread last running
    }
    
    // End.
    System.out.println("Crawler done. " + numURLs + " URL's processed.");
  }

}
