package org.codemomentum.phadoop.core.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IOUtils;

/**
 * @author Halit
 */
public class ScriptReader {

    public String readString(String filePath) throws IOException {
        if (filePath.startsWith("hdfs")) {
            return readStringFromDFS(filePath);
        } else {
            return readStringFromFile(filePath);
        }
    }

    private String readStringFromFile(String fileName) throws IOException {
        String body = FileUtils.readFileToString(new File(fileName));
        if (null == body) {
            throw new RuntimeException("Could not read script file from given path: " + fileName);
        }
        return body;
    }

    private String readStringFromDFS(String fileName) throws IOException {
        return readStringFromDFS(fileName, new Configuration());
    }

    private String readStringFromDFS(String fileName, Configuration configuration) throws IOException {
        Path p = new Path(fileName);

        FSDataInputStream dis = null;

        FileSystem fs = DistributedFileSystem.get(configuration);

        try {
            dis = fs.open(p);
            return org.apache.commons.io.IOUtils.toString(dis);
        } finally {
            IOUtils.closeStream(dis);
        }
    }
}
