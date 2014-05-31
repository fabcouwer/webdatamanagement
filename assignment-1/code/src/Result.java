public class Result implements Comparable<Result>{
	int id;
	int parentId;
	String name;
	String value;
	int depth;
	//TODO keep track of whether related node was queried

	public Result(int id, int parentId, String name, String value, int depth) {
		this.id = id;
		this.parentId = parentId;
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int i) {
		this.parentId = i;
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
		return "Result [id=" + id + ", parentId=" + parentId + ", name=" + name
				+ ", value=" + value + ", depth=" + depth + "]";
	}

	@Override
	public int compareTo(Result other) {
		//Sort Results in order of ascending ID
		return (this.getId() - other.getId());
	}

}
