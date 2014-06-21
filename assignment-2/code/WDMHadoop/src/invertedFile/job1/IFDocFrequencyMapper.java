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

public class IFDocFrequencyMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		SAXBuilder builder = new SAXBuilder();
		try {
			Document d = builder.build(new StringReader(value.toString()));
			Element el = d.getRootElement();

			// Get movie information
			String title = el.getChildText("title");
			String text = el.getChild("revision").getChildText("text");
			if (text != null) {
				Pattern p = Pattern.compile("\\w+");//only words[a-zA-Z]+
				Matcher m = p.matcher(text.toString());
				// while we find words map them to <title>, <word><1>
				while (m.find()) {
					String word = m.group().toLowerCase();
					context.write(new Text(title + "@" + word),
							new IntWritable(1));
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}