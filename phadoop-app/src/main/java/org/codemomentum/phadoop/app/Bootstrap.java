package org.codemomentum.phadoop.app;

import org.apache.hadoop.util.ProgramDriver;
import org.codemomentum.phadoop.core.utils.Constants;
import org.codemomentum.phadoop.core.utils.MRRegistry;
import org.codemomentum.phadoop.js.JSMapper;
import org.codemomentum.phadoop.js.JSReducer;
import org.codemomentum.phadoop.python.PythonMapper;
import org.codemomentum.phadoop.python.PythonReducer;

/**
 * @author Halit
 */
public class Bootstrap {

    static {
        MRRegistry.registerMapper(Constants.JS_EXT, JSMapper.class);
        MRRegistry.registerMapper(Constants.PYTHON_EXT, PythonMapper.class);

        MRRegistry.registerReducer(Constants.JS_EXT, JSReducer.class);
        MRRegistry.registerReducer(Constants.PYTHON_EXT, PythonReducer.class);
    }

    public static void main(String[] args) throws Throwable {
        int exitCode = -1;
        ProgramDriver pgd = new ProgramDriver();

        pgd.addClass("phadoop", PHadoopJob.class,
                "A map/reduce program that runs the provided Script files as Mapper & Reducer");
        pgd.driver(args);

        System.exit(exitCode);
    }

}
