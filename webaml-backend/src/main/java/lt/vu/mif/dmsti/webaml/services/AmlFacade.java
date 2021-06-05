package lt.vu.mif.dmsti.webaml.services;

public interface AmlFacade {

    AmlResult solveModel(String model, String solver);
    void convertModel(String model, String targetAml);
}
