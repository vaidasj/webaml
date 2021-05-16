import {withTheme} from '@rjsf/core';
import {Theme as SemanticUITheme} from '@rjsf/semantic-ui';

const Form = withTheme(SemanticUITheme);

const schema = {
    title: "Solve Model",
    description: "Solve WebAML model using specific AML, solver and features",
    type: "object",
    required: [
        "aml",
        "solver"
    ],
    properties: {
        aml: {
            type: "string",
            title: "Algebraic Modeling Language",
            enum: ["AMPL", "GAMS", "JuMP", "Pyomo"],
            default: "GAMS"
        },
        solver: {
            type: "string",
            title: "Solver",
            //enum: ["GLPK", "CPLEX", "BARON", "Gurobi"],
            //default: "GLPK"
        }
    }
};

const uiSchema = {
    aml: {
        "ui:options": {
            "semantic" : {
               label: ""
            }
        }

    }
};


function Solver() {
    return (

        <Form
            schema={schema}
            formContext={{
                "semantic":{
                    "wrapLabel": true,
                    "wrapContent": true
                }
            }
            }
        />
    );
}

export default Solver;