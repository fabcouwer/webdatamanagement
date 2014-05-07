import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	private PatternNode patternNode;

	private Stack<Match> matches;
	private TPEStack spar;

	ArrayList<TPEStack> getDescendantStacks() {
		return new ArrayList<TPEStack>();
	}

	// gets the stacks for all descendants of p
	public void push(Match m) {
		matches.push(m);
	}

	public Match top() {
		return matches.peek();
	}

	public Match pop() {
		return matches.pop();
	}

	// Getters
	public PatternNode getPatternNode() {
		return patternNode;
	}

	public Stack<Match> getMatches() {
		return matches;
	}

	public TPEStack getSpar() {
		return spar;
	}

}
