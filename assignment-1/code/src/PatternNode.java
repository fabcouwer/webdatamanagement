import java.util.ArrayList;
import java.util.List;

public class PatternNode {

	private String name;

	private ArrayList<PatternNode> children;

	public PatternNode(String name) {
		this.name = name;
		this.children = new ArrayList<PatternNode>();
	}

	public List<PatternNode> getChildren() {
		return new ArrayList<PatternNode>();
	}

	public String getName() {
		return name;
	}

}
