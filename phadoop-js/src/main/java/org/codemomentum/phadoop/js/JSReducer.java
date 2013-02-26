package org.codemomentum.phadoop.js;

import com.sun.script.javascript.RhinoScriptEngineFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;
import org.codemomentum.phadoop.core.BaseReducer;
import sun.org.mozilla.javascript.internal.NativeArray;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
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
    protected ScriptEngine getScriptEngine() {
        ScriptEngineFactory factory=new RhinoScriptEngineFactory();
        return factory.getScriptEngine();
    }

}
