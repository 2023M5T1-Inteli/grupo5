/**
* The MaverickApplication class is the main entry point for the Maverick application.
* It uses the Spring Boot framework to start the application and enables Neo4j repositories.
*/

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

    /**
     * The main method is the starting point for the Maverick application.
     * It calls the SpringApplication.run() method to start the application.
     * 
     * @param args command-line arguments passed to the application.
     * @throws Exception if an error occurs during application startup.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MaverickApplication.class, args);
    }
}