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
public class PyomoFacade implements AmlFacade {

    private final Logger logger = LoggerFactory.getLogger(PyomoFacade.class);

    private final static String AML_NAME = "Pyomo";
    private final static String EXECUTABLE_NAME = "pyomo";
    private final static String TMP_DIR = "pyomo_tmp";
    private final static String OBJECTIVE_PATTERN = "Function\\sValue:\\s([0-9.]+)\\n";
    private final static String SOLVER_PATTERN = "\\s(?:\\+|\\-)([a-z_]+)\\s";


    @Value("${pyomo.directory}")
    private String pyomoDir;
    private boolean amlAvailable;
    private List<String> availableSolvers;

    @PostConstruct
    private void initialize() {
        if (pyomoDir != null && Files.exists(Path.of(pyomoDir + "/" + EXECUTABLE_NAME))) {
            amlAvailable = true;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(pyomoDir + "/" + EXECUTABLE_NAME, "help", "--solvers");
                Process process = processBuilder.start();
                String output = new String(process.getInputStream().readAllBytes());
                int exitCode = process.waitFor();

                availableSolvers = new ArrayList<>();

                if (exitCode == 0) {
                    Pattern pattern = Pattern.compile(SOLVER_PATTERN);
                    Matcher matcher = pattern.matcher(output);
                    availableSolvers = new ArrayList<>();
                    while (matcher.find()) {
                        availableSolvers.add(matcher.group(1).toLowerCase());
                    }
                    Collections.sort(availableSolvers);
                }
            } catch (IOException | InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public boolean isAmlAvailable() {
        return this.amlAvailable;
    }

    @Override
    public String getAmlName() {
        return AML_NAME;
    }

    @Override
    public List<String> getAvailableSolvers() {
        return this.availableSolvers;
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
            Files.writeString(Path.of(TMP_DIR + "/model.py"), model);

            // Run Pyomo
            ProcessBuilder processBuilder = new ProcessBuilder(EXECUTABLE_NAME, "solve", "model.py",
                    "--solver=" + solver);
            processBuilder.directory(new File(TMP_DIR));
            Process process = processBuilder.start();
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            System.out.println("Exit code: " + exitCode);

            // Read solution file
            if (exitCode == 0 || exitCode == 1) {
                Pattern pattern = Pattern.compile(OBJECTIVE_PATTERN, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(output);
                boolean found = matcher.find();
                return new AmlResult().success(found ? matcher.group(1) : "", output);
            }
            else {
                logger.error(output);
                return new AmlResult().error("Pyomo error: " + exitCode, output);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return new AmlResult().error("Internal Error.", e.toString());
        }
        finally {
            try {
                Files.walk(Path.of(TMP_DIR))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ignored) { }
        }
    }

    @Override
    public String convertModel(String model, String targetAml) {
        throw new UnsupportedOperationException("Convert is not supported by Pyomo.");
    }
}
