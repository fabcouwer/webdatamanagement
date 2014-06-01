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
			 * System.out.println("---first test---"); TPEStack tpe =
			 * testStack(); StackEval eval = new
			 * StackEval(tpe.getPatternNode()); reader.setContentHandler(eval);
			 * reader.parse("test.xml");
			 */
			System.out.println("---second test---");
			TPEStack tpe2 = wildcardStack();
			System.out.println(tpe2.getPatternNode().toXMLString());
			StackEval eval = new StackEval(tpe2.getPatternNode());
			reader.setContentHandler(eval);
			reader.parse("people.xml");

			/*
			 * System.out.println("---third test---"); TPEStack tpe3 =
			 * movieStack(); StackEval eval = new
			 * StackEval(tpe3.getPatternNode()); reader.setContentHandler(eval);
			 * reader.parse("movies.xml");
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

	public static TPEStack wildcardStack() {
		PatternNode wc = new PatternNode("*");
		wc.setWildcard(true);
		wc.setQueried(true);
		return new TPEStack(wc, null);
	}

	public static TPEStack personStack() {
		PatternNode person = new PatternNode("person");
		// PatternNode email = new PatternNode("email");
		//PatternNode name = new PatternNode("name");
		//PatternNode all = new PatternNode("*");
		 PatternNode last = new PatternNode("last");
		// email.setOptional(true);
		//all.setWildcard(true);

		TPEStack personStack = new TPEStack(person, null);
		//TPEStack nameStack = new TPEStack(name, personStack);
		//TPEStack allStack = new TPEStack(all, personStack);
		TPEStack lastStack = new TPEStack(last, personStack);

		//personStack.addChildStack(allStack);
		personStack.addChildStack(lastStack);
		//nameStack.addChildStack(allStack);
		// personStack.addChildStack(name);
		// nameStack.addChildStack(last);

		//person.addChild(all);
		person.addChild(last);
		//name.addChild(all);
		// name.addChild(last);

		return personStack;
	}
	
	public static TPEStack person2Stack() {
		PatternNode person = new PatternNode("person");
		PatternNode email = new PatternNode("email");
		PatternNode name = new PatternNode("name");
		PatternNode last = new PatternNode("last");
		email.setQueried(true);
		last.setQueried(true);

		TPEStack personStack = new TPEStack(person, null);
		TPEStack nameStack = new TPEStack(name, personStack);
		TPEStack lastStack = new TPEStack(last, personStack);
		TPEStack emailStack = new TPEStack(email, personStack);

		personStack.addChildStack(nameStack);
		personStack.addChildStack(emailStack);
		personStack.addChildStack(nameStack);
		nameStack.addChildStack(lastStack);

		person.addChild(name);
		person.addChild(email);
		name.addChild(last);

		return personStack;
	}

	public static TPEStack movieStack() {
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
