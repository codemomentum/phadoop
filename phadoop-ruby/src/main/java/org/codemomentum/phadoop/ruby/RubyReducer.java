package org.codemomentum.phadoop.ruby;

import org.codemomentum.phadoop.core.BaseReducer;
import org.jruby.embed.jsr223.JRubyEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Halit
 */
public class RubyReducer extends BaseReducer {
    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineFactory factory = new JRubyEngineFactory();
        return factory.getScriptEngine();
    }
}
