import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

public class TestParse {

	public static void main(String[] args) {
		try {

			// Initialize SAX XMLReader
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();

			// Link StackEval to that reader and read the xml file
			System.out.println("---first test---");
			TPEStack tpe = testStack();
			StackEval eval = new StackEval(tpe.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("test.xml");
			
			System.out.println("---second test---");
			TPEStack tpe2 = personStack();
			eval = new StackEval(tpe2.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("people.xml");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Constructs the TPEStack corresponding to test.xml
	public static TPEStack testStack() {

		PatternNode actor = new PatternNode("actor");
		TPEStack actorStack = new TPEStack(actor, null);

		PatternNode firstName = new PatternNode("first_name", "Viggo");
		PatternNode lastName = new PatternNode("last_name", "Mortensen");
		//PatternNode birthDate = new PatternNode("birth_date", "1958");
		//PatternNode role = new PatternNode("role", "Tom Stall");

		actor.addChild(firstName);
		actor.addChild(lastName);
		//actor.addChild(birthDate);
		//actor.addChild(role);

		actorStack.addChildStack(firstName);
		actorStack.addChildStack(lastName);
		//actorStack.addChildStack(birthDate);
		//actorStack.addChildStack(role);

		//return new TPEStack(actor, null);
		return actorStack;
	}
	
	public static TPEStack personStack(){
		PatternNode person = new PatternNode("person");
		TPEStack personStack = new TPEStack(person, null);
		
		personStack.addChildStack(new PatternNode("email"));
		PatternNode name = new PatternNode("name");
		personStack.addChildStack(name);
		name.addChild(new PatternNode("last"));
		
		return personStack;
	}
}
