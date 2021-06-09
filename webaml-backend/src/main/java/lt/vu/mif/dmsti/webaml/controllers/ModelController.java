package lt.vu.mif.dmsti.webaml.controllers;

import lt.vu.mif.dmsti.webaml.api.ModelApiDelegate;
import lt.vu.mif.dmsti.webaml.models.ModelErrorResponse;
import lt.vu.mif.dmsti.webaml.models.ModelSuccessResponse;
import lt.vu.mif.dmsti.webaml.models.WebAMLModel;
import lt.vu.mif.dmsti.webaml.services.AmlFacade;
import lt.vu.mif.dmsti.webaml.services.AmlResult;
import lt.vu.mif.dmsti.webaml.services.WebAMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModelController implements ModelApiDelegate {

    @Autowired
    private Map<String, WebAMLConverter> converters = new HashMap<>();

    @Autowired
    private Map<String, AmlFacade> facades = new HashMap<>();

    @Override
    public ResponseEntity solveModel(String operation,
                                                           String aml,
                                                           WebAMLModel webAMLModel,
                                                           String solver,
                                                           String features) {

        WebAMLConverter converter = converters.get(aml.toLowerCase() + "Converter");
        if (converter == null) {
            return new ResponseEntity(new ModelErrorResponse().
                    error("Converter for specified AML does not exist."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String convertedModel = converter.convert(webAMLModel.getName(), webAMLModel.getModel());

        AmlFacade facade = facades.get(aml.toLowerCase() + "Facade");
        if (facade == null) {
            return new ResponseEntity(new ModelErrorResponse().
                    error("Facade for specified AML does not exist."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AmlResult result = facade.solveModel(convertedModel, solver.toLowerCase());

        if (result.isSolutionFound()) {
            ModelSuccessResponse response = new ModelSuccessResponse().
                    result(result.getObjectiveValue()).verboseOutput(result.getDetails());

            return ResponseEntity.ok(response);
        }
        else {
            ModelErrorResponse response = new ModelErrorResponse().
                    error(result.getError()).verboseOutput(result.getDetails());

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
