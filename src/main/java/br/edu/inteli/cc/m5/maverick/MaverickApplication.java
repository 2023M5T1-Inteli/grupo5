package br.edu.inteli.cc.m5.maverick;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class MaverickApplication {

    private final static Logger log = LoggerFactory.getLogger(MaverickApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MaverickApplication.class, args);
    }
}