import java.util.ArrayList;
import java.util.List;

public class PatternNode {

	private String name; // The label (for example 'firstname')
	private String value; // The value (for example 'Bob'), empty if N/A
	private String fullName; // The full name (for example 'person/name/first')
	private boolean wildcard = false;
	private boolean optional = false;
	private boolean queried = false;

	// TODO keep track of node ID's to link to Match objects
	private int id;
	private ArrayList<PatternNode> children;

	// Constructor without value given
	public PatternNode(String name) {
		this.name = name;
		this.value = "";
		this.fullName = "";
		this.children = new ArrayList<PatternNode>();
	}

	// Constructor with value given
	public PatternNode(String name, String value) {
		this.name = name;
		this.value = value;
		this.fullName = "";
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

	public boolean isWildcard() {
		return wildcard;
	}

	public void setWildcard(boolean bool) {
		this.wildcard = bool;
	}

	public void setOptional(boolean bool) {
		this.optional = bool;
	}

	public boolean isQueried() {
		return queried;
	}

	public void setQueried(boolean bool) {
		this.queried = bool;
	}

	public boolean isOptional() {
		return optional;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String s) {
		this.fullName = s;
	}

	public void appendToFullName(String s) {
		this.fullName += s;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatternNode other = (PatternNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatternNode [name=" + name + ", value=" + value + ", id=" + id
				+ ", children=" + "TODO" + "]";
	}
}
