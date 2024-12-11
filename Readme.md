Run with
mvn install
cd target
java -Dhttp.proxyHost=proxy.mycompany.net -Dhttp.proxyPort=8443 -Dhttps.proxyHost=proxy.mycompany.net -Dhttps.proxyPort=8443 -jar aws-sdk-testing-1.0-SNAPSHOT-jar-with-dependencies.jar "bucketname" "prefix/test.csv" "us-east-1"