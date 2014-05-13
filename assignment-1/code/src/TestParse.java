import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;


public class TestParse {

	public static void main(String[] args) {
		try {
		      XMLReader parser = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
		      ContentHandler contentHandler = new StackEval();
		      parser.setContentHandler(contentHandler);
		      parser.parse("test.xml");
		    } 
		catch (Exception e) {
		      e.printStackTrace();
		    }
	}
}
