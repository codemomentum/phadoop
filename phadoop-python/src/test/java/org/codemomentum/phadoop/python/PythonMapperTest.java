package org.codemomentum.phadoop.python;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codemomentum.phadoop.core.io.ScriptReader;
import org.codemomentum.phadoop.core.utils.MRRegistry;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import static org.codemomentum.phadoop.core.utils.Constants.*;
import static org.mockito.Mockito.*;

/**
 * @author Halit
 */
public class PythonMapperTest {

    PythonMapper mapper;
    Text key = new Text();
    Text value = new Text();
    Mapper.Context mockContext;

    Configuration configuration;
    String mapperFileName="mapper.py";

    ClassPathResource classPathResource;

    @Before
    public void setUp() throws Exception {
        MRRegistry.flushRegistry();

        MRRegistry.registerMapper(MAPPER, PythonMapper.class);

        mapper = new PythonMapper();

        key.set("my key");
        value.set("my value");

        mockContext=mock(Mapper.Context.class);


        configuration=new Configuration();

        ScriptReader scriptReader=new ScriptReader();

        classPathResource=new ClassPathResource(mapperFileName);

        String mapperScriptAsString = scriptReader.readString(classPathResource.getFile().getAbsolutePath());
        configuration.set(MAPPER_SCRIPT, mapperScriptAsString);
        String mapperExtension= FilenameUtils.getExtension(classPathResource.getFile().getAbsolutePath());
        configuration.set(MAPPER_EXTENSION, mapperExtension);
    }

    @Test
    public void testBasicFlow() throws Exception {
        when(mockContext.getConfiguration()).thenReturn(configuration);

        mapper.setup(mockContext);
        mapper.map(key,value,mockContext);


        verify(mockContext,atMost(2)).getConfiguration();
        verify(mockContext).write(isA(Text.class),isA(Text.class));

        verifyNoMoreInteractions(mockContext);
    }
}
