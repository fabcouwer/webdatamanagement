import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.e.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

import java.io.IOException;


public class TriangleCounting extends BasicComputation<
    DoubleWritable, DoubleWritable, FloatWritable, DoubleWritable> {

  @Override
  public void compute(
      Vertex<DoubleWritable, DoubleWritable, FloatWritable> v,
      Iterable<DoubleWritable> messages) throws IOException {

  // For all neighbors N of V, if V<N send V to N
  if (getSuperstep() == 0) {
    for (Edge<DoubleWritable, FloatWritable> e: v.getEdges()) {
      if (v.getId().compareTo(e.getTargetVertexId()) == -1) {
        sendMessage(e.getTargetVertexId(), v.getId());
      }
    }
  }

  // For all messages M, do:
  // For all neighbors N of V, if M < V < N send M to N
  if (getSuperstep() == 1) {
    for (DoubleWritable message: messages) {
        for (Edge<DoubleWritable, FloatWritable> e: v.getEdges()) {
          if (m.compareTo(v.getId()) == -1 && 
		  v.getId().compareTo(e.getTargetVertexId()) == -1) {
            sendMessage(e.getTargetVertexId(), message);
          }
        }
    }
  }
  /** Sends messages to all its neighbours, the messages it receives **/
  if (getSuperstep() == 2) {
    for (DoubleWritable message: messages) {
        sendMessageToAllEdges(v, message);
    }
  }

  if (getSuperstep() == 3) {
      double val = 0.0;
      for (DoubleWritable message: messages) {
          if (v.getId().equals(message)) {
              val += 1.0;
          }
      }
      v.setValue(new DoubleWritable(val));
  }

  v.voteToHalt();
  }
}