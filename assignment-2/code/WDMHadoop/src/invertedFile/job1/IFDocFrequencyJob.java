package invertedFile.job1;

import movies.XmlInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IFDocFrequencyJob{
	public static void main(String[] args) throws Exception {

		// Load the Hadoop configuration.
		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<movie>");
		conf.set("xmlinput.end", "</movie>");

		if (args.length != 2) {
			System.err.println("Usage: <in> <out>");
			System.exit(2);
		}

		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "word frequency");
		job.setJarByClass(IFDocFrequencyJob.class);

		/* Define the Mapper and the Reducer */
		job.setMapperClass(IFDocFrequencyMapper.class);
		// job.setCombinerClass(IFDocFrequencyReducer.class);
		job.setReducerClass(IFDocFrequencyReducer.class);

		/* Define the input and output types */
		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}