import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResultList {
	private ArrayList<Result> result;
	
	public ResultList(){
		result = new ArrayList<Result>();
	}
	
	public void add(int id, String name, String value, int depth){
		Result r = new Result(id, name, value, depth);
		result.add(r);
	}
	
	public void add(Result r){
		result.add(r);
	}
	
	public void printResultList(List<Integer> results3){
		for(int i : results3){
			System.out.println(getResult(i));
		}
	}
	
	public Result getResult(int id){
		for(Result r : result){
			if(r.getId()==id){
				return r;
			}
		}
		return null;
	}

}
