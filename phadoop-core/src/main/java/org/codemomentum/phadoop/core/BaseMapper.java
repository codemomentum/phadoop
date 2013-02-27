package org.codemomentum.phadoop.core;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.codemomentum.phadoop.core.utils.Constants;
import org.codemomentum.phadoop.core.utils.ScriptEngineHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

import static org.codemomentum.phadoop.core.utils.Constants.*;

/**
 * @author Halit
 */
public abstract class BaseMapper extends Mapper<WritableComparable, Writable,
        WritableComparable, Writable> {

    final static Logger logger = LoggerFactory.getLogger(BaseMapper.class);


    protected ScriptEngine scriptEngine;

    protected ScriptEngineManager scriptEngineManager;

    //compile the utils
    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        scriptEngineManager = new ScriptEngineManager();
        //get utils String and extension
        //instantiate the utils engine manager
        scriptEngine = scriptEngineManager.getEngineByExtension(context.getConfiguration().get(MAPPER_EXTENSION));
        if(null==scriptEngine) {
            scriptEngine=getNewScriptEngine();
        }
        //eval
        try {
            scriptEngine.eval(context.getConfiguration().get(MAPPER_SCRIPT));
            scriptEngine.put("_key",getKey());
            scriptEngine.put("_value",getValue());
        } catch (ScriptException e) {
            logger.error("Error evaluating script: ", e);
            throw new RuntimeException("Error evaluating script: ", e);
        }
    }

    //delegate to the utils
    @Override
    public void map(WritableComparable key, Writable value, Context context) throws IOException, InterruptedException {
        ScriptEngineHelper.callFunction(scriptEngine, "map", key, value, context);
    }

    //release any resources if possible
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
    }

    protected WritableComparable getKey() {
        Object keyFromScript = getScriptEngine().get(Constants.MAPPER_OUTPUT_KEY);
        if (keyFromScript instanceof WritableComparable) {
            return (WritableComparable) keyFromScript;
        } else {
            logger.error("The key from script is not an instance of WritableComparable: {}", keyFromScript);
            throw new RuntimeException("The key from script is not an instance of WritableComparable");
        }
    }

    protected Writable getValue() {
        Object valueFromString = getScriptEngine().get(Constants.MAPPER_OUTPUT_VALUE);
        if (valueFromString instanceof Writable) {
            return (Writable) valueFromString;
        } else {
            logger.error("The value from script is not an instance of Writable: {}", valueFromString);
            throw new RuntimeException("The value from script is not an instance of Writable");
        }
    }

    protected abstract ScriptEngine getNewScriptEngine();

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }
}
