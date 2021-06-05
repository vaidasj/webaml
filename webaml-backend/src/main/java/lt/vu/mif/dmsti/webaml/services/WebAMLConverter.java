package lt.vu.mif.dmsti.webaml.services;

import lt.vu.mif.dmsti.webaml.models.ContentOfWebAMLModel;

public interface WebAMLConverter {

    String convert(String modelName, ContentOfWebAMLModel model);
}
