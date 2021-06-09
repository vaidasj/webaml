package lt.vu.mif.dmsti.webaml.services;

import lt.vu.mif.dmsti.webaml.models.*;
import lt.vu.mif.dmsti.webaml.utils.NodeListIterator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import uk.ac.ed.ph.snuggletex.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
    public String convert(String modelName, ContentOfWebAMLModel model) {

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
        if (model.getParameters().size() > 0) {
            model.getParameters().forEach(param -> {
                if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
                    writeScalar(joiner, param);
                } else if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
                    if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.NUMBER) {
                        writeParameter(joiner, param, model.getMsets());
                    } else if (param.getValueType() == WebAMLParameterStructure.ValueTypeEnum.FORMULA) {
                        writeManipulationParameter(joiner, param);
                    }
                }
            });
        }

        // Write variables
        if (model.getVariables().size() > 0) {
            model.getVariables().forEach(var -> writeVariable(joiner, var));
        }

        // Write equations
        if (model.getConstraints().size() > 0) {
            model.getConstraints().forEach(eq -> writeEquation(joiner, eq));
        }

        // Write objective
        writeObjective(joiner, model.getObjectives().get(0));

        // Write instructions
        writeInstructions(joiner, modelName, model.getObjectives().get(0).getType());

        // Output final model
        return joiner.toString();
    }

    public void writeSet(StringJoiner joiner, WebAMLSetStructure set) {

        if (set.getType() == WebAMLSetStructure.TypeEnum.SIMPLE) {
            joiner.add(String.format("Set %s \"%s\" / %s / ;", set.getName(), set.getDescription(), String.join(", ",
                    set.getValues())));
        }
    }

    public void writeTable(StringJoiner joiner, WebAMLTableStructure table, List<WebAMLSetStructure> sets) {

        // Definition
        joiner.add(String.format("Table %s(%s,%s) \"%s\"", table.getName(), table.getRows(), table.getColumns(),
                table.getDescription()));

        // Data
        WebAMLSetStructure rowSet =
                sets.stream().filter(s -> s.getName().equals(table.getRows())).findAny().orElse(null);
        WebAMLSetStructure columnSet =
                sets.stream().filter(s -> s.getName().equals(table.getColumns())).findAny().orElse(null);

        int maxColumnName = Collections.max(columnSet.getValues(), Comparator.comparing(String::length)).length();
        int maxRowName = Collections.max(rowSet.getValues(), Comparator.comparing(String::length)).length();
        int columnSize = Math.max(maxColumnName, maxRowName) + 1;

        String space = " ";
        String header =
                columnSet.getValues().stream().map(c -> c + space.repeat(columnSize - c.length())).collect(Collectors.joining());
        joiner.add(String.format("%s%s", space.repeat(columnSize), header));

        for (int i = 0; i < rowSet.getValues().size(); i++) {
            String rowName = rowSet.getValues().get(i);
            StringBuilder tableData = new StringBuilder(rowName).append(space.repeat(columnSize - rowName.length()));
            for (int j = 0; j < columnSet.getValues().size(); j++) {
                String value = table.getValues().get(i).get(j);
                tableData.append(value).append(space.repeat(columnSize - value.length()));
            }
            if (i == rowSet.getValues().size() - 1)
                tableData.append(";");

            joiner.add(tableData.toString());
        }
    }

    public void writeScalar(StringJoiner joiner, WebAMLParameterStructure param) {

        if (param.getType() == WebAMLParameterStructure.TypeEnum.SCALAR) {
            joiner.add(String.format("Scalar %s \"%s\" / %s / ;", param.getName(), param.getDescription(),
                    param.getValues().get(0)));
        }
    }

    public void writeParameter(StringJoiner joiner, WebAMLParameterStructure param, List<WebAMLSetStructure> sets) {

        WebAMLSetStructure set =
                sets.stream().filter(s -> s.getName().equals(param.getIndexes().get(0))).findAny().orElse(null);

        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
            String paramData = IntStream.range(0, set.getValues().size())
                    .mapToObj(i -> set.getValues().get(i) + " = " + param.getValues().get(i))
                    .collect(Collectors.joining(", "));
            joiner.add(String.format("Parameter %s(%s) \"%s\" / %s / ;", param.getName(), param.getIndexes().get(0),
                    param.getDescription(), paramData));
        }
    }

    public void writeManipulationParameter(StringJoiner joiner, WebAMLParameterStructure param) {
        if (param.getType() == WebAMLParameterStructure.TypeEnum.INDEXED) {
            joiner.add(String.format("Parameter %s(%s) \"%s\" ;", param.getName(), String.join(",",
                    param.getIndexes()), param.getDescription()));
            joiner.add(String.format("%s(%s) = %s ;", param.getName(), String.join(",", param.getIndexes()),
                    convertLatex(param.getValues().get(0))));
        }
    }

    public void writeVariable(StringJoiner joiner, WebAMLVariableStructure var) {

        String prefix = "";
        if (var.getType().equals(WebAMLVariableStructure.TypeEnum.BINARY))
            prefix = "Binary ";
        else if (var.getType().equals(WebAMLVariableStructure.TypeEnum.INTEGER))
            prefix = "Integer ";

        String index = "";
        if (var.getIndexes() != null && var.getIndexes().size() > 0)
            index = "(" + String.join(",", var.getIndexes()) + ")";

        joiner.add(String.format("%sVariable %s%s \"%s\" ;", prefix, var.getName(), index, var.getDescription()));

        if (var.getValue().isPresent() && var.getValue().get() != null)
            joiner.add(String.format("%s.fx%s = %s ;", var.getName(), index, var.getValue().get()));
        else if (var.getBounds() != null && var.getBounds().size() == 2) {
            if (var.getBounds().get(0) != null) {
                joiner.add(String.format("%s.lo%s = %s ;", var.getName(), index, var.getBounds().get(0)));
            }
            if (var.getBounds().get(1) != null) {
                joiner.add(String.format("%s.up%s = %s ;", var.getName(), index, var.getBounds().get(1)));
            }
        }
    }

    public void writeEquation(StringJoiner joiner, WebAMLConstraintsStructure eq) {

        String index = "";
        if (eq.getIndexes().size() > 0)
            index = "(" + String.join(",", eq.getIndexes()) + ")";

        joiner.add(String.format("Equation %s%s \"%s\" ;", eq.getName(), index, eq.getDescription()));
        joiner.add(String.format("%s%s .. %s ;", eq.getName(), index, convertLatex(eq.getValue())));

    }

    public void writeObjective(StringJoiner joiner, WebAMLObjectiveStructure obj) {

        joiner.add("Variable obj \"Auto added objective variable\" ;");
        joiner.add(String.format("Equation %s \"%s\" ;", obj.getName(), obj.getDescription()));
        joiner.add(String.format("%s ..  obj  =e=  %s ;", obj.getName(), convertLatex(obj.getValue())));

    }

    public void writeInstructions(StringJoiner joiner, String modelName, WebAMLObjectiveStructure.TypeEnum direction) {
        modelName = modelName.replaceAll(" ", "_");
        joiner.add(String.format("Model %s /all/ ;", modelName));
        joiner.add(String.format("%s.optfile = 1;", modelName));
        joiner.add(String.format("Solve %s using LP %s obj ;", modelName,
                direction == WebAMLObjectiveStructure.TypeEnum.MINIMIZE ? "minimizing" : "maximizing"));
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
            parseNode(fNode, false, gamsFormula);
        }

        return postProcess(gamsFormula.toString());
    }

    private void parseNode(Node node, boolean subscript, StringBuilder gamsFormula) {

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
            parseNode(fNode, subscript, gamsFormula);
        }
    }

    private void parseMSub(Node node, StringBuilder gamsFormula) {

        // parse base
        parseNode(node.getFirstChild(), false, gamsFormula);
        // parse subscript
        gamsFormula.append("(");
        parseNode(node.getLastChild(), true, gamsFormula);
        gamsFormula.append(")");
    }

    private void parseMFrac(Node node, StringBuilder gamsFormula) {

        // parse numerator
        parseNode(node.getFirstChild(), false, gamsFormula);

        // parse denominator
        gamsFormula.append("/");
        parseNode(node.getLastChild(), false, gamsFormula);

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
