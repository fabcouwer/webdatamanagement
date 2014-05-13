import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	private PatternNode patternNode;
	private TPEStack spar;
	private Stack<Match> matches;
	private ArrayList<TPEStack> childStacks;

	// Constructor for TPEStack with given set of Matches
	public TPEStack(PatternNode p, Stack<Match> matchStack, TPEStack par) {
		this.patternNode = p;
		this.spar = par;
		this.matches = matchStack;
		this.childStacks = new ArrayList<TPEStack>();
	}

	// Constructor without set of Matches
	public TPEStack(PatternNode p, TPEStack par) {
		this.patternNode = p;
		this.spar = par;
		this.matches = new Stack<Match>();
		this.childStacks = new ArrayList<TPEStack>();
	}

	ArrayList<TPEStack> getDescendantStacks() {
		return childStacks;//TODO childstacks and descendantstack not the same
		
	}

	// gets the stacks for all descendants of p
	public void push(Match m) {
		matches.push(m);
	}

	// Returns the top element of the Matches stack
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
