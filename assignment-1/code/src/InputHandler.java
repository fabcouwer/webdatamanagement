import java.util.HashMap;

//InputHandler: construct tree from a given query
public class InputHandler {

	private String query;
	private String qFor = "";
	private String qIn = "";
	private String qWhere = "";
	private String qReturn = "";

	private PatternNode root;
	private HashMap<String, PatternNode> nodes = new HashMap<String, PatternNode>();
	private HashMap<String, TPEStack> stacks = new HashMap<String, TPEStack>();

	// private ArrayList<String> requiredNodes = new ArrayList<String>();

	public InputHandler(String q) {
		this.query = q;

		// Ensure correct quote marks in where
		q.replaceAll("’", "'");

		int indexIn = query.indexOf(" in ");
		int indexWhere = query.indexOf(" where ");
		int indexReturn = query.indexOf(" return ");

		qFor = query.substring(0, indexIn);

		if (indexWhere >= 0) {
			qIn = query.substring(indexIn + 1, indexWhere);
			qWhere = query.substring(indexWhere + 1, indexReturn);
		} else {
			qIn = query.substring(indexIn + 1, indexReturn);
		}
		qReturn = query.substring(indexReturn + 1);

	}

	public PatternNode parseQuery() {

		parseFor();
		parseIn();
		parseWhere();
		parseReturn();

		return root;
	}

	private void parseFor() {
		// Remove "for " from qFor
		qFor = qFor.substring(4);
	}

	private void parseIn() {
		String content = qIn.substring(3);

		int delimiter = content.indexOf("[");
		String[] descendants = new String[0];

		// If delimiter -1 no descendant conditions found
		if (delimiter == -1) {
			root = new PatternNode(content.substring(2));
		} else {
			root = new PatternNode(content.substring(2, delimiter));
			descendants = content.substring(delimiter).split("]");
		}
		root.setFullName(root.getName());
		TPEStack rootStack = new TPEStack(root, null);
		stacks.put(root.getName(), rootStack);
		nodes.put(root.getName(), root);

		for (int i = 0; i < descendants.length; i++) {
			// Remove the [ from descendant names
			descendants[i] = descendants[i].substring(1);
			// System.out.println(descendants[i]);

			// parse descendants for new nodes
			parseDescendants(root, descendants[i]);
		}

	}

	private void parseDescendants(PatternNode parent, String remaining) {
		if (remaining.startsWith("//")) {
			// TODO handle ancestors
		} else {
			// check if we need to go deeper
			int loc = remaining.indexOf("/");
			PatternNode newNode;

			if (loc > 0) {
				newNode = new PatternNode(remaining.substring(0, loc));
			} else {
				newNode = new PatternNode(remaining);
			}

			newNode.setFullName((parent.getFullName() + "/" + newNode.getName()));
			if (newNode.getName().equals("*")) {
				newNode.setWildcard(true);
			}
			nodes.put(newNode.getFullName(), newNode);

			TPEStack nodeStack = new TPEStack(newNode, stacks.get(parent
					.getFullName()));
			stacks.put(newNode.getFullName(), nodeStack);
			stacks.get(parent.getFullName()).addChildStack(nodeStack);
			parent.addChild(newNode);

			if (loc > 0)
				parseDescendants(newNode, remaining.substring(loc + 1));

		}
	}

	private void parseWhere() {
		String[] conditions = qWhere.substring(6).split(",");
		int forLength = qFor.length();

		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = conditions[i].trim().substring(forLength);

			if (conditions[i].startsWith("//")) {
				// TODO
			} else {
				// Get full path for the condition
				String conditionPath = conditions[i].substring(0,
						conditions[i].indexOf("="));

				// Get the required value
				String conditionValue = conditions[i].substring(
						conditions[i].indexOf("=") + 2,
						conditions[i].length() - 1);

				// Insert value if node exists, otherwise go through the tree to
				// create the node
				if (nodes.containsKey(conditionPath)) {
					nodes.get(conditionPath).setValue(conditionValue);
				} else {
					insertConditions(root, conditionPath, conditionValue);
				}

			}
		}
	}

	private void insertConditions(PatternNode parent, String remainingPath,
			String conditionValue) {
		if (remainingPath.isEmpty()) {
			parent.setValue(conditionValue);
		} else {
			String nextPart = remainingPath.split("/")[1];
			String nextNode = parent.getFullName() + "/" + nextPart;
			if (nodes.containsKey(nextNode)) {
				insertConditions(nodes.get(nextNode),
						remainingPath.substring(nextPart.length() + 1),
						conditionValue);
			} else {
				PatternNode newNode = new PatternNode(nextPart);
				newNode.setFullName(parent.getFullName() + "/" + nextPart);

				nodes.put(newNode.getFullName(), newNode);

				TPEStack nodeStack = new TPEStack(newNode, stacks.get(parent
						.getFullName()));
				stacks.put(newNode.getFullName(), nodeStack);
				stacks.get(parent.getFullName()).addChildStack(nodeStack);
				parent.addChild(newNode);

				insertConditions(newNode,
						remainingPath.substring(nextPart.length() + 1),
						conditionValue);
			}
		}
	}

	private void parseReturn() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String testQ = "for $p in //person[name/last] where $p/email='m@home' , $p//last='Johnson' return ($p//first, $p//last)";

		InputHandler ih = new InputHandler(testQ);
		System.out.println(ih.qFor);
		System.out.println(ih.qIn);
		System.out.println(ih.qWhere);
		System.out.println(ih.qReturn);
		System.out.println();
		ih.parseQuery();
	}

}
