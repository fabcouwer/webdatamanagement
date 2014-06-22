package invertedFile.job3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class IDFReducer extends Reducer<Text, Text, Text, DoubleWritable> {
	//retrieved from first map reduce phase
	//private final static int filecount = 2553589;
	//for test wiki file
	private final static int filecount = 1000;

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Map<String, String> frequencies = new HashMap<String, String>();
		int totalcount = 0;
		// count the number of documents that have a certain word in it
		for (Text value : values) {
			String[] line = value.toString().split("=");
			int count = Integer.parseInt(line[1].split("/")[0]);
			if (count > 0) {
				totalcount++;
			}
			frequencies.put(line[0], line[1]);
		}
		for (String doc : frequencies.keySet()) {
			int doccount = Integer.parseInt(frequencies.get(doc).split("/")[0]);
			int total = Integer.parseInt(frequencies.get(doc).split("/")[1]);

			//Calculate TF-IDF
			double tf = calculateTF(doccount, total);
			double idf = calculateIDF(filecount, totalcount);
			double tfidf = tf * idf;

			//Write results to output
			context.write(new Text(key + " - " + doc),
					new DoubleWritable(tfidf));
		}
	}

	// Calculate TF by dividing number of times term occurs by number of terms
	public double calculateTF(int term, int count) {
		return Double.valueOf(term) / Double.valueOf(count);
	}

	// Calculate IDF by dividing 
	public double calculateIDF(int filecount, int count) {
		double a = (double) filecount;
		double b;
		if (count == 0) {
			b = count + 1.0;
		} else {
			b = (double) count;
		}
		return Math.log10(a / b);
	}
}
