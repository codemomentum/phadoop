package org.codemomentum.phadoop.js;

import com.sun.script.javascript.RhinoScriptEngineFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.codemomentum.phadoop.core.BaseReducer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Halit
 */
public class JSReducer extends BaseReducer {

    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineFactory factory=new RhinoScriptEngineFactory();
        return factory.getScriptEngine();
    }

}
