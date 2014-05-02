import java.util.List;
import java.util.Stack;

public class StackEval {

	// TreePattern q;
	TPEStack rootStack; // stack for the root of q

	// pre number of the last element which has started:
	int currentPre = 0;

	// pre numbers for all elements having started but not ended yet:
	Stack<Integer> preOfOpenNodes;

	public void startElement(String localName, List<Attribute> attributes) {

		for (TPEStack s : rootStack.getDescendantStacks()) {
			if (localName == s.p.name && s.spar.top().state == 1) {
				Match m = new Match(currentPre, s.spar.top(), s);
				// create a match satisfying the ancestor conditions
				// of query node s.p
				s.push(m);
				preOfOpenNodes.push(currentPre);
			}
			currentPre++;
		}
		for (Attribute a : attributes) {
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()) {
				if (a.name == s.p.name && s.spar.top().state == 1) {
					Match ma = new Match(currentPre, s.spar.top(), s);
					s.push(ma);
				}
			}
			currentPre++;
		}
	}

	public void endElement(String localName) {
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		int preOflastOpen = preOfOpenNodes.pop();
		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()) {
			if (s.p.name == localName && s.top().state == 1 && s.top().pre == preOflastOpen) {
				// all descendants of this Match have been traversed by now.
				Match m = s.pop();

				// check if m has child matches for all children of its pattern node
				for (PatternNode pChild : s.p.getChildren()) {
					// pChild is a child of the query node for which m was created
					if (m.children.get(pChild) == null) {
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent
						remove(m, s);
					}
				}
				m.close();
			}
		}
	}

	public void remove(Match m, TPEStack s) {
		// TODO
	}

}
