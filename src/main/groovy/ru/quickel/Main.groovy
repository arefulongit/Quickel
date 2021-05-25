package ru.quickel

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.core.io.FileSystemResourceLoader
import org.springframework.core.io.Resource
import org.springframework.web.client.RestTemplate

@SpringBootApplication
public class Main {
    public static void main(String... args) {
        loadSpring(args)
        //loadResources()
    }

    static void loadSpring(String... args){
        SpringApplication.run(Main.class, args)
    }

    static void loadResources() {
        File currentDir = new File("bpmn\\").getAbsoluteFile()
        assert currentDir.isDirectory() == true
        currentDir.listFiles().findAll {
            File file ->
                file.name.containsIgnoreCase(".bpmn") ||
                        file.name.containsIgnoreCase(".cmn")
        }.each {
            File bpmnOrCmnFile ->
                loadResource(bpmnOrCmnFile)
                print("${bpmnOrCmnFile}\n")
        }
        //todo: Необходимо продолжить загрузку *.bpmn файлов при старте
    }

    static void loadResource(File pFile) {
        FileReader reader = new FileReader(pFile)
        RestTemplate restTemplate = new RestTemplate()
        restTemplate.postForLocation("http://localhost:8080/engine-rest/deployment/create", reader)
    }
}
