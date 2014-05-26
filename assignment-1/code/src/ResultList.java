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
		if (pos >= 0)
			results.remove(pos);

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

	public void printFullTable(TPEStack rootStack) {
		System.out.println(printFullHeader(rootStack.getPatternNode()));
		System.out.println(printFullTableContent(rootStack));
	}
	
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
	
	private String printFullTableContent(TPEStack t){
		StringBuilder sb = new StringBuilder();
		for(Match m : t.getMatches()){
			//System.out.print(m.getPre() + "| ");
			sb.append(m.getPre() + "\t");
			for(PatternNode p : m.getSt().getPatternNode().getChildren()){
				for(Match m2 : m.getChildren().get(p)){
					System.out.println(sb.toString());
					sb.setLength(0);
					//TODO print childmatches in same row
				}
			}
		}
		return sb.toString();
	}

	public void printXMLFullTable(TPEStack rootStack) {
		// TODO Auto-generated method stub
		
	}

}
