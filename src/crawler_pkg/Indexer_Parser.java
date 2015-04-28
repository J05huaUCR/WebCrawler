package crawler_pkg;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import crawler_pkg.Indexer_Obj;
import crawler_pkg.Indexer_IndexedHTML;
import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

public class Indexer_Parser {
  private String htmlPath = "";
  private String indexPath = "";
  public int id = 0;
  
  public Indexer_Parser(){
    // default constructor
  }
  
  public Indexer_Parser(String inputPath, String outputPath, int i) throws MalformedURLException, IOException {
    htmlPath = inputPath;
    indexPath = outputPath;
    id = i;
  }
  
  public Indexer_IndexedHTML extractText() throws MalformedURLException, IOException {
    
    Indexer_IndexedHTML indexPage = new Indexer_IndexedHTML();
    if ( htmlPath.indexOf(':') == -1 ) htmlPath="file:" + htmlPath;
    MicrosoftConditionalCommentTagTypes.register();
    PHPTagTypes.register();
    PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
    MasonTagTypes.register();
    Source source=new Source(new URL(htmlPath));

    // Call fullSequentialParse manually as most of the source will be parsed.
    source.fullSequentialParse();
    
    indexPage.setID(id);

    String title=getTitle(source);
   // System.out.println("Document title:"); 
   // System.out.println(title==null ? "(none)" : title);
    indexPage.setTitle(title);

    String description=getMetaValue(source,"description");
    //System.out.println("\nDocument description:");
    //System.out.println(description==null ? "(none)" : description);
    indexPage.setDesc(description);

    String keywords=getMetaValue(source,"keywords");
    //System.out.println("\nDocument keywords:");
   // System.out.println(keywords==null ? "(none)" : keywords);
    indexPage.setKeywords(keywords);
  
    //System.out.println("\nLinks to other documents:");
    List<Element> linkElements=source.getAllElements(HTMLElementName.A);
    for (Element linkElement : linkElements) {
      String href=linkElement.getAttributeValue("href");
      if (href==null) continue;
      // A element can contain other tags so need to extract the text from it:
      //String label=linkElement.getContent().getTextExtractor().toString();
      //System.out.println(label+" <"+href+'>');
    }
    
    //System.out.println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
   // System.out.println(source.getTextExtractor().setIncludeAttributes(true).toString());
    //indexPage.setBody(source.getTextExtractor().setIncludeAttributes(true).toString());
    
    /*
     * System.out.println("\nSame again but this time extend the TextExtractor class 
     * to also exclude text from P elements and any elements with class=\"control\":\n");
     */
    /**/
    TextExtractor textExtractor = new TextExtractor(source) {
      public boolean excludeElement(StartTag startTag) {
        return startTag.getName() == HTMLElementName.A || "control".equalsIgnoreCase(startTag.getAttributeValue("class"));
      }
    };
    indexPage.appendBody(textExtractor.setIncludeAttributes(true).toString());

    //System.out.println(textExtractor.setIncludeAttributes(true).toString());
    
    Indexer_Obj.index(indexPage, indexPath);
    /**/
    return indexPage;
  }
  
  private static String getTitle(Source source) {
    Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
    if (titleElement==null) return null;
    // TITLE element never contains other tags so just decode it collapsing whitespace:
    return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
  }

  private static String getMetaValue(Source source, String key) {
    for (int pos=0; pos<source.length();) {
      StartTag startTag=source.getNextStartTag(pos,"name",key,false);
      if (startTag==null) return null;
      if (startTag.getName()==HTMLElementName.META)
        return startTag.getAttributeValue("content"); // Attribute values are automatically decoded
      pos=startTag.getEnd();
    }
    return null;
  }
  
  public void setInputPath(String p) {
    htmlPath = p;
    return;
  }
  
  public void setIndexPath(String p) {
    indexPath = p;
    return;
  }
}
