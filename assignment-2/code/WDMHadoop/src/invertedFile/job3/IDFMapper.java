package invertedFile.job3;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class IDFMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String[] line = value.toString().split("\t");
		String doc = line[0].split("@")[0];
		String word = line[0].split("@")[1];
		context.write(new Text(word), new Text(doc+"="+line[1]));
	}
}
