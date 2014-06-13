package invertedFile.job2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// gets <title><word> <frequency> -> <title> <word><frequency>
public class IFWordDocFrequencyMapper extends Mapper<LongWritable, Text, Text, Text>{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String line = value.toString();
		String count = line.split("\t")[1];
		String title = line.split("\t")[0].split("@")[0];
		//String word = line.split("\t")[0].split("=")[1];
		context.write(new Text(title), new Text(title + "=" + count));
	}

}
