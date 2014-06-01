import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	private PatternNode patternNode;
	private TPEStack spar;
	private Stack<Match> matches;
	private ArrayList<TPEStack> childStacks;

	// Constructor for TPEStack with given set of Matches
	public TPEStack(PatternNode p, Stack<Match> matchStack, TPEStack parent) {
		this.patternNode = p;
		this.spar = parent;
		this.matches = matchStack;
		this.childStacks = new ArrayList<TPEStack>();
	}

	// Constructor without set of Matches
	public TPEStack(PatternNode p, TPEStack parent) {
		this.patternNode = p;
		this.spar = parent;
		this.matches = new Stack<Match>();
		this.childStacks = new ArrayList<TPEStack>();
	}

	// Construct descendant stacks
	public ArrayList<TPEStack> getDescendantStacks() {
		ArrayList<TPEStack> descendants = new ArrayList<TPEStack>();
		descendants.add(this);
		for (TPEStack s : childStacks) {
			ArrayList<TPEStack> list = s.getDescendantStacks();
			for (TPEStack t : list)
				descendants.add(t);
		}
		return descendants;

	}

	public void initializeTree() {
		if (patternNode.getChildren().size() != 0) {

			for (PatternNode child : patternNode.getChildren()) {
				TPEStack stack = new TPEStack(child, this);
				stack.initializeTree();
				childStacks.add(stack);
			}
		}
	}

	// Add child stack
	public void addChildStack(TPEStack child) {
		childStacks.add(child);
	}

	public void addChildStack(PatternNode node) {
		childStacks.add(new TPEStack(node, this));
	}

	// Stack operations
	public void push(Match m) {
		matches.push(m);
	}

	// Returns the top element of the Matches stack
	public Match top() {
		if (!matches.isEmpty()) {
			return matches.peek();
		} else
			return null;
	}

	// Returns true if the top match in matches is not null and open
	public boolean verifyTopMatch() {
		if (!matches.isEmpty())
			return (matches.peek().getState() == 1);
		else
			return false;
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

	@Override
	public String toString() {
		return "TPEStack [patternNode=" + patternNode.toString() + "]";
	}
}
