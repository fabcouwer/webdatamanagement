import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TCReducerOne extends
		Reducer<LongWritable, LongWritable, Text, LongWritable> {
	Text rKey = new Text();
	final static LongWritable zero = new LongWritable((byte) 0);
	final static LongWritable one = new LongWritable((byte) 1);
	long[] vArray = new long[4096];
	int size = 0;

	public void reduce(LongWritable key, Iterable<LongWritable> values,
			Context context) throws IOException, InterruptedException {
		// for(u,w): u,w in S
		// emit {v;(u,w)}

		Iterator<LongWritable> vs = values.iterator();
		for (size = 0; vs.hasNext();) {
			if (vArray.length == size) {
				vArray = Arrays.copyOf(vArray, vArray.length * 2);
			}

			long e = vs.next().get();
			vArray[size++] = e;

			// emit v
			rKey.set(key.toString() + "," + Long.toString(e));
			context.write(rKey, zero);
		}

		Arrays.sort(vArray, 0, size);

		// emit (u,w)
		for (int i = 0; i < size; ++i) {
			for (int j = i + 1; j < size; ++j) {
				rKey.set(Long.toString(vArray[i]) + ","
						+ Long.toString(vArray[j]));
				context.write(rKey, one);
			}
		}
	}
}
