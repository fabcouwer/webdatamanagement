import java.util.ArrayList;
import java.util.List;

public class ResultList {
	private ArrayList<Result> results;

	public ResultList() {
		results = new ArrayList<Result>();
	}

	public void add(int id, String name, String value, int depth) {
		Result r = new Result(id, name, value, depth);
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

}
