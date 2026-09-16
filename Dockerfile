FROM tomcat:9.0-jdk8

COPY target/LoanAPI.war /usr/local/tomcat/webapps/

EXPOSE 8080