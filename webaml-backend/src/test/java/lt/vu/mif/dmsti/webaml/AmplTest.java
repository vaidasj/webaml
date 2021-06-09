package lt.vu.mif.dmsti.webaml;

import lt.vu.mif.dmsti.webaml.services.AmlResult;
import lt.vu.mif.dmsti.webaml.services.AmplFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = AmplFacade.class)
public class AmplTest {


    @Autowired
    private AmplFacade facade;

    @Test
    void testSolve() {
        String model = "set I;\n" +
                "set J;\n" +
                "param a{i in I};\n" +
                "param b{j in J};\n" +
                "param d{i in I, j in J};\n" +
                "param f;\n" +
                "param c{i in I, j in J} := f * d[i,j] / 1000;\n" +
                "var x{i in I, j in J} >= 0;\n" +
                "minimize cost: sum{i in I, j in J} c[i,j] * x[i,j];\n" +
                "s.t. supply{i in I}: sum{j in J} x[i,j] <= a[i];\n" +
                "s.t. demand{j in J}: sum{i in I} x[i,j] >= b[j];\n" +
                "data;\n" +
                "set I := Seattle San-Diego;\n" +
                "set J := New-York Chicago Topeka;\n" +
                "param a := Seattle     350\n" +
                "           San-Diego   600;\n" +
                "param b := New-York    325\n" +
                "           Chicago     300\n" +
                "           Topeka      275;\n" +
                "param d :              New-York   Chicago   Topeka :=\n" +
                "           Seattle     2.5        1.7       1.8\n" +
                "           San-Diego   2.5        1.8       1.4  ;\n" +
                "param f := 90;  \n" +
                "end;";

        AmlResult result = facade.solveModel(model, "cplex");
        assertEquals("Objective value", "153.675", result.getObjectiveValue());

    }
}
