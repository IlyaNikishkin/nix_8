call mvn clean install
cp ./target/cleanspring.war ./apache-tomcat-9.0.58/webapps
cd apache-tomcat-9.0.58/bin
call startup.sh