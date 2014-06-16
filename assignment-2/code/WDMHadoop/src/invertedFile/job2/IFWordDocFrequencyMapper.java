package invertedFile.job2;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// gets <title><word> <frequency> -> <title> <word><frequency>
public class IFWordDocFrequencyMapper extends Mapper<LongWritable, Text, Text, Text>{	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String[] wordAndDocCounter = value.toString().split("\t");
		String counter = wordAndDocCounter[1];
        String[] wordAndDoc = wordAndDocCounter[0].split("@");
        String word = wordAndDoc[0];
        String doc = wordAndDoc[1];
        context.write(new Text(doc), new Text(word + "=" + counter));

	}

}
