import java.util.ArrayList;
import java.util.Map;

public class Match {

	private int start;
	private int state;
	private int pre;
	private Match parent;
	private Map<PatternNode, ArrayList<Match>> children;
	private TPEStack st;

	public Match(int i, Match p, TPEStack s) {
		start = i;
		parent = p;
		st = s;
		// NYF
	}

	int getStatus() {
		return 0;
	}

	public void close() {
		// TODO
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
