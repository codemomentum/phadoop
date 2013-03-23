package org.codemomentum.phadoop.app;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.codemomentum.phadoop.core.io.ScriptReader;
import org.codemomentum.phadoop.core.utils.Constants;
import org.codemomentum.phadoop.core.utils.MRRegistry;

import javax.script.ScriptEngine;


/**
 * @author Halit
 */
public class PHadoopJob extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new PHadoopJob(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 4) {
            String usage = "phadoop <mapperFileName> <reducerFileName> <inDir> <outDir>";
            System.out.println(usage);
            ToolRunner.printGenericCommandUsage(System.out);
            System.exit(1);
        }

        PHadoopJob pHadoopJob = new PHadoopJob();
        String mapperFileName = args[0];
        String reducerFileName = args[1];
        String inDir = args[2];
        String outDir = args[3];
        pHadoopJob.startJob(mapperFileName, reducerFileName, inDir, outDir);
        return 0;
    }

    private void startJob(String mapperFileName, String reducerFileName, String inDir, String outDir) throws Exception {
        Configuration config = new Configuration();
        ScriptReader scriptReader = new ScriptReader();

        //beware of the naming convention
        String mapperScriptAsString = scriptReader.readString(mapperFileName);
        config.set(Constants.MAPPER_SCRIPT, mapperScriptAsString);
        String mapperExtension = FilenameUtils.getExtension(mapperFileName);
        config.set(Constants.MAPPER_EXTENSION, mapperExtension);


        String reducerScriptAsString = scriptReader.readString(reducerFileName);
        config.set(Constants.REDUCER_SCRIPT, reducerScriptAsString);
        String reducerExtension = FilenameUtils.getExtension(reducerFileName);
        config.set(Constants.REDUCER_EXTENSION, reducerExtension);

        Job job = new Job(config);
        job.setJarByClass(PHadoopJob.class);
        job.setJobName(String.format("PHadoopJob MAP: %s, RED: %s, IN: %s, OUT: %s", mapperFileName, reducerFileName, inDir, outDir));

        job.setMapperClass(MRRegistry.getRegisteredMapper(mapperExtension));
        job.setReducerClass(MRRegistry.getRegisteredReducer(reducerExtension));


        FileInputFormat.addInputPath(job, new Path(inDir));
        FileOutputFormat.setOutputPath(job, new Path(outDir));


        ScriptEngine seMapper = ScriptEngineFactoryInitializer.getScriptEngineInstanceByExtension(mapperExtension);
        seMapper.eval(mapperScriptAsString);


        //now we need the Script engine to evaluate InputFormatClass and OutputFormatClass
        Object ifClassName = seMapper.get(Constants.INPUT_FORMAT);
        if (null != ifClassName) {
            job.setInputFormatClass((Class<? extends InputFormat>) Class.forName(ifClassName.toString()));
        } else {
            job.setInputFormatClass(TextInputFormat.class);
        }

        Object ofClassName = seMapper.get(Constants.OUTPUT_FORMAT);
        if (null != ifClassName) {
            job.setOutputFormatClass((Class<? extends OutputFormat>) Class.forName(ofClassName.toString()));
        } else {
            job.setOutputFormatClass(TextOutputFormat.class);
        }


        //now we need the Script engine to evaluate MapOutputKeyClass and MapOutputValueClass
        Object mapperOutputKey = seMapper.get(Constants.MAPPER_OUTPUT_KEY);
        job.setMapOutputKeyClass(null == mapperOutputKey ? Text.class : mapperOutputKey.getClass());

        Object mapperOutputValue = seMapper.get(Constants.MAPPER_OUTPUT_VALUE);
        job.setMapOutputValueClass(null == mapperOutputValue ? Text.class : mapperOutputValue.getClass());


        job.waitForCompletion(true);
    }

    public String startOnCluster(String mapperScriptAsString, String mapperExtension,
                                  String reducerScriptAsString, String reducerExtension,
                                  String inDir, String outDir) throws Exception {
        Configuration config = new Configuration();
        ScriptReader scriptReader = new ScriptReader();

        //beware of the naming convention
        config.set(Constants.MAPPER_SCRIPT, mapperScriptAsString);
        config.set(Constants.MAPPER_EXTENSION, mapperExtension);


        config.set(Constants.REDUCER_SCRIPT, reducerScriptAsString);
        config.set(Constants.REDUCER_EXTENSION, reducerExtension);

        Job job = new Job(config);
        job.setJarByClass(PHadoopJob.class);
        job.setJobName(String.format("PHadoopJob MAP: %s, RED: %s, IN: %s, OUT: %s", mapperExtension, reducerExtension, inDir, outDir));

        job.setMapperClass(MRRegistry.getRegisteredMapper(mapperExtension));
        job.setReducerClass(MRRegistry.getRegisteredReducer(reducerExtension));


        FileInputFormat.addInputPath(job, new Path(inDir));
        FileOutputFormat.setOutputPath(job, new Path(outDir));


        ScriptEngine seMapper = ScriptEngineFactoryInitializer.getScriptEngineInstanceByExtension(mapperExtension);
        seMapper.eval(mapperScriptAsString);


        //now we need the Script engine to evaluate InputFormatClass and OutputFormatClass
        Object ifClassName = seMapper.get(Constants.INPUT_FORMAT);
        if (null != ifClassName) {
            job.setInputFormatClass((Class<? extends InputFormat>) Class.forName(ifClassName.toString()));
        } else {
            job.setInputFormatClass(TextInputFormat.class);
        }

        Object ofClassName = seMapper.get(Constants.OUTPUT_FORMAT);
        if (null != ifClassName) {
            job.setOutputFormatClass((Class<? extends OutputFormat>) Class.forName(ofClassName.toString()));
        } else {
            job.setOutputFormatClass(TextOutputFormat.class);
        }


        //now we need the Script engine to evaluate MapOutputKeyClass and MapOutputValueClass
        Object mapperOutputKey = seMapper.get(Constants.MAPPER_OUTPUT_KEY);
        job.setMapOutputKeyClass(null == mapperOutputKey ? Text.class : mapperOutputKey.getClass());

        Object mapperOutputValue = seMapper.get(Constants.MAPPER_OUTPUT_VALUE);
        job.setMapOutputValueClass(null == mapperOutputValue ? Text.class : mapperOutputValue.getClass());


        job.submit();

        return job.getTrackingURL();
    }
}

