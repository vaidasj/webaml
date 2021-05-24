package lt.vu.mif.dmsti.webaml.services;

import lt.vu.mif.dmsti.webaml.models.ContentOfWebAMLModel;
import lt.vu.mif.dmsti.webaml.models.WebAMLParameterStructure;
import lt.vu.mif.dmsti.webaml.models.WebAMLSetStructure;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GamsConverter implements WebAMLConverter {

    @Override
    public String convert(ContentOfWebAMLModel model) {

        final StringJoiner joiner = new StringJoiner("\n");

        // Write sets
        if (model.getMsets().size() > 0) {
            model.getMsets().forEach(set -> writeSet(joiner, set));
        }

        // Write parameters
        model.getParameters().forEach(param -> {
            if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
                writeScalar(joiner, param);
            }
            else if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
                if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.NUMBER) {
                    WebAMLSetStructure set = model.getMsets().stream().filter(s -> s.getName().equals(param.getIndexes().get(0))).findAny().orElse(null);
                    writeParameter(joiner, param, set.getValues());
                }
                else if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.FORMULA) {
                    writeManipulationParameter(joiner, param);
                }
            }
        });

        // Output final model
        return joiner.toString();
    }

    public StringJoiner writeSet(StringJoiner joiner, WebAMLSetStructure set) {

        if (set.getType() == WebAMLSetStructure.TypeEnum.SIMPLE) {
            joiner.add(String.format("Set %s \"%s\" / %s / ;", set.getName(), set.getDescription(), String.join(", ", set.getValues())));
        }
        return joiner;
    }

    public StringJoiner writeScalar(StringJoiner joiner, WebAMLParameterStructure param) {

        if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
            joiner.add(String.format("Scalar %s \"%s\" / %s / ;", param.getName(), param.getDescription(), param.getValues().get(0)));
        }

        return joiner;
    }

    public StringJoiner writeParameter(StringJoiner joiner, WebAMLParameterStructure param, List<String> index) {

        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
            String paramData = IntStream.range(0, index.size())
                    .mapToObj(i -> index.get(i) + " = " + param.getValues().get(i))
                    .collect(Collectors.joining(", "));
            joiner.add(String.format("Parameter %s(%s) \"%s\" / %s / ;", param.getName(), param.getIndexes().get(0), param.getDescription(), paramData));
        }
        return joiner;
    }

    public StringJoiner writeManipulationParameter(StringJoiner joiner, WebAMLParameterStructure param) {
        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
                joiner.add(String.format("Parameter %s(%s) \"%s\" ;", param.getName(), String.join(",", param.getIndexes()), param.getDescription()));
                joiner.add(String.format("%s(%s) = %s ;", param.getName(), String.join(",", param.getIndexes()), convertFormula(param.getValues().get(0))));
        }
        return joiner;
    }

    public String convertFormula(String asciiMath) {

        String gamsFormula = asciiMath;

        // Replace subscript
        String subscriptPattern = "_\\(?([a-zA-Z]+)\\)?";
        gamsFormula = gamsFormula.replaceAll(subscriptPattern, "($1)");

        // Replace two consecutive sums
        String sumMergePattern = "sum\\(([a-zA-Z])\\)\\s?sum\\(([a-zA-Z])\\)";
        gamsFormula = gamsFormula.replaceAll(sumMergePattern, "sum($1$2) ");

        // Group sums and operands
        String sumGroupPattern = "sum\\(([a-z]+)\\)([^><=]+)";
        gamsFormula = gamsFormula.replaceAll(sumGroupPattern, "sum(($1),$2) ");

        // Replace operations
        String greaterThanPattern = "(>=|=>)";
        gamsFormula = gamsFormula.replaceAll(greaterThanPattern, "=g=");

        String lessThanPattern = "(<=|=<)";
        gamsFormula = gamsFormula.replaceAll(lessThanPattern, "=l=");

        String equalsPattern = "\\s(=)\\s";
        gamsFormula = gamsFormula.replaceAll(equalsPattern, "=e=");

        // Split parameters by comma

        return gamsFormula;
    }
}
