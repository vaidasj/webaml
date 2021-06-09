package lt.vu.mif.dmsti.webaml.controllers;

import lt.vu.mif.dmsti.webaml.services.AmlFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AmlController {

    @Autowired
    private Map<String, AmlFacade> facades = new HashMap<>();

    private Map<String, List<String>> availableAmls;

    @PostConstruct
    private void initialize() {
        availableAmls = new HashMap<>();
        facades.forEach((k, v) -> {
            if (v.isAmlAvailable()) {
                availableAmls.put(v.getAmlName(), v.getAvailableSolvers());
            }
        });
    }

    @RequestMapping("/aml")
    @ResponseBody
    public Map<String, List<String>> getAvailableAmls() {
        return availableAmls;
    }

}
