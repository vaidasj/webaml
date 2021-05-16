package lt.vu.mif.dmsti.webaml.api;

import lt.vu.mif.dmsti.webaml.models.ModelSuccessResponse;
import lt.vu.mif.dmsti.webaml.models.WebAMLModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * A delegate to be called by the {@link ModelApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
public interface ModelApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /model/{operation} : Solve or convert a WebAML model using specific AML
     *
     * @param operation AML tasks to be executed on the model (required)
     * @param aml AML for solving the model (required)
     * @param webAMLModel Model defined in WebAML format (required)
     * @param solver Solver for solving the model. If none specified default for AML will be used. (optional)
     * @param features Additional features to be used if supported by AML. (optional)
     * @return Successful AML task (status code 200)
     *         or Failed AML task (status code 500)
     * @see ModelApi#solveModel
     */
    default ResponseEntity<ModelSuccessResponse> solveModel(String operation,
        String aml,
        WebAMLModel webAMLModel,
        String solver,
        String features) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"verboseOutput\" : \"verboseOutput\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
