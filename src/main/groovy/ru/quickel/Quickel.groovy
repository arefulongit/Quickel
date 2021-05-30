package ru.quickel

import groovy.cli.commons.CliBuilder
import groovy.cli.commons.OptionAccessor
import org.apache.hc.client5.http.entity.mime.FileBody
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.fluent.Request
import org.springframework.boot.SpringApplication


class Quickel {

    /**
     *
     */
    String BPMS_REST_PROCESS_CREATE = "/engine-rest/deployment/create"

    /**
     *  The address where the Camunda web interface should be located
     */
    String bpmsUrl = ""

    /**
     * The directory in which the * .BPMN files are located, which will
     * be loaded into Camunda at the start of the program
     */
    String bpmnDir = ""

    Quickel(String[] pArgs) {
        def cliBuilder = new CliBuilder("usage": "Quickel options", "header": "options:")
        cliBuilder.u(longOpt: "bpmsUrl",
                args: 1,
                argName: "url",
                "Camunda endpoint url, for example http://localhost:8080",
                required: true)
        cliBuilder.d(longOpt: 'bpmnDir',
                args: 1,
                argName: "path",
                "Path to directory with *.bpmn files, c:\\Temp\\bpmn",
                required: true)
        cliBuilder.h(longOpt: "help",
                "display usage")
        OptionAccessor cmdLineOptions = cliBuilder.parse(pArgs)
        if (!cmdLineOptions) return
        this.bpmsUrl = cmdLineOptions.u
        this.bpmnDir = cmdLineOptions.d
    }

    void loadAll(String... args) {
        loadSpringStarterCamunda()
        loadUserResources()
    }

    /**
     *
     * @param args
     */
    private void loadSpringStarterCamunda(String... args) {
        SpringApplication.run(Main.class, args)
    }

    /**
     * Loads * .bpmn, * .dmn, * .cmn files into the pre-launched Camunda from the directory specified
     * in the command line
     */
    private void loadUserResources() {
        File currentDir = new File("${this.bpmnDir}\\").getAbsoluteFile()
        assert currentDir.isDirectory() == true: "${currentDir} is not directory"
        currentDir.listFiles().findAll {
            File file ->
                file.name.containsIgnoreCase(".bpmn")
                        || file.name.containsIgnoreCase(".cmn")
                        || file.name.containsIgnoreCase(".dmn")
        }.each {
            File bpmnOrCmnOrDmnFile ->
                loadUserResource(bpmnOrCmnOrDmnFile)
                println("Loading a custom resource: ${bpmnOrCmnOrDmnFile}")
        }
    }

    /**
     * Loads one custom * .bpmn, * .cmn, * .dmn. Called from loadUserResources ()
     * @param pFile - file to upload
     */
    private void loadUserResource(File pFile) {
        String url = "${bpmsUrl}${BPMS_REST_PROCESS_CREATE}"
        Request.post(url)
                .body(
                        MultipartEntityBuilder
                                .create()
                                .addPart("file", new FileBody(pFile))
                                .build())
                .execute()
    }

}
