package org.codemomentum.phadoop.js;

import com.sun.script.javascript.RhinoScriptEngineFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.codemomentum.phadoop.core.BaseMapper;
import org.codemomentum.phadoop.core.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.io.IOException;

/**
 * @author Halit
 */
public class JSMapper extends BaseMapper {

    @Override
    protected ScriptEngine getNewScriptEngine() {
        ScriptEngineFactory factory = new RhinoScriptEngineFactory();
        return factory.getScriptEngine();
    }
}
