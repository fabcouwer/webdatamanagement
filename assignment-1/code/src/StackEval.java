import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class StackEval implements ContentHandler {

	// TreePattern q;
	TPEStack rootStack; // stack for the root of q

	// Pre number of the last element which has started. Starts at 0
	int currentPre = 0;

	// pre numbers for all elements having started but not ended yet:
	Stack<Integer> preOfOpenNodes = new Stack<Integer>();

	public StackEval(PatternNode root) {
		this.rootStack = new TPEStack(root, null);
	}

	@Override
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributes) {
		System.out.println("startElement()");
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			TPEStack spar = s.getSpar();
			if (localName.equals(p.getName()) && spar.top().getState() == 1) {
				Match m = new Match(currentPre, spar.top(), s);
				// create a match satisfying the ancestor conditions
				// of query node s.p
				s.push(m);
				preOfOpenNodes.push(currentPre);
			}
			currentPre++;
		}
		for (int i = 0; i < attributes.getLength(); i++) {
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()) {
				PatternNode p = s.getPatternNode();
				TPEStack spar = s.getSpar();
				if (attributes.getLocalName(i).equals(p.getName())
						&& spar.top().getState() == 1) {
					Match ma = new Match(currentPre, spar.top(), s);
					s.push(ma);
				}
			}
			currentPre++;
		}
	}

	@Override
	public void endElement(String nameSpaceURI, String localName, String rawName) {
		System.out.println("endElement()");
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		int preOflastOpen = preOfOpenNodes.pop();
		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			if (p.getName().equals(localName) && s.top().getState() == 1// really
																		// should
																		// be
																		// equals
																		// instead
																		// of ==
					&& s.top().getPre() == preOflastOpen) {
				// all descendants of this Match have been traversed by now.
				Match m = s.pop();

				// check if m has child matches for all children of its pattern
				// node
				for (PatternNode pChild : p.getChildren()) {
					// pChild is a child of the query node for which m was
					// created
					if (m.getChildren().get(pChild) == null) {
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent
						remove(m, s);
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
		rootStack = new TPEStack(null, null);// TODO ???
		preOfOpenNodes = new Stack<Integer>();// TODO ???
		// TODO initialize stuff???
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument()");
		// TODO Handle closing of document (print results)

	}

	// Methods used in processing elements (TODO)
	public void remove(Match m, TPEStack s) {
		System.out.println("remove()");
		// TODO
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String str = new String(ch, start, length);
		System.out.println("characters: " + str);
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
