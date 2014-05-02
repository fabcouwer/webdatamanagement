import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	PatternNode p;
	Stack<Match> matches;
	TPEStack spar;

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
}
