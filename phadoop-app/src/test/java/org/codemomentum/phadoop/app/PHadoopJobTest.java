package org.codemomentum.phadoop.app;

import org.codemomentum.phadoop.core.utils.MRRegistry;
import org.codemomentum.phadoop.js.JSMapper;
import org.codemomentum.phadoop.js.JSReducer;
import org.codemomentum.phadoop.python.PythonMapper;
import org.codemomentum.phadoop.python.PythonReducer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Halit
 */
@Ignore
public class PHadoopJobTest {
    @Before
    public void setUp() throws Exception {
        MRRegistry.flushRegistry();

        MRRegistry.registerMapper("js", JSMapper.class);
        MRRegistry.registerMapper("py", PythonMapper.class);

        MRRegistry.registerReducer("js", JSReducer.class);
        MRRegistry.registerReducer("py", PythonReducer.class);
    }

    @Test
    public void testBasicFlow() throws Exception {
        PHadoopJob hadoopJob=new PHadoopJob();
        hadoopJob.run(new String[]{"./example/mapper.js",
                "./example/reducer.py",
                "./example/input/",
                "./example/output"});

    }
}
