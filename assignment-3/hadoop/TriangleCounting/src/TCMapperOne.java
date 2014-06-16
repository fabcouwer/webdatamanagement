import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TCMapperOne extends
		Mapper<LongWritable, Text, LongWritable, LongWritable> {

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		StringTokenizer tokenizer = new StringTokenizer(value.toString());
		long e1, e2;
		while (tokenizer.hasMoreTokens()) {
			e1 = Long.parseLong(tokenizer.nextToken());
			e2 = Long.parseLong(tokenizer.nextToken());
			// If v > u then emit (u,v)
			if (e1 < e2) {
				context.write(new LongWritable(e1), new LongWritable(e2));
			}
		}
	}
}