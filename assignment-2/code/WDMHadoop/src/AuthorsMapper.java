import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AuthorsMapper extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable one = new IntWritable(1);
	private Text author = new Text();

	public void map(Object key, Text value, Context context)
		throws IOException, InterruptedException {

	  /* Open a Java scanner object to parse the line */
	  Scanner line = new Scanner(value.toString());
	  line.useDelimiter("\t");
	  author.set(line.next());
	  context.write(author, one);
	}
  }