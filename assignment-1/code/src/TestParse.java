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
			/*
			System.out.println("---first test---");
			TPEStack tpe = testStack();
			StackEval eval = new StackEval(tpe.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("test.xml");
			*/
			System.out.println("---second test---");
			TPEStack tpe2 = personStack();
			StackEval eval = new StackEval(tpe2.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("people.xml");
			
			/*
			System.out.println("---third test---");
			TPEStack tpe3 = movieStack();
			StackEval eval = new StackEval(tpe3.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("movies.xml");
			*/
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
		// PatternNode birthDate = new PatternNode("birth_date", "1958");
		// PatternNode role = new PatternNode("role", "Tom Stall");

		actor.addChild(firstName);
		actor.addChild(lastName);
		// actor.addChild(birthDate);
		// actor.addChild(role);

		actorStack.addChildStack(firstName);
		actorStack.addChildStack(lastName);
		// actorStack.addChildStack(birthDate);
		// actorStack.addChildStack(role);

		// return new TPEStack(actor, null);
		return actorStack;
	}

	public static TPEStack personStack() {
		PatternNode person = new PatternNode("person");
		//PatternNode email = new PatternNode("email");
		PatternNode name = new PatternNode("name");
		PatternNode all = new PatternNode("*");
		//PatternNode last = new PatternNode("last");
		//email.setOptional(true);
		all.setWildcard(true);

		TPEStack personStack = new TPEStack(person, null);
		TPEStack nameStack = new TPEStack(name, personStack);
		TPEStack allStack = new TPEStack(all, nameStack);

		personStack.addChildStack(nameStack);
		nameStack.addChildStack(allStack);
		//personStack.addChildStack(name);
		//nameStack.addChildStack(last);

		person.addChild(name);
		name.addChild(all);
		//name.addChild(last);

		return personStack;
	}
	
	public static TPEStack movieStack(){
		PatternNode movie = new PatternNode("movie");
		PatternNode title = new PatternNode("title");
		PatternNode year = new PatternNode("year", "2003");
		PatternNode summary = new PatternNode("summary");
		summary.setOptional(true);
		
		TPEStack movieStack = new TPEStack(movie, null);
		TPEStack titleStack = new TPEStack(title, movieStack);
		TPEStack yearStack = new TPEStack(year, movieStack);
		TPEStack summaryStack = new TPEStack(summary, movieStack);
		
		movieStack.addChildStack(titleStack);
		movieStack.addChildStack(yearStack);
		movieStack.addChildStack(summaryStack);
		
		movie.addChild(title);
		movie.addChild(year);
		movie.addChild(summary);
		
		return movieStack;
	}
}
