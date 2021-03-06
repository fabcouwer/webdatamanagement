package movies;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * The following class implements the Job submission, based on the Mapper
 * (MoviesMapper) and the Reducer (MoviesReducer)
 */
public class MoviesJob {

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
		Job job = new Job(conf, "Movie title-actor-director");
		job.setJarByClass(MoviesJob.class);

		/* Define the Mapper and the Reducer */
		job.setMapperClass(MoviesMapper.class);
		// job.setCombinerClass(MoviesCombiner.class);
		job.setReducerClass(MoviesReducer.class);

		/* Define the input and output types */
		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		/* Set multiple outputs (2 files in this case) */
		MultipleOutputs.addNamedOutput(job, "titleAndActor",
				TextOutputFormat.class, NullWritable.class, Text.class);
		MultipleOutputs.addNamedOutput(job, "directorAndTitle",
				TextOutputFormat.class, NullWritable.class, Text.class);

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
