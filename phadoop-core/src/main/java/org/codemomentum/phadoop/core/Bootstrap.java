package org.codemomentum.phadoop.core;

import org.apache.hadoop.util.ProgramDriver;

/**
 * @author Halit
 */
public class Bootstrap {

    public static void main(String[] args) throws Throwable {
        int exitCode = -1;
        ProgramDriver pgd = new ProgramDriver();

        pgd.addClass("js-runner", GenericJob.class,
                "A map/reduce program that runs the provided JavaScript file as Mapper & Reducer");
        pgd.driver(args);

        System.exit(exitCode);
    }

}
