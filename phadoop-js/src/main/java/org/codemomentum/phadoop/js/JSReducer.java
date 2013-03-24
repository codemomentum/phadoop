package org.codemomentum.phadoop.js;

import org.codemomentum.phadoop.core.BaseReducer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Halit
 */
public class JSReducer extends BaseReducer {

    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        return factory.getEngineByName("JavaScript");
    }

}
