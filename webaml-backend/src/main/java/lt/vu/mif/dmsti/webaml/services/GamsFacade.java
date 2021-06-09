package lt.vu.mif.dmsti.webaml.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GamsFacade implements AmlFacade {

    private final Logger logger = LoggerFactory.getLogger(GamsFacade.class);

    private final static String AML_NAME = "GAMS";
    private final static String EXECUTABLE_NAME = "gams";
    private final static String TMP_DIR = "gms_tmp";
    private final static String OBJECTIVE_PATTERN = "[*]{4}\\sOBJECTIVE VALUE\\s+([0-9.]+)";
    private final static String SOLVER_PATTERN = "\\n([A-Z0-9]+)\\s\\d";

    @Value("${gams.directory}")
    private String gamsDir;
    private boolean amlAvailable;
    private List<String> availableSolvers;
    private List<String> supportedConversions;

    @PostConstruct
    private void initialize() {
        if (gamsDir != null && Files.exists(Path.of(gamsDir + "/" + EXECUTABLE_NAME))) {
            amlAvailable = true;
            try {
                String solverFile = Files.readString(Path.of(gamsDir + "/gmscmpun.txt"));
                Pattern pattern = Pattern.compile(SOLVER_PATTERN);
                Matcher matcher = pattern.matcher(solverFile);
                availableSolvers = new ArrayList<>();
                while (matcher.find()) {
                    availableSolvers.add(matcher.group(1).toLowerCase());
                }
                Collections.sort(availableSolvers);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            supportedConversions = Arrays.asList("ampl", "pyomo", "jump");
        }
    }

    @Override
    public AmlResult solveModel(String model, String solver) {

        if (!amlAvailable || !availableSolvers.contains(solver))
            return new AmlResult().error("Invalid configuration", "AML or solver is not available.");

        if (model == null || model.length() == 0)
            return new AmlResult().error("Internal error", "AML Facade received empty model.");

        try {
            // Create temp directory
            if (!Files.exists(Path.of(TMP_DIR)))
                Files.createDirectory(Path.of(TMP_DIR));

            // Create model file
            Files.writeString(Path.of(TMP_DIR + "/model.gms"), model);

            // Run GAMS
            ProcessBuilder processBuilder = new ProcessBuilder(gamsDir + "/" + EXECUTABLE_NAME, "model.gms", "solver" +
                    "=" + solver);

            processBuilder.directory(new File(TMP_DIR));
            Process process = processBuilder.start();
            String output = new String(process.getInputStream().readAllBytes());
            logger.info(output);
            int exitCode = process.waitFor();

            // Read solution file
            if (exitCode == 0) {
                String result = Files.readString(Path.of(TMP_DIR + "/model.lst"));
                Pattern pattern = Pattern.compile(OBJECTIVE_PATTERN);
                Matcher matcher = pattern.matcher(result);
                boolean found = matcher.find();
                return new AmlResult().success(found ? matcher.group(1) : "", result);
            } else {
                logger.error(output);
                return new AmlResult().error("Gams error: " + exitCode, output);
            }

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return new AmlResult().error("Internal Error.", e.toString());
        } finally {
            try {
                Files.walk(Path.of(TMP_DIR))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public String convertModel(String model, String targetAml) {

        if (!amlAvailable || !supportedConversions.contains(targetAml))
            throw new UnsupportedOperationException("GAMS is not available or target AML not supported.");

        if (model == null || model.length() == 0)
            throw new UnsupportedOperationException("Internal error: AML Facade received empty model.");

        try {
            // Create temp directory
            if (!Files.exists(Path.of(TMP_DIR)))
                Files.createDirectory(Path.of(TMP_DIR));

            // Create model file
            Files.writeString(Path.of(TMP_DIR + "/model.gms"), model);

            // Create configuration file
            Files.writeString(Path.of(TMP_DIR + "/convert.opt"), String.format("%s converted.txt", targetAml));

            // Run GAMS
            ProcessBuilder processBuilder = new ProcessBuilder(gamsDir + "/" + EXECUTABLE_NAME, "model.gms",
                    "lp=convert");

            processBuilder.directory(new File(TMP_DIR));
            Process process = processBuilder.start();
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();
            // Read solution file
            if (exitCode == 0) {
                String result = Files.readString(Path.of(TMP_DIR + "/converted.txt"));
                return result;
            } else {
                logger.error(output);
                return null;
            }

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            try {
                Files.walk(Path.of(TMP_DIR))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public boolean isAmlAvailable() {
        return amlAvailable;
    }

    @Override
    public List<String> getAvailableSolvers() {
        return availableSolvers;
    }

    @Override
    public String getAmlName() {
        return AML_NAME;
    }
}
