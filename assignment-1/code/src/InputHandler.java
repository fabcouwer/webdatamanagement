import java.util.ArrayList;
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
	private HashMap<String, String> parents = new HashMap<String, String>();

	// private ArrayList<String> requiredNodes = new ArrayList<String>();

	public InputHandler(String q) {
		this.query = q;
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

		return null;
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

			if (newNode.getName().equals("*")) {
				newNode.setWildcard(true);
			}
			nodes.put(newNode.getName(), newNode);

			TPEStack nodeStack = new TPEStack(newNode, stacks.get(parent
					.getName()));
			stacks.put(newNode.getName(), nodeStack);
			stacks.get(parent.getName()).addChildStack(nodeStack);
			parent.addChild(newNode);
			parents.put(parent.getName(), newNode.getName());

			if (loc > 0)
				parseDescendants(newNode, remaining.substring(loc + 1));

		}
	}

	private void parseWhere() {
		// TODO Auto-generated method stub

	}

	private void parseReturn() {
		// TODO Auto-generated method stub

	}

	// Link all parent-child pairs together
	public void processParents() {
		// TODO
	}

	public static void main(String[] args) {
		String testQ = "for $p in //person[email][name/last] where $p/email=’m@home’ return ($p//first, $p//last)";

		InputHandler ih = new InputHandler(testQ);
		System.out.println(ih.qFor);
		System.out.println(ih.qIn);
		System.out.println(ih.qWhere);
		System.out.println(ih.qReturn);
		System.out.println();
		ih.parseQuery();
	}

}
