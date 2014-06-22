package invertedFile.job1;

import movies.XmlInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * This job should get a movie xml file as input and gives as output the amount
 * of times a word was found in the summary
 * 
 * @input movies.xml
 * @output key:<title>@<word> value:<wordcount>
 * 
 */
public class IFDocFrequencyJob {

	private static final String OUTPUT_DIR = "temp1";

	public static void main(String[] args) throws Exception {

		// Load the Hadoop configuration.
		// each map is over one page in the wikipedia file
		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<page>");
		conf.set("xmlinput.end", "</page>");

		if (args.length != 1) {
			System.err.println("Usage: <in>");
			System.exit(2);
		}

		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "TF - IDF 1");
		job.setJarByClass(IFDocFrequencyJob.class);

		/* Define the Mapper, Combiner and the Reducer */
		job.setMapperClass(IFDocFrequencyMapper.class);
		job.setCombinerClass(IFDocFrequencyReducer.class);
		job.setReducerClass(IFDocFrequencyReducer.class);

		/* Define the input and output types */
		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_DIR));

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}