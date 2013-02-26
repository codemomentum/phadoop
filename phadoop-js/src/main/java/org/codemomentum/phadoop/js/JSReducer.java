package org.codemomentum.phadoop.js;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;
import org.codemomentum.phadoop.core.BaseReducer;
import sun.org.mozilla.javascript.internal.NativeArray;

import java.io.IOException;

/**
 * @author Halit
 */
public class JSReducer extends BaseReducer {
    Text key=new Text();

    Text value=new Text();

    @Override
    protected WritableComparable getKey() {
        return key;
    }

    @Override
    protected Writable getValue() {
        return value;
    }

    @Override
    public void reduce(WritableComparable key, Iterable<Writable> values, Context context) throws IOException, InterruptedException {
        //NativeArray nativeArray=new NativeArray()
        //this would be very sad


        super.reduce(key, values, context);
    }
}
