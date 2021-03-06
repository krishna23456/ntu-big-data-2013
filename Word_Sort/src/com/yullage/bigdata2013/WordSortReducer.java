package com.yullage.bigdata2013;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * A word sort reducer implementation.
 * 
 * @author Steven Huang
 * @version 1.0b
 * 
 */
public class WordSortReducer extends MapReduceBase implements
		Reducer<Text, IntWritable, Text, IntWritable> {
	@Override
	public void reduce(Text key, Iterator<IntWritable> values,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		while (values.hasNext()) {
			output.collect(key, values.next());
		}
	}
}
