package movies;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class MoviesMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

	private final static IntWritable actorKey = new IntWritable(1);
	private final static IntWritable directorKey = new IntWritable(2);

	// For each XML file, retrieve relevant information using JDOM.
	// Based on tutorials at:
	// http://www.studytrails.com/java/xml/jdom2/java-xml-jdom2-introduction.jsp
	@SuppressWarnings("unchecked")
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		SAXBuilder builder = new SAXBuilder();

		try {
			Document d = builder.build(value.toString());
			Element movie = d.getRootElement();

			// Get movie information
			String movieTitle = movie.getChildText("title");
			String movieYear = movie.getChildText("year");

			// Build title-actor output line for every actor
			List<Element> actors = movie.getChildren("actor");
			for (Element actor : actors) {
				String actorOutput = movieTitle + "\t";

				// Add actor information
				actorOutput += actor.getChildText("first_name") + " "
						+ actor.getChildText("last_name") + "\t";
				actorOutput += actor.getChildText("birth_date") + "\t"
						+ actor.getChildText("role");

				context.write(actorKey, new Text(actorOutput));
			}

			Element director = movie.getChild("director");

			// Build director-title output line
			String directorOutput = director.getChildText("first_name") + " "
					+ director.getChildText("last_name") + "\t";

			// Add movie information
			directorOutput += movieTitle + "\t" + movieYear;

			context.write(directorKey, new Text(directorOutput));

		} catch (JDOMException e) {
			e.printStackTrace();
		}

	}
}