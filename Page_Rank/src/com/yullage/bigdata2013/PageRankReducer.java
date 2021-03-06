package com.yullage.bigdata2013;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * A page rank reducer implementation.
 * 
 * @author Steven Huang
 * @version 1.0b
 * 
 */
public class PageRankReducer extends MapReduceBase implements
		Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		String outLinks = "";
		double newRank = 0;

		while (values.hasNext()) {
			String line = values.next().toString();

			// If a line starts with OUT_LINK_FLAG, it represents the original
			// out
			// links of the page.
			if (line.startsWith(PageRank.OUT_LINK_FLAG)) {
				outLinks = line.substring(2);
				continue;
			}

			String[] tokens = line.split("\\s+");
			double rankFromOtherPage = Double.parseDouble(tokens[0])
					/ Double.parseDouble(tokens[1]);
			newRank += rankFromOtherPage;
		}

		newRank = (1 - PageRank.DAMPING) + PageRank.DAMPING * newRank;
		Text v = new Text(Double.toString(newRank) + "\t" + outLinks);
		output.collect(key, v);
	}

}
