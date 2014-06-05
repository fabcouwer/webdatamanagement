import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class AuthorsCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();

	public void reduce(Text key, Iterable<IntWritable> values, 
			Context context)
		throws IOException, InterruptedException {
	  
	  /* Iterate on the list to compute the count */
	  int count = 0;
	  for (IntWritable val : values) {
		count += val.get();
	  }
	  result.set(count);
	  context.write(key, result);
	}
  }