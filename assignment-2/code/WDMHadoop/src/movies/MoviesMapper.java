package movies;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MoviesMapper extends Mapper<Object, Text, Text, IntWritable> {

	// Do something

}