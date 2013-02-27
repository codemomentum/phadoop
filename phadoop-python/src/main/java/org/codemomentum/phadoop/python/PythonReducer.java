package org.codemomentum.phadoop.python;

import org.codemomentum.phadoop.core.BaseReducer;
import org.python.jsr223.PyScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Halit
 */
public class PythonReducer extends BaseReducer {
    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineFactory factory = new PyScriptEngineFactory();
        return factory.getScriptEngine();
    }
}