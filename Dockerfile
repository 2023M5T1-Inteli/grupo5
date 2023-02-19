FROM eclipse-temurin:17-jre-focal

RUN apt-get update && \
    apt-get install -y gdal-bin && \
    apt-get install -y libgdal-java && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*gitk

ENV LD_LIBRARY_PATH=/usr/lib/jni
ENV CLASSPATH=/usr/share/java/gdal.jar

CMD ["bash"]