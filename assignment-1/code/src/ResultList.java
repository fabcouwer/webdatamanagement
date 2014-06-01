import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ResultList {
	private ArrayList<Result> results;

	public ResultList() {
		results = new ArrayList<Result>();
	}

	// Add new Result to this ResultList based on values
	public void add(int id, int parentID, String name, String value, int depth) {
		Result r = new Result(id, parentID, name, value, depth);
		results.add(r);
	}

	// Add existing Result object to this ResultList
	public void add(Result r) {
		results.add(r);
	}

	// Remove Result with given id from this ResultList
	public void removeResult(int id) {
		int pos = -1;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getId() == id) {
				pos = i;
				continue;
			}
		}
		if (pos >= 0) {
			results.remove(pos);
		}
	}

	// Print Results corresponding to the id's in list
	public void printResultList(List<Integer> list) {
		for (int i : list) {
			System.out.println(getResult(i));
		}
	}

	// Print every result in this list
	public void print() {
		for (Result r : results) {
			System.out.println(r.toString());
		}
	}

	// Retrieve a Result by id
	public Result getResult(int id) {
		for (Result r : results) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}

	/**
	 * public method of printing the header and the contents of the Full table
	 * 
	 * @param rootStack
	 */
	public void printFullTable(TPEStack rootStack) {
		System.out.println("Table with Numbers");
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		System.out.println(printFullTableContent(rootStack));
	}

	private String printFullHeader(PatternNode p) {
		StringBuilder sb = new StringBuilder();
		sb.append(p.getName() + "\t|");
		for (PatternNode p1 : p.getChildren()) {
			if (p1.getChildren() != null && p1.isQueried()) {
				sb.append(printFullHeader(p1));
			}
		}
		return sb.toString();
	}

	private String printFullTableContent(TPEStack t) {
		StringBuilder sb = new StringBuilder();
		for (Match m : t.getMatches()) {
			sb.append(printRecursiveTableContent(m));// recursively print each
														// row for all matches
			System.out.println(sb.toString());// print it out
			sb.setLength(0);// reset the stringBuffer
		}
		return sb.toString();
	}

	private String printRecursiveTableContent(Match m) {
		StringBuilder sb = new StringBuilder();
		sb.append(m.getPre() + "\t|"); // print the number
		for (PatternNode p : m.getSt().getPatternNode().getChildren()) {
			if (p.isQueried()) {
				if (m.getChildren().get(p) != null) {
					for (Match m2 : m.getChildren().get(p)) {// get the child
																// matches
						if (m2.getPre() != m.getPre())
							sb.append(printRecursiveTableContent(m2));// call
																		// recursively
					}
				} else {
					sb.append("null\t|");
				}
			}
		}
		return sb.toString();
	}

	public void printNameFullTable(TPEStack rootStack) {
		System.out.println("Table with Names");
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		System.out.println(printNameTableContent(rootStack));
	}

	public String printNameTableContent(TPEStack t) {
		StringBuilder sb = new StringBuilder();
		for (Match m : t.getMatches()) {
			sb.append(printNameRecursiveTableContent(m));
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		return sb.toString();
	}

	private String printNameRecursiveTableContent(Match m) {
		StringBuilder sb = new StringBuilder();
		Result r = this.getResult(m.getPre());
		String name = "" + m.getPre();
		if (r != null && r.getValue().length() > 0 && r.isQueried()) {
			name = r.getValue();
		}
		sb.append(name + "\t|");
		for (PatternNode p : m.getSt().getPatternNode().getChildren()) {
			if (p.isQueried()) {
				if (m.getChildren().get(p) != null) {
					for (Match m2 : m.getChildren().get(p)) {
						if (m2.getPre() != m.getPre())
							sb.append(printNameRecursiveTableContent(m2));
					}
				} else {
					sb.append("null\t|");
				}
			}
		}
		return sb.toString();
	}

	public String printXML(TPEStack t) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<results>\n");// enclose results in results node
		for (Match m : t.getMatches()) {
			sb.append(printXMLrecursively(m));
		}
		sb.append("</results>\n");
		return sb.toString();
	}

	private String printXMLrecursively(Match m) {
		StringBuilder sb = new StringBuilder();
		Result r = this.getResult(m.getPre());

		if (r != null
				&& r.getName() != null
				&& (r.isQueried() || (!r.isQueried() && r.getValue().length() == 0))) {
			sb.append(printIndent(r.getDepth()) + "<" + r.getName() + ">"); // opening
																			// tag
			if (r.getValue().length() > 0) {
				sb.append(r.getValue());
			} else {
				sb.append("\n");
			}
			for (PatternNode p : m.getSt().getPatternNode().getChildren()) {
				if (m.getChildren().get(p) != null) {
					for (Match m2 : m.getChildren().get(p)) {// get the child
																// matches
						if (m2.getPre() != m.getPre())
							sb.append(printXMLrecursively(m2));// call
																// recursively
					}
				}
			}
			if (r.getValue().length() == 0) {
				sb.append(printIndent(r.getDepth()));
			}
			sb.append("</" + r.getName() + ">\n"); // print closing tag
		}
		return sb.toString();
	}

	// Prints out a result list in XML format
	public String printXMLfromResultList() {
		StringBuilder sb = new StringBuilder();
		Stack<String> tagStack = new Stack<String>();
		int currentDepth = -1;
		int diff = 0;
		Result r;

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<results>\n");// enclose results in results node)

		// Print every tag
		for (int i = 0; i < results.size(); i++) {
			r = results.get(i);

			// Print closing tags for elements that are now finished
			diff = currentDepth - r.getDepth();
			if (diff > 0) {
				for (int j = 0; j < diff; j++) {
					if (tagStack.isEmpty())
						break;
					else
						sb.append(tagStack.pop());
				}
			}
			currentDepth = r.getDepth();

			// Print <name>value</name> if value exists
			// Else open a tag and put the closing tag on the stack for later
			if (r.getValue().length() > 0) {
				sb.append(printIndent(r.getDepth()) + "<" + r.getName() + ">"
						+ r.getValue() + "</" + r.getName() + ">\n");
			} else {
				sb.append(printIndent(r.getDepth()) + "<" + r.getName() + ">\n");
				tagStack.push(printIndent(r.getDepth()) + "</" + r.getName()
						+ ">\n");
			}

		}

		// Empty out tagstack after last element
		while (!tagStack.isEmpty()) {
			sb.append(tagStack.pop());
		}

		sb.append("</results>\n");

		return sb.toString();
	}

	// Returns a String with a number of tabs corresponding to depth
	private static String printIndent(int depth) {
		String str = "";
		for (int i = 0; i < depth; i++) {
			str += "\t";
		}
		return str;
	}

	// Sorts the results ArrayList by ascending ID
	public void sortByID() {
		Collections.sort(results);
	}

}
