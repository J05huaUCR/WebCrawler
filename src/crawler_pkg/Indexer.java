package crawler_pkg;

import java.io.File;

import crawler_pkg.Indexer_Obj;
import crawler_pkg.Indexer_IndexedHTML;
import crawler_pkg.Indexer_Parser;

public class Indexer {
public static void main(String[] args) throws Exception {
    
    /* 
     * Begin timer
     */
    float startTime = System.nanoTime();
    float endTime = System.nanoTime();
    
    /*
     *  Initialize variables
     */
    String inputDir, outputDir;
    inputDir = outputDir = "";
    final float NANO2SEC = 1000000000; // for converting nanoseconds to seconds
    int numThreads = 10; // default number of threads if not specified at runtime
    int numFiles = 0; // counts files being processed
    
    /*
     * Retrieve and parse passed in parameters
     */
    for (int i = 0; i < args.length; i++) {

      if (args[i].substring(0, 1).equals("-")) {
        String catchMe = args[i].substring(1, 2);
        
        if (catchMe.equals("i")) {
          inputDir = args[i + 1];
        } else if (catchMe.equals("o")) {
          outputDir = args[i + 1];
        } else if (catchMe.equals("t")) {
          numThreads = Integer.parseInt(args[i + 1]);
          System.out.print("Number of threads: " + numThreads);
        } else {
          // Nothing
        }
      }
    }
    
    /* 
     * Retrieve list of files from input director
     */
    File f = new File(inputDir); // current directory
    File[] files = f.listFiles();
    int maxFiles = files.length;
    
    Indexer_Parser parseHTML = new Indexer_Parser();
    Indexer_IndexedHTML indexPage = new Indexer_IndexedHTML();
    while (numFiles < maxFiles) {
      //parseHTML.setInputPath(files[numFiles].getCanonicalPath());
      //parseHTML.setIndexPath(outputDir);
      parseHTML = new Indexer_Parser(files[numFiles].getCanonicalPath(), outputDir, numFiles);
      indexPage = parseHTML.extractText();
      Indexer_Obj.index(indexPage, outputDir);
      numFiles++;
      endTime = System.nanoTime();
      float duration = (endTime - startTime) / NANO2SEC;  //divide by 1000000 to get milliseconds
      System.out.print("Number of files processed: " + numFiles + " | Running time:" + duration + "(secs)\n");
    }

    System.out.print("outputDir: " + outputDir);
    
     /*
      * end Timer
      */
    endTime = System.nanoTime();
    float duration = (endTime - startTime) / NANO2SEC;  //divide by 1000000 to get milliseconds
    System.out.print("Time to complete:" + duration + "(secs)\n");
    
  }

}
