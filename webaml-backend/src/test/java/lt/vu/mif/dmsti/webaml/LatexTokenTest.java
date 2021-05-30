package lt.vu.mif.dmsti.webaml;

import lt.vu.mif.dmsti.webaml.services.GamsConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = GamsConverter.class)
public class LatexTokenTest {

    @Autowired
    GamsConverter gamsConverter;

    @Test
    void convertLatex() throws IOException {
        System.out.println(gamsConverter.convertLatex("f * \\frac{d_{ij}}{1000}"));
        System.out.println(gamsConverter.convertLatex(" \\sum_i \\sum_j c_{ij}*x_{ij}"));
        System.out.println(gamsConverter.convertLatex("\\sum_i x_{ij} \\geq b_j"));
        System.out.println(gamsConverter.convertLatex("b_j \\geq \\sum_i x_{ij}"));


    }
}
