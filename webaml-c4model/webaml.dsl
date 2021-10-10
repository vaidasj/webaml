
 workspace "WebAML and Optimization System" "Architecture viewpoints for open-source WebAML and Optimization System." {

    model {
        modeler = person "AML Modeler" "A person developing AML models."
        neos = softwaresystem "NEOS Server" "Remote solvers available via NEOS platform." "External"

        ecosystem = enterprise "WebAML and Optimization Ecosystem" {

            amls = softwaresystem "AMLs" "AMLs (GAMS, AMPL, Pyomo, JuMP) installed locally on the node." "External"
            gamsConvert = softwaresystem "GAMS Convert" "GAMS tool to transpile GAMS model to other AMLs." "External"
            solvers = softwaresystem "Solvers" "Solvers installed locally on the node." "External"

            webAMLSystem = softwaresystem "WebAML and Optimization System" "Allows modelers to create and solve optimization models." {
                singlePageApplication = container "Single-Page Application" "Provides all of the WebAML functionality to modelers via web browser." "JavaScript" "Web Browser"
                webApplication = container "Web Application" "Delivers the static content and the single page application." "node.js"
                apiApplication = container "API Application" "Provides WebAML parsing, converting and solving functionality via RESTFul API." "Java and Spring Boot" {
                    amlController = component "AMLController" "Provides meta information about supported AMLs and solvers." "Spring Boot Rest Controller"
                    modelController = component "Solver Controller" "Converts WebAML model to target AML and sends it for solving." "Spring Boot Rest Controller"
                    webAMLConverter = component "WebAML Converter" "Interface for WebAML to specific AMLs converter" "Interface"
                    gamsConverter = component "GAMS Converter" "Converts GAMS model to specific AMLs." "Spring Bean"
                    amplConverter = component "AMPL Converter" "Converts AMPL model to specific AMLs." "Spring Bean"
                    jumpConverter = component "JuMP Converter" "Converts JuMP model to specific AMLs." "Spring Bean"
                    pyomoConverter = component "Pyomo Converter" "Converts Pyomo model to specific AMLs." "Spring Bean"
                    amlFacade = component "AML Facade" "An interface for implementing translation of requests and responses to/from specific AML CLIs." "Interface"
                    gamsFacade = component "GAMS Facade" "A bridge for translation of requests and responses to/from GAMS CLI" "Spring Bean"
                    amplFacade = component "AMPL Facade" "A bridge for translation of requests and responses to/from AMPL CLIs." "Spring Bean"
                    jumpFacade = component "JuMP Facade" "A bridge for translation of requests and responses to/from JumP CLIs." "Spring Bean"
                    pyomoFacade = component "Pyomo Facade" "A bridge for translation of requests and responses to/from Pyomo CLIs." "Spring Bean"

            }

        }

        # relationships between people and software systems

        uses = modeler -> webAMLSystem "Uses UI to build and solve WebAML models"
        webAMLSystem -> amls "Uses to solve created AML model"
        webAMLSystem -> gamsConvert "Uses to convert generated GAMS model to other AML"
        amls -> solvers "Calls local solvers to solve the model"
        amls -> neos "Calls NEOS Server to solve the model"

        # relationships to/from containers
        modeler -> webApplication "Visits WebAML and Optimization System
        modeler -> singlePageApplication "Creates and solves WebAML models"
        webApplication -> singlePageApplication "Delivers to the modeler's web browser"

        # relationships to/from components
        singlePageApplication -> amlController "Makes API calls to" "REST"
        singlePageApplication -> modelController "Makes API calls to" "REST"
        amlController -> amlFacade "Uses"
        modelController -> webAMLConverter "Uses"
        modelController -> amlFacade "Uses"
        modelController -> gamsFacade "Uses"
        gamsConverter -> webAMLConverter "Implements"
        amplConverter -> webAMLConverter "Implements"
        jumpConverter -> webAMLConverter "Implements"
        pyomoConverter -> webAMLConverter "Implements"
        gamsFacade -> amlFacade "Implements"
        amplFacade -> amlFacade "Implements"
        jumpFacade -> amlFacade "Implements"
        pyomoFacade -> amlFacade "Implements"
        gamsFacade -> amls "Makes RPC calls"
        amplFacade -> amls "Makes RPC calls"
        jumpFacade -> amls "Makes RPC calls"
        pyomoFacade -> amls "Makes RPC calls"

        }

        deploymentEnvironment "PROD" {

            deploymentNode "Modeler's computer" "" "Microsoft Windows, Linux or Apple macOS" {
                deploymentNode "Web Browser" "" "Chrome, Firefox, Safari, or Edge" {
                    liveSinglePageApplicationInstance = containerInstance singlePageApplication
                }
            }

            deploymentNode "Universal Optimization Ecosystem" "" "Azure Cloud" {
                deploymentNode "Azure Cointainer Service" "" "Linux" "" {
                    deploymentNode "node.js" "" "node.js" {
                        liveWebApplicationInstance = containerInstance webApplication
                    }
                    deploymentNode "Apache Tomcat" "" "Apache Tomcat" "" 2 {
                        liveApiApplicationInstance = containerInstance apiApplication
                    }
                }
            }
        }
    }

    views {
        systemlandscape "SystemLandscape" {
            include *
            autoLayout
        }

        systemcontext webAMLSystem "SystemContext" {
            include *
            autoLayout
        }

        container webAMLSystem "Containers" {
            include *
            autoLayout
        }

        component apiApplication "Components" {
            include *
            autoLayout
        }

        dynamic apiApplication "Solve" "Summarises how solving WebAML with AMPL works." {
            singlePageApplication -> modelController "Submits WebAML model for solving"
            modelController -> gamsConverter "Build GAMS model from WebAML"
            gamsConverter -> modelController "Return GAMS model"
            modelController -> gamsFacade "Convert GAMS model to AMPL"
            gamsFacade -> modelController "Returns AMPL scalar model"
            modelController -> amplFacade "Invoke AMPL solve"
            amplFacade -> amls "Execute RPC call to AMPL"
            amls -> solvers "Call specific solver"
            solvers -> amls "Return solver results"
            amls -> amplFacade "Return AMPL solution"
            amplFacade -> modelController "Return solution"
            modelController -> singlePageApplication "Send back solution to UI"
        }

        deployment webAMLSystem "PROD" "PRODDeployment" {
            include *
            autoLayout
        }

        styles {
            element "Person" {
                background #08427b
                color #ffffff
                fontSize 22
                shape Person
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "External" {
                background #999999
                color #ffffff
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Web Browser" {
                shape WebBrowser
            }
            element "Database" {
                shape Cylinder
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
    }

    }
}

