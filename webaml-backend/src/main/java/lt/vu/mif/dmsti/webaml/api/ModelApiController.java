package lt.vu.mif.dmsti.webaml.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-05-15T19:25:32.947144+03:00[Europe/Vilnius]")
@Controller
@RequestMapping("${openapi.webAMLModelSolver.base-path:}")
public class ModelApiController implements ModelApi {

    private final ModelApiDelegate delegate;

    public ModelApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) ModelApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ModelApiDelegate() {});
    }

    @Override
    public ModelApiDelegate getDelegate() {
        return delegate;
    }

}
