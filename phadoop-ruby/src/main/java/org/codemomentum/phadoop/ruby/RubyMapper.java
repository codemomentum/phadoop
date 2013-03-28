package org.codemomentum.phadoop.ruby;

import org.codemomentum.phadoop.core.BaseMapper;
import org.jruby.embed.jsr223.JRubyEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Halit
 */
public class RubyMapper extends BaseMapper {
    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineFactory factory=new JRubyEngineFactory();
        return factory.getScriptEngine();
    }

}