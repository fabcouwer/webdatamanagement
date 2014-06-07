package movies;

/**
 * Example of a simple MapReduce job: it reads 
 * file containing authors and publications, and 
 * produce each author with her publication count.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * The following class implements the Job submission, based on the Mapper
 * (MoviesMapper) and the Reducer (MoviesReducer)
 */
public class MoviesJob {

	public static void main(String[] args) throws Exception {

		/*
		 * Load the Hadoop configuration. IMPORTANT: the $HADOOP_HOME/conf
		 * directory must be in the CLASSPATH
		 */
		Configuration conf = new Configuration();

		/* We expect two arguments */

		if (args.length != 2) {
			System.err.println("Usage: MoviesJob <in> <out>");
			System.exit(2);
		}

		/* Alright, define and submit the job */
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "Movies title-actor and director-title");

		/* Define the Mapper and the Reducer */
		job.setMapperClass(MoviesMapper.class);
		// job.setCombinerClass(MoviesCombiner.class);
		job.setReducerClass(MoviesReducer.class);

		/* Define the output type */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
