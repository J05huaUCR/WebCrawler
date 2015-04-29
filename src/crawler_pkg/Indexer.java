/*  =============================================================================
  Main driver routine for the Indexer application
  
  @version  0.1
  @author   Joshua Potter
  @SID      860159747
  @author   Ashwin Ramadevanahalli
  @SID      861186399
  @classID  CS242
  @title    Crawler & Indexer Project
  ========================================================================== */

package crawler_pkg;

import java.io.File;

import crawler_pkg.Indexer_Obj;
import crawler_pkg.Indexer_IndexedHTML;
import crawler_pkg.Indexer_Parser;
import crawler_pkg.Environment;

public class Indexer {
  
  public static void main(String[] args) throws Exception {
    
    /* 
     * Begin timer
     */
    final float NANO2SEC = 1000000000; // for converting nanoseconds to seconds
    float startTime = System.nanoTime();
    float endTime = System.nanoTime();
    
    /*
     *  Initialize Environment
     */
    Environment appEnv = new Environment(args);
    int fileCount = 0; // counts files being processed
    int maxFiles = 0; // Total number of files in input directory
    
    /* 
     * Retrieve files from input directory
     */
    if (appEnv.getInputDir() != "") {
      File file = new File( appEnv.getInputDir() );
      File[] files = file.listFiles();
      maxFiles = files.length;
      Indexer_Parser parseHTML = new Indexer_Parser();
      Indexer_IndexedHTML indexPage = new Indexer_IndexedHTML();
      
      // Process files in input directory
      while (fileCount < maxFiles) {
        parseHTML = new Indexer_Parser(files[fileCount].getCanonicalPath(), appEnv.getOutputDir() , fileCount);
        indexPage = parseHTML.extractText();
        Indexer_Obj.index(indexPage, appEnv.getOutputDir() );
        fileCount++;
        endTime = System.nanoTime();
        float duration = (endTime - startTime) / NANO2SEC;  //divide by 1000000 to get milliseconds
        System.out.print("Number of files processed: " + fileCount + " | Running time:" + duration + "(secs)\n");
      }
      
      // Processing Complete. Output summary.
      endTime = System.nanoTime();
      float duration = (endTime - startTime) / NANO2SEC;  //divide by 1000000 to get milliseconds
      System.out.print("Time to complete:" + duration + "(secs)\n");
    } else {
      System.err.println("No input directory specified. Exiting.");
      System.exit(-1);
    }
  }
}
