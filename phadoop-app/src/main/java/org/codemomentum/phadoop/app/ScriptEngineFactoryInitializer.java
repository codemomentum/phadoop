package org.codemomentum.phadoop.app;

import org.codemomentum.phadoop.core.utils.Constants;
import org.python.jsr223.PyScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * @author Halit
 *         <p/>
 *         TODO: duplicate
 */
public class ScriptEngineFactoryInitializer {

    final static Logger logger = LoggerFactory.getLogger(ScriptEngineFactoryInitializer.class);

    public static ScriptEngine getScriptEngineInstanceByExtension(String extension) {
        if (extension.equals(Constants.JS_EXT)) {
            ScriptEngineManager factory = new ScriptEngineManager();
            return factory.getEngineByName("JavaScript");
        } else if (extension.equals(Constants.PYTHON_EXT)) {
            ScriptEngineFactory factory = new PyScriptEngineFactory();
            return factory.getScriptEngine();
        } else {
            logger.error("Unknown Extension: {}", extension);
            throw new RuntimeException("Unknown Extension:" + extension);
        }
    }
}
