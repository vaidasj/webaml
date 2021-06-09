package lt.vu.mif.dmsti.webaml.services;

import java.util.List;

public interface AmlFacade {

    boolean isAmlAvailable();
    String getAmlName();
    List<String> getAvailableSolvers();
    AmlResult solveModel(String model, String solver);
    String convertModel(String model, String targetAml);

}
