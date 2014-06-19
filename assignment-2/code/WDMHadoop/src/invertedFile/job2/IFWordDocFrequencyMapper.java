package invertedFile.job2;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// gets <title><word> <frequency> -> <title> <word><frequency>
public class IFWordDocFrequencyMapper extends
		Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// Retrieve count for a term
		String[] wordAndDocCounter = value.toString().split("\t");
		String counter = wordAndDocCounter[1];

		// Retrieve the term and the corresponding document
		String[] wordAndDoc = wordAndDocCounter[0].split("@");
		String word = wordAndDoc[0];
		String doc = wordAndDoc[1];

		// Write to output: key = document name, value = term and its frequency
		context.write(new Text(doc), new Text(word + "=" + counter));
	}
}
