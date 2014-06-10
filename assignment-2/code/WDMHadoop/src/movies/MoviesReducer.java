package movies;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MoviesReducer extends
		Reducer<IntWritable, Text, NullWritable, Text> {

	@SuppressWarnings("rawtypes")
	private MultipleOutputs outputs;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setup(Context context) {
		outputs = new MultipleOutputs(context);
	}

	@SuppressWarnings("unchecked")
	public void reduce(IntWritable key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		for (Text value : values) {
			if (key.get() == 1) {
				// Write to title-actor
				outputs.write("titleAndActor", NullWritable.get(), value);
			} else if (key.get() == 2) {
				// Write to director-title
				outputs.write("directorAndTitle", NullWritable.get(), value);
			}
		}
	}

	public void cleanup(Context context) throws IOException,
			InterruptedException {
		if (outputs != null)
			outputs.close();

	}
}