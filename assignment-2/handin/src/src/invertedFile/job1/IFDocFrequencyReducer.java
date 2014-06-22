package invertedFile.job1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class IFDocFrequencyReducer extends
		Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();
	
	// add all counts and write them to <title><word>, <count>
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		for (IntWritable val : values) {
			count += val.get();
		}
		result.set(count);
		context.write(key, result);
	}
}