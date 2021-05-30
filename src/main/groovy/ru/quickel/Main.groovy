package ru.quickel

import org.apache.hc.client5.http.entity.mime.FileBody
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.fluent.Request
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import groovy.cli.commons.CliBuilder

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        def cli = new CliBuilder(usage: 'Quickel [options]', header: "Options")
        cli.ceu(args:1, argName:"url", "Camunda endpoint url, for example http://localhost:8080")
        cli.pdd(args:1, argName:'path', "Path to directory with *.bpmn files, c:\\Temp\\bpmn")
        def cmdLineOptions = cli.parse(args)
        //assert cmdLineOptions != null
        assert cmdLineOptions.arguments() == ["options"]
        //assert cmdLineOptions.ceu == null
        //assert cmdLineOptions.pdd == null

        loadSpring(args)
        //loadResources()
    }

    static void loadSpring(String... args) {
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
        Request.post(url)
                .body(
                        MultipartEntityBuilder
                                .create()
                                .addPart("file", new FileBody(pFile))
                                .build())
                .execute()
    }
}
