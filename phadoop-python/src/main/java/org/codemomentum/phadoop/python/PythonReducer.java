package org.codemomentum.phadoop.python;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.codemomentum.phadoop.core.BaseReducer;
import org.python.jsr223.PyScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Halit
 */
public class PythonReducer extends BaseReducer {
    Text key = new Text();

    Text value = new Text();

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
        ScriptEngineFactory factory = new PyScriptEngineFactory();
        return factory.getScriptEngine();
    }
}