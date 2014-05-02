import java.util.ArrayList;
import java.util.Map;

public class Match {

	int start;
	int state;
	int pre;
	Match parent;
	Map<PatternNode, ArrayList<Match>> children;
	TPEStack st;

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
}
