package ru.quickel

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication

import java.nio.file.Path;

@SpringBootApplication
public class Main {
    public static void main(String... args) {
        SpringApplication.run(Main.class, args)
        print("Spring is loaded\n")
        loadBpmn()
    }

    static Closure loadBpmn = {
        File currentDir = new File("bpmn\\").getAbsoluteFile()
        assert currentDir.isDirectory() == true
        currentDir.listFiles().findAll {
            File file ->
                file.name.containsIgnoreCase(".bpmn")
        }.each {
            File currentFile ->
                print("${currentFile}\n")
        }
        //todo: Необходимо продолжить загрузку *.bpmn файлов при старте
    }
}
