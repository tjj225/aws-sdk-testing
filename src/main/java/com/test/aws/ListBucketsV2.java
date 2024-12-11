package com.test.aws;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class ListBucketsV2 {
    static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        String bucket= "bucketname";
        String key ="prefix/test.csv";
        String region = "us-east-1";
        listObjectsInBucket(bucket,key,region);
    }


    /**
     * Lists all the S3 buckets available in the current AWS account.
     *
     * @param s3     The {@link S3Client} instance to use for interacting with the Amazon S3 service.
     * @param region
     */
    public static void listObjectsInBucket(String bucket, String key, String region) {


        S3Client s3Client = S3Client.builder().credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(region))
                .build();

        listAllBuckets(s3Client);
        //check ListObjectsV2Request
        listObjectsV2Request(bucket,key,s3Client);
        try {
            //check head
            //aws s3api head-object --bucket bucket --key key
            headObject(bucket, key, s3Client);
            print(bucket,key,s3Client);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        s3Client.close();
    }

    private static void headObject(String bucket, String key, S3Client s3Client) {
        final HeadObjectRequest headObjectRequest =
                HeadObjectRequest.builder().bucket(bucket).key(key).build();
        HeadObjectResponse x = s3Client.headObject(headObjectRequest);
        System.out.println( "HeadObjectResponse:"+x.toString());
    }

    private static  void listObjectsV2Request(
            final String bucket,
            final String key,
            final S3Client s3Client) {
        logger.log(Level.INFO, "listObjectsV2Request..");
        if (key.isEmpty()) {
            return ;
        }
        final ListObjectsV2Request listObjectsV2Request =
                ListObjectsV2Request.builder().bucket(bucket).prefix(key).build();
        s3Client.listObjectsV2(listObjectsV2Request).contents().stream()
                .forEach(s3ObjectSummary -> System.out.println("listObjectsV2:"+s3ObjectSummary.key()));
    }

    public static void print(final String bucket,
                             final String key,
                             final S3Client s3Client) throws IOException {
        logger.log(Level.INFO, "printing..");
        final GetObjectRequest request =
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .range("bytes=" + 0 + "-" + 10)
                        .build();
        ResponseInputStream<GetObjectResponse> s3objectResponse = s3Client
                .getObject(request);
        BufferedReader reader = new BufferedReader(new InputStreamReader(s3objectResponse));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    /**
     * Lists all the S3 buckets available in the current AWS account.
     *
     * @param s3 The {@link S3Client} instance to use for interacting with the Amazon S3 service.
     */
    public static void listAllBuckets(S3Client s3) {

        logger.log(Level.INFO, "listAllBuckets..");
        ListBucketsResponse response = s3.listBuckets();
        List<Bucket> bucketList = response.buckets();
        for (Bucket bucket: bucketList) {
            System.out.println("Bucket name "+bucket.name());
        }
    }
}

