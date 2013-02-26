package org.codemomentum.phadoop.core;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author Halit
 */
public class BaseReducer extends Reducer<WritableComparable, Writable,
        WritableComparable, Writable> {
}
