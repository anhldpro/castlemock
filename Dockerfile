FROM openjdk:8-jdk-stretch

ARG TOMCAT_VERSION=8.5.47

RUN mkdir /tomcat
RUN curl --silent --location --retry 3 "https://archive.apache.org/dist/tomcat/tomcat-8/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz" | gunzip | tar x -C /tomcat/ 

RUN mv /tomcat/apache-* /tomcat/tomcat

ENV CATALINA_HOME /tomcat/tomcat

RUN curl -o castlemock.war -fSL https://github.com/castlemock/castlemock/releases/download/1.40/castlemock.war
RUN cp castlemock.war /tomcat/tomcat/webapps 

RUN useradd jboss -g 0 
RUN usermod -g root -G 0 jboss && mkdir /home/jboss 
RUN chown -R jboss:0 /tomcat/tomcat && chown -R jboss:0 /home/jboss

CMD ["/tomcat/tomcat/bin/catalina.sh", "run"]

# Expose HTTP port 8080
EXPOSE 8080

USER jboss
