/*  =============================================================================
  The Class that implements the Lucene indexer using the data passed in
  from the Indexed HTML
  
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
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import crawler_pkg.Indexer_IndexedHTML;

public class Indexer_Obj {
  
  public Indexer_Obj(){
    // Default Constructor
  }
  
  public static void index (Indexer_IndexedHTML page, String indexDir) {
    File index = new File(indexDir); 
    IndexWriter writer = null;
    try { 
      IndexWriterConfig indexConfig = new IndexWriterConfig(Version.LUCENE_34, new StandardAnalyzer(Version.LUCENE_34));
      writer = new IndexWriter(FSDirectory.open(index), indexConfig);
      System.out.println("Indexing to directory '" + index + "'..."); 
      Document luceneDoc = new Document();  
      luceneDoc.add(new Field("text", page.getBody(), Field.Store.YES, Field.Index.ANALYZED));
      luceneDoc.add(new Field("keywords", page.getKeywords(), Field.Store.YES, Field.Index.ANALYZED));
      luceneDoc.add(new Field("description", page.getDesc(), Field.Store.YES, Field.Index.NO));
      luceneDoc.add(new Field("url", page.getURL(), Field.Store.YES, Field.Index.NO));
      luceneDoc.add(new Field("title", page.getTitle(), Field.Store.YES, Field.Index.NO));
      writer.addDocument(luceneDoc);      
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (writer !=null)
        try {
          writer.close();
        } catch (CorruptIndexException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

}
