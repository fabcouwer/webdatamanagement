
public class Result {
	int id;
	String name;
	String value;
	int depth;
	public Result(int id, String name, String value, int depth) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.depth = depth;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "Result [id=" + id + ", name=" + name + ", value=" + value
				+ ", depth=" + depth + "]";
	}
}
