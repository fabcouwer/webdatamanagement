import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class StackEval implements ContentHandler {

	// stack for the root of the tree pattern
	TPEStack rootStack;

	int currentPre = 1;

	// Pre numbers for all open elements
	Stack<Integer> preOfOpenNodes = new Stack<Integer>();

	// Map of pre numbers to String values
	Map<Integer, String> nodeStrings = new HashMap<Integer, String>();

	// Map of pre numbers to Matches
	Map<Integer, Match> resultsMap = new HashMap<Integer, Match>();

	// ResultList for all nodes traversed by the algorithm
	ResultList results = new ResultList();

	// Set root and initialize stacks for the nodes in the tree pattern
	public StackEval(PatternNode root) {
		this.rootStack = new TPEStack(root, null);
		rootStack.initializeTree();
	}

	@Override
	public void startElement(String nameSpaceURI, String localName,
			String rawName, Attributes attributes) {
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			TPEStack spar = s.getSpar();
			if (rawName.equals(p.getName()) || p.isWildcard()) {
				if (spar == null) {
					Match m = new Match(currentPre, null, s);
					resultsMap.put(currentPre, m);
					s.push(m);
				} else if (spar.top() != null && spar.top().getState() == 1) {
					Match m = new Match(currentPre, spar.top(), s);
					spar.top().addChild(s.getPatternNode(), m);
					// create a match satisfying the ancestor conditions of
					// query node s.p
					resultsMap.put(currentPre, m);
					s.push(m);
				}
			}
		}
		// Attributes part
		for (int i = 0; i < attributes.getLength(); i++) {
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()) {
				PatternNode p = s.getPatternNode();
				TPEStack spar = s.getSpar();
				if (attributes.getLocalName(i).equals(p.getName())
						&& (spar == null || spar.verifyTopMatch())) {
					Match ma;
					if (spar == null) {
						ma = new Match(currentPre, null, s);
					} else {
						ma = new Match(currentPre, spar.top(), s);
						spar.top().addChild(p, ma);
					}
					nodeStrings.put(currentPre, attributes.getValue(i));
					ma.close();
					s.push(ma);
				}
			}
		}
		preOfOpenNodes.push(currentPre);
		currentPre++;
	}

	@Override
	public void endElement(String nameSpaceURI, String localName, String rawName) {
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		int preOflastOpen = preOfOpenNodes.pop();

		// set this element's parent
		if (!preOfOpenNodes.isEmpty()) {
			results.getResult(preOflastOpen).setParentId(preOfOpenNodes.peek());
			results.getResult(preOflastOpen).setName(rawName);
		}

		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()) {
			PatternNode p = s.getPatternNode();
			// Only check last 2 if s.top() is not null
			if ((p.getName().equals(rawName) || p.isWildcard())
					&& s.verifyTopMatch() && s.top().getPre() == preOflastOpen) {
				// all descendants of this Match have been traversed by now.
				Match m = s.top();
				Result r1 = results.getResult(m.getPre());
				if (r1 != null) {
					r1.setName(rawName);
					if (p.isQueried()) {
						r1.setQueried(true);
					}
				}
				m.close();

				// Check for the value of the node
				String value = m.getSt().getPatternNode().getValue();
				if (!value.isEmpty()
						&& !value.equals(nodeStrings.get(m.getPre()))) {
					resultsMap.remove(m.getPre());
					remove(m, s);

					if (r1 != null) {
						r1.setQueried(false);
					}
					if (m.getParent() != null) {
						m.getParent().removeChild(s.getPatternNode(), m);
					}
				}

				// check if m has child matches for all children of its pattern
				// node
				for (PatternNode pChild : p.getChildren()) {
					// pChild is a child of the query node for which m was
					// created
					if (!pChild.isOptional()
							&& (m.getChildren().get(pChild) == null || m
									.getChildren().get(pChild).size() == 0)) {
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent
						remove(m, s);
						if (r1 != null) {
							r1.setQueried(false);
						}
						resultsMap.remove(m.getPre());
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
		// EMPTY: initializing the necessary stacks is performed in the
		// constructor
	}

	@Override
	public void endDocument() throws SAXException {
		ResultList finalResults = new ResultList();
		for (Integer i : resultsMap.keySet()) {
			Result addToFinal = results.getResult(i);
			if (addToFinal != null && addToFinal.isQueried()) {
				finalResults.add(addToFinal);
			}

		}
		finalResults.sortByID();

		// Print results:
		// Table with numbers, table with names and XML
		finalResults.printFullTable(rootStack);
		finalResults.printNameFullTable(rootStack);
		System.out.println(results.printXML(rootStack));

	}

	// Below: Methods used in processing elements

	// Remove m from given stack's matches
	public void remove(Match m, TPEStack s) {
		s.getMatches().remove(m);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String str = new String(ch, start, length).trim();
		int last = preOfOpenNodes.lastElement();

		// Update result's value if applicable
		Result r1 = results.getResult(last);
		if (r1 != null) {
			r1.setValue(r1.getValue() + str); // Append in case of multiple
												// calls to characters
		} else {
			r1 = new Result(last, -1, null, str, preOfOpenNodes.size());
			results.add(r1);
		}

		// Update nodeStrings if applicable
		if (str.length() > 0) {
			if (nodeStrings.containsKey(last))
				nodeStrings.put(last, nodeStrings.get(last) + " " + str);
			else
				nodeStrings.put(last, str);
		}
	}

	// Below: unused methods from the ContentHandler interface

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
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
	
	public static void main(String[] args) throws Exception{
		if(args.length>0 && args[1].equals("testrun")){
			TestQueries.main(null);
		}
		else if(args.length==3){
			InputHandler.main(args);
		}
		else{
			System.out.println("To do a testrun call:\nStackEval testrun");
			System.out.println("otherwise call:\nStackEval <xquery file> <xml file>");
		}
	}
}
