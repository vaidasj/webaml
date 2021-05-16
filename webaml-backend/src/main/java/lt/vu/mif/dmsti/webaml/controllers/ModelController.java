package lt.vu.mif.dmsti.webaml.controllers;

import lt.vu.mif.dmsti.webaml.api.ModelApiDelegate;
import lt.vu.mif.dmsti.webaml.models.ModelSuccessResponse;
import lt.vu.mif.dmsti.webaml.models.WebAMLModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ModelController implements ModelApiDelegate {

    @Override
    public ResponseEntity<ModelSuccessResponse> solveModel(String operation,
                                                            String aml,
                                                            WebAMLModel webAMLModel,
                                                            String solver,
                                                            String features) {

        ModelSuccessResponse response = new ModelSuccessResponse().
                result(webAMLModel.getModel().getParameters().get(0).getValues().toString());

        return ResponseEntity.ok(response);
    }

}
