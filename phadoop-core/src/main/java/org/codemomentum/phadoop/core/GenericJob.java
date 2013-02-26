package org.codemomentum.phadoop.core;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.codemomentum.phadoop.core.io.ScriptReader;
import org.codemomentum.phadoop.core.utils.MRRegistry;

import static org.codemomentum.phadoop.core.utils.Constants.*;

/**
 * @author Halit
 */
public class GenericJob extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new GenericJob(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 4) {
            String usage = "generic-job <mapperFileName> <reducerFileName> <inDir> <outDir>";
            System.out.println(usage);
            ToolRunner.printGenericCommandUsage(System.out);
            System.exit(1);
        }

        GenericJob genericJob = new GenericJob();
        String mapperFileName = args[0];
        String reducerFileName = args[1];
        String inDir = args[2];
        String outDir = args[3];
        genericJob.startJob(mapperFileName, reducerFileName, inDir, outDir);
        return 0;
    }

    private void startJob(String mapperFileName, String reducerFileName, String inDir, String outDir) throws Exception{
        Configuration config = getConf();
        ScriptReader scriptReader=new ScriptReader();

        //beware of the naming convention
        String mapperScriptAsString = scriptReader.readString(mapperFileName);
        config.set(MAPPER_SCRIPT, mapperScriptAsString);
        String mapperExtension= FilenameUtils.getExtension(mapperFileName);
        config.set(MAPPER_EXTENSION, mapperExtension);


        String reducerScriptAsString = scriptReader.readString(reducerFileName);
        config.set(REDUCER_SCRIPT, reducerScriptAsString);
        String reducerExtension=FilenameUtils.getExtension(reducerFileName);
        config.set(REDUCER_EXTENSION, reducerExtension);

        Job job = new Job(new JobConf());

        job.setMapperClass(MRRegistry.getRegisteredMapper(MAPPER));
        job.setReducerClass(MRRegistry.getRegisteredReducer(REDUCER));

        FileInputFormat.addInputPath(job,new Path(inDir));
        job.setInputFormatClass(TextInputFormat.class);

        FileOutputFormat.setOutputPath(job,new Path(outDir));
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setJarByClass(GenericJob.class);


        job.waitForCompletion(true);
    }
}
