package org.codemomentum.phadoop.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;

/**
 * @author Halit
 */
public class ScriptEngineHelper {

    final static Logger logger = LoggerFactory.getLogger(ScriptEngineHelper.class);

    /**
     * Call a function on the Script Engine.
     */
    public static Object callFunction(ScriptEngine scriptEngine, String functionName, Object... args) {

        try {
            return ((Invocable) scriptEngine)
                    .invokeFunction(functionName, args);
        } catch (Exception e) {
            logger.error("Error Executing Function from utils:", e);
            throw new RuntimeException("Error Executing Function from utils:", e);
        }
    }

}
