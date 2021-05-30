package lt.vu.mif.dmsti.webaml.services;

import lt.vu.mif.dmsti.webaml.models.ContentOfWebAMLModel;
import lt.vu.mif.dmsti.webaml.models.WebAMLParameterStructure;
import lt.vu.mif.dmsti.webaml.models.WebAMLSetStructure;
import lt.vu.mif.dmsti.webaml.models.WebAMLTableStructure;
import lt.vu.mif.dmsti.webaml.utils.NodeListIterator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import uk.ac.ed.ph.snuggletex.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GamsConverter implements WebAMLConverter {

    private SnuggleSession session;
    private final static String TWO_SUM_PATTERN = "sum\\(([a-zA-z])\\)\\s?sum\\(([a-zA-z])\\)";
    private final static String SUM_OPERANDS_PATTERN = "sum\\(([a-z\\s,]+)\\)\\s?(.+)";
    private final static String SUM_OPERANDS_PATTERN_WITH_EQ_1 = "sum\\(([a-z\\s,]+)\\)\\s?(.+)(\\s=[egl]=.+)";
    private final static String SUM_OPERANDS_PATTERN_WITH_EQ_2 = "(.+\\s=[egl]=\\s)sum\\(([a-z\\s,]+)\\)\\s?(.+)";


    @PostConstruct
    private void initializeLatexConverter() {
        SnuggleEngine engine = new SnuggleEngine();
        this.session = engine.createSession();
    }

    @Override
    public String convert(ContentOfWebAMLModel model) {

        final StringJoiner joiner = new StringJoiner("\n");

        // Write sets
        if (model.getMsets().size() > 0) {
            model.getMsets().forEach(set -> writeSet(joiner, set));
        }

        // Write tables
        if (model.getTables().size() > 0) {
            model.getTables().forEach(table -> writeTable(joiner, table, model.getMsets()));
        }

        // Write parameters
        model.getParameters().forEach(param -> {
            if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
                writeScalar(joiner, param);
            } else if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
                if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.NUMBER) {
                    WebAMLSetStructure set =
                            model.getMsets().stream().filter(s -> s.getName().equals(param.getIndexes().get(0))).findAny().orElse(null);
                    writeParameter(joiner, param, set.getValues());
                } else if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.FORMULA) {
                    writeManipulationParameter(joiner, param);
                }
            }
        });

        // Output final model
        return joiner.toString();
    }

    public StringJoiner writeSet(StringJoiner joiner, WebAMLSetStructure set) {

        if (set.getType() == WebAMLSetStructure.TypeEnum.SIMPLE) {
            joiner.add(String.format("Set %s \"%s\" / %s / ;", set.getName(), set.getDescription(), String.join(", ",
                    set.getValues())));
        }
        return joiner;
    }

    public StringJoiner writeTable(StringJoiner joiner, WebAMLTableStructure table, List<WebAMLSetStructure> sets) {

        // Definition
        joiner.add(String.format("Table %s(%s,%s) \"%s\"", table.getName(), table.getRows(), table.getColumns(),
                table.getDescription()));

        // Data
        WebAMLSetStructure rowsSet =
                sets.stream().filter(s -> s.getName().equals(table.getRows())).findAny().orElse(null);
        WebAMLSetStructure columnSet =
                sets.stream().filter(s -> s.getName().equals(table.getColumns())).findAny().orElse(null);

        return joiner;
    }

    public StringJoiner writeScalar(StringJoiner joiner, WebAMLParameterStructure param) {

        if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
            joiner.add(String.format("Scalar %s \"%s\" / %s / ;", param.getName(), param.getDescription(),
                    param.getValues().get(0)));
        }

        return joiner;
    }

    public StringJoiner writeParameter(StringJoiner joiner, WebAMLParameterStructure param, List<String> index) {

        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
            String paramData = IntStream.range(0, index.size())
                    .mapToObj(i -> index.get(i) + " = " + param.getValues().get(i))
                    .collect(Collectors.joining(", "));
            joiner.add(String.format("Parameter %s(%s) \"%s\" / %s / ;", param.getName(), param.getIndexes().get(0),
                    param.getDescription(), paramData));
        }
        return joiner;
    }

    public StringJoiner writeManipulationParameter(StringJoiner joiner, WebAMLParameterStructure param) {
        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
            joiner.add(String.format("Parameter %s(%s) \"%s\" ;", param.getName(), String.join(",",
                    param.getIndexes()), param.getDescription()));
            joiner.add(String.format("%s(%s) = %s ;", param.getName(), String.join(",", param.getIndexes()),
                    convertLatex(param.getValues().get(0))));
        }
        return joiner;
    }

    public String convertLatex(String latexInput) {

        try {
            session.parseInput(new SnuggleInput(String.format("$%s$", latexInput)));
        } catch (IOException e) {
            session.reset();
            return "";
        }

        StringBuilder gamsFormula = new StringBuilder();

        Element root = (Element) session.buildDOMSubtree().item(0);
        session.reset();

        if (!root.getNodeName().equalsIgnoreCase("math"))
            return "";

        for (Node fNode : NodeListIterator.iterable(root.getChildNodes())) {
            processNode(fNode, false, gamsFormula);
        }

        return postProcess(gamsFormula.toString());
    }

    private void processNode(Node node, boolean subscript, StringBuilder gamsFormula) {

        if (isToken(node)) {
            parseToken(node, gamsFormula);
        } else if (node.getNodeName().equalsIgnoreCase("msub")) {
            parseMSub(node, gamsFormula);
        } else if (node.getNodeName().equalsIgnoreCase("mfrac")) {
            parseMFrac(node, gamsFormula);
        } else if (node.getNodeName().equalsIgnoreCase("mrow")) {
            parseMRow(node, subscript, gamsFormula);
        }
    }

    private void parseMRow(Node node, boolean subscript, StringBuilder gamsFormula) {
        String previousSeparator = "";
        for (Node fNode : NodeListIterator.iterable(node.getChildNodes())) {
            if (subscript) {
                gamsFormula.append(previousSeparator);
                previousSeparator = ",";
            }
            processNode(fNode, subscript, gamsFormula);
        }
    }

    private void parseMSub(Node node, StringBuilder gamsFormula) {

        // parse base
        processNode(node.getFirstChild(), false, gamsFormula);
        // parse subscript
        gamsFormula.append("(");
        processNode(node.getLastChild(), true, gamsFormula);
        gamsFormula.append(")");
    }

    private void parseMFrac(Node node, StringBuilder gamsFormula) {

        // parse numerator
        processNode(node.getFirstChild(), false, gamsFormula);

        // parse denominator
        gamsFormula.append("/");
        processNode(node.getLastChild(), false, gamsFormula);

    }

    private void parseToken(Node node, StringBuilder gamsFormula) {
        if (node.getFirstChild().getNodeValue().equalsIgnoreCase("∑"))
            gamsFormula.append("sum");
        else if (node.getFirstChild().getNodeValue().equalsIgnoreCase("≤"))
            gamsFormula.append(" =l= ");
        else if (node.getFirstChild().getNodeValue().equalsIgnoreCase("≥"))
            gamsFormula.append(" =g= ");
        else if (node.getFirstChild().getNodeValue().equalsIgnoreCase("="))
            gamsFormula.append(" =e= ");
        else
            gamsFormula.append(node.getFirstChild().getNodeValue());
    }

    private boolean isToken(Node node) {
        return (node.getNodeName().equalsIgnoreCase("mo") ||
                node.getNodeName().equalsIgnoreCase("mi") ||
                node.getNodeName().equalsIgnoreCase("mn"));
    }

    private String postProcess(String input) {
        String output;

        // Process summation
        output = input.replaceAll(TWO_SUM_PATTERN, "sum($1,$2)");
        output = output.replaceAll(SUM_OPERANDS_PATTERN_WITH_EQ_1, "sum(($1),$2)$3");
        output = output.replaceAll(SUM_OPERANDS_PATTERN_WITH_EQ_2, "$1sum(($2),$3)");
        output = output.replaceAll(SUM_OPERANDS_PATTERN, "sum(($1),$2)");

        return output;
    }
}
