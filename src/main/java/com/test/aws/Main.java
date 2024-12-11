package com.test.aws;

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class Main {

    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        String path = Main.class
                .getClassLoader().getResource("logging.properties").getFile();
        System.out.println(path);
        System.setProperty("java.util.logging.config.file", path);

    }

    public static void main(String[] args) {
        ListBucketsV2.listObjectsInBucket(args[0],args[1],args[2]);
    }
}

