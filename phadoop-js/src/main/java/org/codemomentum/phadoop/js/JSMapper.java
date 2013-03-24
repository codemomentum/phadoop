package org.codemomentum.phadoop.js;


import org.codemomentum.phadoop.core.BaseMapper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Halit
 */
public class JSMapper extends BaseMapper {

    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        return factory.getEngineByName("JavaScript");
    }
}
