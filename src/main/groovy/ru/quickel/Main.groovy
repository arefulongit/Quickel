package ru.quickel

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.core.io.FileSystemResourceLoader
import org.springframework.core.io.Resource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
        String url = "http://localhost:8080/engine-rest/deployment/create"
        String fileContent = pFile.text
        RestTemplate restTemplate = new RestTemplate()
        HashMap formParams = [
                "deployment-name":"aName",
                "enable-duplicate-filtering":"true",
                "deployment-source":"process application",
                "data":"filename=${pFile.getName()}"
        ]
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<String> entity = new HttpEntity<String>(fileContent, headers );
        //HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
