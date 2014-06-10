package invertedFile.job1;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class IFDocFrequencyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		SAXBuilder builder = new SAXBuilder();
		Text word = new Text();
		// TODO get all text from the summary, not just the first word
		try {
			Document d = builder.build(new StringReader(value.toString()));
			Element movie = d.getRootElement();

			// Get movie information
			String movieTitle = movie.getChildText("title");
			String movieSummary = movie.getChildText("summary");
			if(movieSummary!=null){
				Pattern p = Pattern.compile("\\w+");
		        Matcher m = p.matcher(movieSummary.toString());
				//Scanner line = new Scanner(movieSummary);
				//line.useDelimiter(" ");
				// TODO should we do this in a loop???
				while(m.find()){
					word.set(movieTitle + "\t" + m.group().toLowerCase());
					context.write(word, new IntWritable(1));
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}