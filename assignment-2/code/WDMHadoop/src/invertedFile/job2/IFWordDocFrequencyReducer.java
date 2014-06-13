package invertedFile.job2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//<title> <word><frq> -> 
public class IFWordDocFrequencyReducer extends Reducer<Text, Text, Text, IntWritable>{
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		int totalWordInDocument = 0;
		Map<String, Integer> wordFreq= new HashMap<>();
		for(Text value : values){
			totalWordInDocument += Integer.parseInt(value.toString().split("=")[1]);
			wordFreq.put(value.toString().split("=")[0], Integer.parseInt(value.toString().split("=")[1]));
		}
		for(String word: wordFreq.keySet()){
			context.write(key, new IntWritable(totalWordInDocument));
		}
	}
}
