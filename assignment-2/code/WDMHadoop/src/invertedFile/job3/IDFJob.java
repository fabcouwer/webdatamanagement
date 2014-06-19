package invertedFile.job3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IDFJob {
	private static final String INPUT_DIR = "output2";
	private static final String OUTPUT_DIR = "output3";

	/**
	 * @param args
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "TF - IDF 3");
		job.setJarByClass(IDFJob.class);

		/* Define the Mapper and the Reducer */
		job.setMapperClass(IDFMapper.class);
		// job.setCombinerClass(IFDocFrequencyReducer.class);
		job.setReducerClass(IDFReducer.class);

		/* Define the input and output types */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job, new Path(INPUT_DIR));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_DIR));

		/* Do it! */
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
