import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;


public class TestQueries {
	static SAXParserFactory factory;
	static SAXParser parser;
	static XMLReader reader;

	public static void main(String[] args) throws Exception {
		tearUp();
		test("movie.xquery", "movies.xml");
		test("wildcard.xquery", "people.xml");
		test("wildcard2.xquery", "people.xml");
		test("testQ1.xquery", "people.xml");
		test("testQ2.xquery", "people.xml");
		test("testQ3.xquery", "people.xml");
		test("testQ4.xquery", "people.xml");
		test("testQ5.xquery", "people.xml");
	}

	public static void tearUp() throws Exception{
		factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
		reader = parser.getXMLReader();
	}
	
	private static void test(String XQueryFileName, String XMLFileName) throws Exception {
		System.out.println("***** " + XQueryFileName + " *****");
		BufferedReader XQueryFileReader = new BufferedReader(new FileReader(new File(XQueryFileName)));
		String XQueryString = "";
		String line;
		while ((line = XQueryFileReader.readLine()) != null) {
			XQueryString += line + " ";
		}
		XQueryFileReader.close();
		
		InputHandler ih = new InputHandler(XQueryString);
		PatternNode root = ih.parseQuery();
		TPEStack t = new TPEStack(root, null);
		
		StackEval eval = new StackEval(t.getPatternNode());
		reader.setContentHandler(eval);
		reader.parse(XMLFileName);
	}

}
