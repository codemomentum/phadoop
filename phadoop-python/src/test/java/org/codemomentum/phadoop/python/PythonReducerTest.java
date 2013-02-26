package org.codemomentum.phadoop.python;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.codemomentum.phadoop.core.io.ScriptReader;
import org.codemomentum.phadoop.core.utils.MRRegistry;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

import static org.codemomentum.phadoop.core.utils.Constants.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * @author Halit
 */
public class PythonReducerTest {
    PythonReducer reducer;
    Text key = new Text();
    Text value1 = new Text();
    Text value2 = new Text();
    List<Writable> values;
    Reducer.Context mockContext;

    Configuration configuration;
    String scriptFileName="reducer.py";

    ClassPathResource classPathResource;

    @Before
    public void setUp() throws Exception {
        MRRegistry.flushRegistry();
        MRRegistry.registerReducer(REDUCER, PythonReducer.class);

        reducer = new PythonReducer();

        key.set("key");
        value1.set("value1");
        value2.set("value2");

        values=new ArrayList<Writable>();
        values.add(value1);
        values.add(value2);

        mockContext=mock(Reducer.Context.class);


        configuration=new Configuration();

        ScriptReader scriptReader=new ScriptReader();

        classPathResource=new ClassPathResource(scriptFileName);

        String reducerScriptAsString = scriptReader.readString(classPathResource.getFile().getAbsolutePath());
        configuration.set(REDUCER_SCRIPT, reducerScriptAsString);
        String reducerExtension= FilenameUtils.getExtension(classPathResource.getFile().getAbsolutePath());
        configuration.set(REDUCER_EXTENSION, reducerExtension);
    }

    @Test
    public void testBasicFlow() throws Exception {
        when(mockContext.getConfiguration()).thenReturn(configuration);

        reducer.setup(mockContext);
        reducer.reduce(key, values, mockContext);


        verify(mockContext,times(2)).getConfiguration();
        verify(mockContext,times(2)).write(isA(Text.class),isA(Text.class));

        verifyNoMoreInteractions(mockContext);
    }
}
