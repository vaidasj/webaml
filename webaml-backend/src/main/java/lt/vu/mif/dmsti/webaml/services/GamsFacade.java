package lt.vu.mif.dmsti.webaml.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GamsFacade implements AmlFacade {

    private Logger logger = LoggerFactory.getLogger(GamsFacade.class);
    private final static String OBJECTIVE_PATTERN = "[*]{4}\\sOBJECTIVE VALUE\\s+([0-9.]+)";


    @Override
    public AmlResult solveModel(String model, String solver) {
        try {
            // Create model file
            Files.writeString(Path.of("model.gms"), model);

            // Run GAMS
            Process process = new ProcessBuilder("gams", "model.gms", "solver=" + solver).start();
            String output = new String(process.getInputStream().readAllBytes());
            logger.info(output);
            int exitCode = process.waitFor();

            // Read solution file
            if (exitCode == 0) {
                String result = Files.readString(Path.of("model.lst"));
                Pattern pattern = Pattern.compile(OBJECTIVE_PATTERN);
                Matcher matcher = pattern.matcher(result);
                boolean found = matcher.find();
                return new AmlResult().success(found ? matcher.group(1) : "", result);
            }
            else {
                logger.error(output);
                return new AmlResult().error("Gams error: " + exitCode, output);
            }

        } catch (IOException | InterruptedException e) {

            logger.error(e.getMessage());
            return new AmlResult().error("Internal Error.", e.getMessage());
        }
    }

    @Override
    public void convertModel(String model, String targetAml) {
        throw new UnsupportedOperationException("Convert is not supported by GAMS");
    }
}
