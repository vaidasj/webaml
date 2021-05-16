package lt.vu.mif.dmsti.webaml.controllers;

import lt.vu.mif.dmsti.webaml.api.ApiUtil;
import lt.vu.mif.dmsti.webaml.api.ModelApiDelegate;
import lt.vu.mif.dmsti.webaml.models.ModelSuccessResponse;
import lt.vu.mif.dmsti.webaml.models.WebAMLModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Home redirection to OpenAPI api documentation
 */
@Component
public class ModelController implements ModelApiDelegate {

    @Override
    public ResponseEntity<ModelSuccessResponse> solveModel(String operation,
                                                            String aml,
                                                            WebAMLModel webAMLModel,
                                                            String solver,
                                                            String features) {

        ModelSuccessResponse response = new ModelSuccessResponse();
        response.setResult(webAMLModel.getModel().getParameters().get(0).getValues().toString());

        return ResponseEntity.ok(response);
    }

}
