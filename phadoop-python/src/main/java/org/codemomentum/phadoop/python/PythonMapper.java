package org.codemomentum.phadoop.python;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.codemomentum.phadoop.core.BaseMapper;

/**
 * @author Halit
 */
public class PythonMapper extends BaseMapper {

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
}
