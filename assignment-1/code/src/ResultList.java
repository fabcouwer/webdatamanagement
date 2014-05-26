import java.util.ArrayList;
import java.util.List;

public class ResultList {
	private ArrayList<Result> results;

	public ResultList() {
		results = new ArrayList<Result>();
	}

	public void add(int id, int parentID, String name, String value, int depth) {
		Result r = new Result(id, parentID, name, value, depth);
		results.add(r);
	}

	public void add(Result r) {
		results.add(r);
	}

	public void removeResult(int id) {
		int pos = -1;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getId() == id) {
				pos = i;
				continue;
			}
		}
		if (pos >= 0){
			results.remove(pos);
		}
	}

	public void printResultList(List<Integer> list) {
		for (int i : list) {
			System.out.println(getResult(i));
		}
	}

	public void print() {
		for (Result r : results) {
			System.out.println(r.toString());
		}
	}

	public Result getResult(int id) {
		for (Result r : results) {
			if (r.getId() == id) {
				return r;
			}
		}
		return null;
	}
	/**
	 * public method of printing the header and the contents of the Full table
	 * @param rootStack
	 */
	public void printFullTable(TPEStack rootStack) {
		System.out.println("Table with Numbers");
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		System.out.println(printFullTableContent(rootStack));
	}
	
	/**
	 * prints first line of table
	 * @param p
	 * @return
	 */
	private String printFullHeader(PatternNode p){
		StringBuilder sb = new StringBuilder();
		sb.append(p.getName() + "\t|");
		for(PatternNode p1 : p.getChildren()){
			if(p1.getChildren()!=null){
				sb.append(printFullHeader(p1));
			}
		}
		return sb.toString();
	}
	
	/**
	 * person 	email 	name 	last
	 * 2	 	3 		4 		6
	 * 11 		12 		14 		16
	 * 11 		13 		14 		16
	 * Is now printing in order of: person, name, last, email 
	 */
	private String printFullTableContent(TPEStack t){
		StringBuilder sb = new StringBuilder();
		for(Match m : t.getMatches()){
			sb.append(printRecursiveTableContent(m));// recursively print each row for all matches
			System.out.println(sb.toString());// print it out
			sb.setLength(0);// reset the stringBuffer
		}
		return sb.toString();
	}
	
	private String printRecursiveTableContent(Match m){
		StringBuilder sb = new StringBuilder();
		sb.append(m.getPre() + "\t|"); //print the number
		for(PatternNode p : m.getSt().getPatternNode().getChildren()){
			for(Match m2 : m.getChildren().get(p)){// get the child matches that match the patternNode
				sb.append("*" + printRecursiveTableContent(m2));// call recursively
				//TODO Fix double rows when there are two email adresses for example
				//TODO remove last entry and concat next one
			}
		}
		return sb.toString();
	}
	
	
	public void printNameFullTable(TPEStack rootStack) {
		System.out.println("Table with Names");
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		System.out.println(printNameTableContent(rootStack));
		
	}
	
	public String printNameTableContent(TPEStack t){
		StringBuilder sb = new StringBuilder();
		for(Match m : t.getMatches()){
			sb.append(printNameRecursiveTableContent(m));
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		return sb.toString();
	}

	private String printNameRecursiveTableContent(Match m) {
		StringBuilder sb = new StringBuilder();
		sb.append(m.getPre()+ "\t|");//TODO change to value of the id
		for(PatternNode p : m.getSt().getPatternNode().getChildren()){
			for(Match m2 : m.getChildren().get(p)){
				sb.append("*" + printNameRecursiveTableContent(m2));
				//TODO Fix double rows when two email adresses for example
				//TODO remove last entry and concat next one
			}
		}
		return sb.toString();
	}
	
}
