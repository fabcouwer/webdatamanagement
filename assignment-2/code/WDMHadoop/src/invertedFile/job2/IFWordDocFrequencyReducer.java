package invertedFile.job2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//<title> <word><frequency> -> 
public class IFWordDocFrequencyReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		int totalWordInDocument = 0;
		Map<String, Integer> wordFreq = new HashMap<>();
		for (Text value : values) {
			int count;
			try {
				count = Integer.parseInt(value.toString().split("=")[1]);
			} catch (NumberFormatException e) {
				count = 0;
			}
			totalWordInDocument += count;
			wordFreq.put(value.toString().split("=")[0], count);
		}
		for (String word : wordFreq.keySet()) {
			int i = wordFreq.get(word);
			context.write(new Text(word + "@" + key), new Text("" + i + "/"
					+ totalWordInDocument));
		}
	}
}
