package ru.quickel

import groovy.cli.commons.OptionAccessor
import org.apache.hc.client5.http.entity.mime.FileBody
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.fluent.Request
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import groovy.cli.commons.CliBuilder

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        new Quickel(args).loadAll(args)
    }

}
