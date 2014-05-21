import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class StackEval implements ContentHandler {

	// TreePattern q;
	TPEStack rootStack; // stack for the root of q

	// Pre number of the last element which has started. Starts at 0
	int currentPre = 1;

	// Pre numbers for all elements having started but not ended yet:
	Stack<Integer> preOfOpenNodes = new Stack<Integer>();

	// Map of pre number to value
	Map<Integer, String> nodeStrings = new HashMap<Integer, String>(23);
	List<String> results2 = new ArrayList<String>();
	List<Integer> results3 = new ArrayList<Integer>();

	public StackEval(PatternNode root) {
		this.rootStack = new TPEStack(root, null);
		rootStack.initializeTree();
	}

	@Override
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributes) {
		System.out.println("startElement() -> " + rawName);
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			TPEStack spar = s.getSpar();
			if (rawName.equals(p.getName())) {
				if (spar == null) {
					Match m = new Match(currentPre, null, s);
					// System.out.println("new Match created");
					results3.add(currentPre);
					s.push(m);
				} else if (spar.top() != null && spar.top().getState() == 1) {
					Match m = new Match(currentPre, spar.top(), s);
					// System.out.println("new Match created");
					spar.top().addChild(s.getPatternNode(), m);
					// create a match satisfying the ancestor conditions of
					// query node s.p
					results3.add(currentPre);
					s.push(m);
				}
			}
		}
		// Attributes part
		// TODO fix this part
		for (int i = 0; i < attributes.getLength(); i++) {
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()) {
				PatternNode p = s.getPatternNode();
				TPEStack spar = s.getSpar();
				// System.out.println("+");
				// System.out.println(attributes.getLocalName(i));
				// System.out.println(p.getName());
				if (attributes.getLocalName(i).equals(p.getName())
						&& spar.top().getState() == 1) {
					Match ma = new Match(currentPre, spar.top(), s);
					// System.out.println("new Match created");
					nodeStrings.put(currentPre, attributes.getValue(i));
					results2.add("<" + attributes.getLocalName(i) + ">"
							+ attributes.getValue(i) + "</"
							+ attributes.getLocalName(i) + ">");
					s.push(ma);
				}
			}
		}
		preOfOpenNodes.push(currentPre);
		currentPre++;
	}

	@Override
	public void endElement(String nameSpaceURI, String localName, String rawName) {
		System.out.println("endElement() -> " + rawName);
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		int preOflastOpen = preOfOpenNodes.pop();
		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			// Only check last 2 if s.top() is not null
			if (p.getName().equals(rawName) && s.verifyTopMatch()
					&& s.top().getPre() == preOflastOpen) {
				// System.out.println("local: "+ localName);
				// System.out.println("raw: "+rawName);
				// all descendants of this Match have been traversed by now.
				Match m = s.pop();
				System.out.println(m.getPre() + " " + p.getName());
				m.close();

				// Check for the value of the node
				String value = m.getSt().getPatternNode().getValue();
				if (value != null && !value.equals(nodeStrings.get(m.getPre()))) {
					remove(m, s);
					if (m.getParent() != null) {
						m.getParent().removeChild(s.getPatternNode(), m);
					}
				}

				// check if m has child matches for all children of its pattern
				// node
				for (PatternNode pChild : p.getChildren()) {
					// pChild is a child of the query node for which m was
					// created
					System.out.println("name" + pChild.getName() + "id: "
							+ currentPre);
					if (m.getChildren().get(pChild) == null
							|| m.getChildren().get(pChild).size() == 0) {
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent
						remove(m, s);
						if (m.getParent() != null) {
							m.getParent().removeChild(s.getPatternNode(), m);
						}
					}
				}
				m.close();
			}
		}
	}

	// Start and End Document

	@Override
	public void startDocument() throws SAXException {
		System.out.println("startDocument()");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument()");
		// TODO Handle closing of document (print results)
		System.out.println(nodeStrings.toString());
		System.out.println(results2.toString());

		for (Integer i : results3) {
			if (nodeStrings.get(i) != null)
				System.out.println(nodeStrings.get(i).toString());
		}

	}

	// Methods used in processing elements (TODO)
	public void remove(Match m, TPEStack s) {
		// System.out.println("remove()");
		s.getMatches().remove(m);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String str = new String(ch, start, length).trim();

		int last = preOfOpenNodes.lastElement();

		if (str.length() > 0) {
			System.out.println("characters: " + str);

			if (nodeStrings.containsKey(last))
				nodeStrings.put(last, nodeStrings.get(last) + " " + str);
			else
				nodeStrings.put(last, str);

			results2.add("<" + preOfOpenNodes.peek() + ">" + str + "</"
					+ preOfOpenNodes.peek() + ">");
		}
	}

	// BELOW: Unused methods from ContentHandler

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {

	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		System.out.println("ignore");

	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {

	}

	@Override
	public void setDocumentLocator(Locator arg0) {

	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {

	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {

	}

}
