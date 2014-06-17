package invertedFile;

import invertedFile.job1.IFDocFrequencyJob;
import invertedFile.job1.IFDocFrequencyMapper;
import invertedFile.job1.IFDocFrequencyReducer;
import invertedFile.job2.IFWordDocFrequencyJob;
import invertedFile.job2.IFWordDocFrequencyMapper;
import invertedFile.job2.IFWordDocFrequencyReducer;
import invertedFile.job3.IDFJob;
import invertedFile.job3.IDFMapper;
import invertedFile.job3.IDFReducer;
import movies.XmlInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IFJob{
	private static final String TEMP_DIR1 = "temp1";
	private static final String TEMP_DIR2 = "temp2";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		// ** Job1 ***
		// Load the Hadoop configuration.
		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<movie>");
		conf.set("xmlinput.end", "</movie>");

		if (args.length != 2) {
			System.err.println("Usage: <in> <out>");
			System.exit(2);
		}
		Job job1 = new Job(conf, "TF - IDF 1");
		job1.setJarByClass(IFDocFrequencyJob.class);

		/* Define the Mapper and the Reducer */
		job1.setMapperClass(IFDocFrequencyMapper.class);
		job1.setCombinerClass(IFDocFrequencyReducer.class);//Combiner is the same as the reducer
		job1.setReducerClass(IFDocFrequencyReducer.class);

		/* Define the input and output types */
		job1.setInputFormatClass(XmlInputFormat.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(TEMP_DIR1));
		job1.waitForCompletion(true);
		
		/* Do it! */
		job1.submit();
		
		// ** Job2 ***
		Configuration conf2 = new Configuration();
		Job job2 = new Job(conf2, "TF - IDF 2");
		job2.setJarByClass(IFWordDocFrequencyJob.class);

		/* Define the Mapper and the Reducer */
		job2.setMapperClass(IFWordDocFrequencyMapper.class);
		// job.setCombinerClass(IFDocFrequencyReducer.class);
		job2.setReducerClass(IFWordDocFrequencyReducer.class);

		/* Define the input and output types */
		//job.setInputFormatClass(XmlInputFormat.class);
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job2, new Path(TEMP_DIR1));
		FileOutputFormat.setOutputPath(job2, new Path(TEMP_DIR2));
		
		job1.waitForCompletion(true);
		/* Do it! */
		job2.submit();
		
		// *** job 3 ***
		Configuration conf3 = new Configuration();
		Job job3 = new Job(conf3, "TF - IDF 3");
		job3.setJarByClass(IDFJob.class);

		/* Define the Mapper and the Reducer */
		job3.setMapperClass(IDFMapper.class);
		// job.setCombinerClass(IFDocFrequencyReducer.class);
		job3.setReducerClass(IDFReducer.class);

		/* Define the input and output types */
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);

		/* Set the input and the output */
		FileInputFormat.addInputPath(job3, new Path(TEMP_DIR2));
		FileOutputFormat.setOutputPath(job3, new Path(args[1]));
		
		/* Do it! */
		job3.submit();
		
	}
}