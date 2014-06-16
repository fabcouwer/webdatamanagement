import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TriangleCountingJob {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		if (args.length != 2) {
			System.err.println("Usage: <in> <out>");
			System.exit(2);
		}

		@SuppressWarnings("deprecation")
		Job job1 = new Job(conf, "map1");
		job1.setJarByClass(TriangleCountingJob.class);

		/* Define the Mapper and the Reducer */
		job1.setMapperClass(TCMapperOne.class);
		job1.setReducerClass(TCReducerOne.class);

		/* Define the input and output types */
		job1.setInputFormatClass(TextInputFormat.class);
		job1.setOutputFormatClass(TextOutputFormat.class);

		job1.setMapOutputKeyClass(LongWritable.class);
		job1.setMapOutputValueClass(LongWritable.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(LongWritable.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path("temp1"));

		// Job2:

		// Job3:

	}

}