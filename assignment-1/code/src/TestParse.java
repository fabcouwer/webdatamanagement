import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

public class TestParse {

	public static void main(String[] args) {
		try {

			//Initialize SAX XMLReader
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			
			//Link StackEval to that reader and read the xml file
			ContentHandler contentHandler = new StackEval();
			reader.setContentHandler(contentHandler);
			reader.parse("test.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
