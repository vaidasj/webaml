package lt.vu.mif.dmsti.webaml.controllers;

import lt.vu.mif.dmsti.webaml.api.ModelApiDelegate;
import lt.vu.mif.dmsti.webaml.models.ModelSuccessResponse;
import lt.vu.mif.dmsti.webaml.models.WebAMLModel;
import lt.vu.mif.dmsti.webaml.services.WebAMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModelController implements ModelApiDelegate {

    private final Map<String, String> amlConverters =
            Map.of("gams", "gamsConverter", "ampl", "amplConverter");

    @Autowired
    private Map<String, WebAMLConverter> converters = new HashMap<>();

    @Override
    public ResponseEntity<ModelSuccessResponse> solveModel(String operation,
                                                           String aml,
                                                           WebAMLModel webAMLModel,
                                                           String solver,
                                                           String features) {

        String converterId = amlConverters.get(aml.toLowerCase());
        String convertedModel = converters.get(converterId).convert(webAMLModel.getModel());

        System.out.println(convertedModel);

        ModelSuccessResponse response = new ModelSuccessResponse().
                result("Solved").verboseOutput(convertedModel);

        return ResponseEntity.ok(response);
    }

}
