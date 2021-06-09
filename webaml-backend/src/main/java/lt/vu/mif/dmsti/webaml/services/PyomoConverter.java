package lt.vu.mif.dmsti.webaml.services;

import lt.vu.mif.dmsti.webaml.models.ContentOfWebAMLModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PyomoConverter implements WebAMLConverter {

    @Autowired
    private GamsFacade gamsFacade;

    @Autowired
    private GamsConverter gamsConverter;

    @Override
    public String convert(String modelName, ContentOfWebAMLModel model) {

        // Using Gams Convert as an intermediate converter
        String gamsModel = gamsConverter.convert(modelName, model);
        String pyomoModel = gamsFacade.convertModel(gamsModel, "pyomo");

        return pyomoModel;
    }
}
