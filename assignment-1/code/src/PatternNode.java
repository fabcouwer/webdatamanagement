import java.util.ArrayList;
import java.util.List;

public class PatternNode {

	private String name;

	// TODO keep track of node ID's to link to Match objects
	private int id;

	private ArrayList<PatternNode> children;

	public PatternNode(String name) {
		this.name = name;
		this.children = new ArrayList<PatternNode>();
	}

	public void addChild(PatternNode p) {
		children.add(p);
	}

	public void removeChild(PatternNode p) {
		children.remove(p); // List remains unchanged if it does not contain p
	}

	public List<PatternNode> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

}
