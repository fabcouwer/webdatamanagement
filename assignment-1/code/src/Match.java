import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Match {

	private int start;
	private int state; // 0 = closed, 1 = open
	private int pre;
	private Match parent;
	private Map<PatternNode, ArrayList<Match>> children;
	private TPEStack st;

	// Match constructor: sets status to open
	public Match(int currentPre, Match p, TPEStack s) {
		this.start = 0; // TODO implement start

		this.pre = currentPre;
		this.parent = p;
		this.st = s;
		this.state = 1;

		this.children = new HashMap<PatternNode, ArrayList<Match>>();
	}

	int getStatus() {
		return state;
	}

	// Sets Match status to closed
	public void close() {
		this.state = 0;
	}

	// Add child
	public void addChild(PatternNode childNode, Match childMatch) {
		// Make record if it does not exist yet
		if (!children.containsKey(childNode)) {
			children.put(childNode, new ArrayList<Match>());
		}
		//
		children.get(childNode).add(childMatch);

	}

	// Remove child
	public void removeChild(PatternNode childNode, Match m) {
		ArrayList<Match> values = children.get(childNode);
		if (values != null && values.contains(m)) {
			values.remove(m);
		}
	}

	// Getters
	public int getStart() {
		return start;
	}

	public int getState() {
		return state;
	}

	public int getPre() {
		return pre;
	}

	public Match getParent() {
		return parent;
	}

	public Map<PatternNode, ArrayList<Match>> getChildren() {
		return children;
	}

	public TPEStack getSt() {
		return st;
	}

}
