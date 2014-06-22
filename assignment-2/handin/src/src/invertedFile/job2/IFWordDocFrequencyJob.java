package invertedFile.job2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 
 * @input key:<title>@<word> value:<wordcount>
 * @output key:<title> value:<totalwordcount>
 * */
public class IFWordDocFrequencyJob {
	private static final String INPUT_DIR = "temp1";
	private static final String OUTPUT_DIR = "temp2";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "TF - IDF 2");
		job.setJarByClass(IFWordDocFrequencyJob.class);

		/* Define the Mapper and the Reducer */
		job.setMapperClass(IFWordDocFrequencyMapper.class);
		job.setReducerClass(IFWordDocFrequencyReducer.class);

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
