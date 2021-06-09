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
public class AmplFacade implements AmlFacade {

    private final Logger logger = LoggerFactory.getLogger(AmplFacade.class);

    private final static String AML_NAME = "AMPL";
    private final static String EXECUTABLE_NAME = "ampl";
    private final static String TMP_DIR = "ampl_tmp";
    private final static String OBJECTIVE_PATTERN = "objective\\s([0-9.]+)\\n";

    @Value("${ampl.directory}")
    private String amplDir;
    private boolean amlAvailable;
    private List<String> availableSolvers;

    @PostConstruct
    private void initialize() {
        if (amplDir != null && Files.exists(Path.of(amplDir + "/" + EXECUTABLE_NAME))) {
            amlAvailable = true;
            try {

                ProcessBuilder processBuilder = new ProcessBuilder("find", amplDir, "-maxdepth", "1", "-perm", "-111"
                        , "-type", "f");
                Process process = processBuilder.start();
                String output = new String(process.getInputStream().readAllBytes());
                int exitCode = process.waitFor();

                availableSolvers = new ArrayList<>();

                if (exitCode == 0) {
                    String[] lines = output.split(System.getProperty("line.separator"));
                    Arrays.stream(lines).forEach(l -> {
                        String name = l.replaceAll(amplDir + "/", "");
                        if (!name.startsWith("ampl") && !name.startsWith("fingerprint"))
                            availableSolvers.add(name);
                    });
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
            Files.writeString(Path.of(TMP_DIR + "/model.mod"), model);

            // Create run file
            String runFile = String.format(
                    "model model.mod;\n" +
                    "option solver %s;\n" +
                    "option show_stats 1;\n" +
                    "solve;\n", solver);
            Files.writeString(Path.of(TMP_DIR + "/model.run"), runFile);

            // Run AMPL
            ProcessBuilder processBuilder = new ProcessBuilder(EXECUTABLE_NAME, "model.run");
            processBuilder.directory(new File(TMP_DIR));
            Process process = processBuilder.start();
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            // Read solution file
            if (exitCode == 0) {
                Pattern pattern = Pattern.compile(OBJECTIVE_PATTERN, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(output);
                boolean found = matcher.find();
                return new AmlResult().success(found ? matcher.group(1) : "", output);
            }
            else {
                logger.error(output);
                return new AmlResult().error("AMPL error: " + exitCode, output);
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
        throw new UnsupportedOperationException("Convert is not supported by AMPL.");
    }
}
