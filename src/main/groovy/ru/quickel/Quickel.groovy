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
     * Name of protocols (http/https) - used in uri-string
     */
    String protoName = "http"

    /**
     *  The address where the Camunda web interface should be located
     */
    String serverName = ""

    /**
     * The port where the Camunda web interface should be located
     */
    String portNum = "8080"

    /**
     * The directory in which the * .BPMN files are located, which will
     * be loaded into Camunda at the start of the program
     */
    String bpmnDir = ""


    /**
     *
     * @param pArgs - command line arguments
     */
    Quickel(String[] pArgs) {
        def cliBuilder = new CliBuilder("usage": "Quickel options", "header": "options:")
        cliBuilder.n(longOpt: "protoName",
                args: 1,
                argName: "name",
                "Protocol name: http or https",
                required: true
        )
        cliBuilder.s(longOpt: "serverName",
                args: 1,
                argName: "url",
                "This server name, for example localhost or my-beautiful-server",
                required: true
        )
        cliBuilder.p(longOpt: "portNum",
                args: 1,
                argName: "num",
                "http-port for webinterface",
                required: true
        )
        cliBuilder.d(longOpt: 'bpmnDir',
                args: 1,
                argName: "path",
                "Path to directory with *.bpmn files, c:\\Temp\\bpmn",
                required: true
        )
        cliBuilder.h(longOpt: "help",
                "display usage"
        )
        OptionAccessor cmdLineOptions = cliBuilder.parse(pArgs)
        if (!cmdLineOptions) System.exit(-1)
        this.protoName = cmdLineOptions.n
        this.serverName = cmdLineOptions.s
        this.portNum = cmdLineOptions.p
        this.bpmnDir = cmdLineOptions.d
    }

    /**
     * First, we load Spring, and then the user files * .bpmn, * .dmn, * .cmn.
     * loadSpringStarterCamunda's function parameters are still reserved
     */
    void loadAll() {
        loadSpringStarterCamunda("","")
        loadUserResources()
    }

    /**
     * Load Spring
     * @param args
     */
    private void loadSpringStarterCamunda(String... pArgs) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", this.portNum));
        app.run(pArgs);
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
        String url = "${protoName}://${serverName}:${portNum}${BPMS_REST_PROCESS_CREATE}"
        Request.post(url)
                .body(
                        MultipartEntityBuilder
                                .create()
                                .addPart("file", new FileBody(pFile))
                                .build())
                .execute()
    }

}
