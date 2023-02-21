FROM eclipse-temurin:17-focal

RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.0/binaries/apache-maven-3.9.0-bin.tar.gz -P /tmp && \
    tar xf /tmp/apache-maven-*.tar.gz -C /opt && \
    rm /tmp/apache-maven-*-bin.tar.gz && \
    ln -s /opt/apache-maven-3.9.0 /opt/maven && \
    apt-get update && \
    apt-get install -y gdal-bin && \
    apt-get install -y libgdal-java && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV M2_HOME=/opt/maven
ENV MAVEN_HOME=/opt/maven
ENV PATH=${M2_HOME}/bin:${PATH}

ENV LD_LIBRARY_PATH=/usr/lib/jni
ENV CLASSPATH=/usr/share/java/gdal.jar

WORKDIR /app
COPY . /app

RUN mvn clean install

EXPOSE 8080

CMD ["mvn", "exec:java", "-Dexec.mainClass=br.edu.inteli.cc.m5.maverick.MaverickApplication"]
