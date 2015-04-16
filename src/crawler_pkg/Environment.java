/*  =============================================================================
  Environment class holds all application variables
  
  @version  0.1
  @author   Joshua Potter
  @studentID  860159747
  @author   Ashwin
  @studentID  861------
  @classID  CS242
  @title    Crawler Project
  ========================================================================== */

package crawler_pkg;

import java.io.File;

public class Environment {
  
  // Object Parameters
  String inputFile = ""; // Input file path/name
  String outputDir = "output"; // Name of output directory passed in ("output" default)
  String outputPath = outputDir + "/";
  int maxHops = 0; // Number of hops (if passed in, 0 is default
  int maxPages = 0; // Maximum number of pages to crawl
  int maxThreads = 10; // Maximum number of threads to instantiate
  int numThreads = 0; // Count of active Threads
  int numPages = 1; // Tracking number of pages visited
  boolean heedRobotsTxt = true; // Flag to read and adhere to robots.txt
  
  // Constructor
  public Environment(String[] args){
    
    // Vars to hold parameter info
    String parameters,flag,mark; 
    
    // Retrieve and parse passed in parameters
    for (int i = 0; i < args.length; i++) {
      parameters = args[i];
      mark = parameters.substring(0,1);
      if ( mark.equals("-") ) {
        
        flag = parameters.substring(1,2);
        
        /*
         * Parse flags
         * 
         * -f gets file name of seeds text file
         * -h gets maximum number of hops
         * -p gets maximum number of pages
         * -o gets the output directory for saving downloaded pages
         * -t gets maximum number of threads to instantiate
         * -r sets to ignore robots.txt when crawling a domain
         */
        switch (flag) {
          
          case "f":
            // seeds file
            inputFile = args[i+1];
          break;

          case "h":
            // Get hopCount and increase by one to offset starting 
            // from 1 instead of 0
            maxHops = Integer.parseInt(args[i+1]) + 1;
          break;
          
          case "p":
            // ceiling of pages to crawl
            maxPages = Integer.parseInt(args[i+1]);
          break;
          
          case "o":
            // Save directory
            outputDir = args[i+1];
          break;
          
          case "t":
            // Get thread count + 1 to account for main executable Thread
            maxThreads = Integer.parseInt(args[i+1]) + 1;
          break;
          
          case "r":
            // Disable reading of robots.txt
            heedRobotsTxt = false;
          break;
        }
      }
    }   
    
  };
  
  public int init() {
    
    // No inputFile name? Exit.
    if (inputFile == "") {
      System.err.println("No file found. Exiting.");
      return -1;
    }
    
    // Create Output Folder for output
    File theDir = new File(outputDir);

    // if the directory does not exist, create it
    if (!theDir.exists()) {
      boolean result = false;
    
      try{
          theDir.mkdir();
          result = true;
      } catch(SecurityException se){
        return -2;
      }        
       
      if(result) {    
         //System.out.println("DIR created");  
      }
    }
    
    return 0;
  }
  
  public String getInputFileName() {
    return inputFile;
  }
  
  public String getOutputDir() {
    return outputDir;
  }
  
  public String getOutputPath() {
    return outputPath;
  }
  
  public int getMaxHops() {
    return maxHops;
  }
  
  public int getMaxPages() {
    return maxPages;
  }
  
  public int getMaxThreads() {
    return maxThreads;
  }
  
  public boolean listenToRobotsTxt() {
    return heedRobotsTxt;
  }
  
  public void increaseThreadCount() {
    numThreads++;
  }
  
  public void decreaseThreadCount() {
    numThreads--;
  }
  
  public int getThreadCount() {
    return numThreads;
  }
  
  public int getPageCount() {
    return numPages;
  }
  
  public void increasePageCount() {
    numPages++;
  }

  public void printVars() {
    // Verification
    System.out.println("Parameters processed:");
    System.out.println("inputFile = " + inputFile);
    System.out.println("outputDir = " + outputDir);
    System.out.println("maxPages = " + maxPages);
    System.out.println("maxHops = " + maxHops);
    System.out.println("maxThreads = " + maxThreads);
    System.out.println("heedRobotsTxt = " + heedRobotsTxt);
  }
}