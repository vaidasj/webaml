import Form from '@rjsf/core';
import axios from 'axios';
import React, { useState } from 'react';
import {WebAMLContext} from "../App";

function Solver() {

    const schema = {
        title: "Solve",
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
                enum: ["GLPK", "CPLEX", "BARON", "Gurobi"],
                default: "GLPK"
            },
            features: {
                type: "array",
                title: "Features",
                uniqueItems: true,
                items: {
                    type: "string",
                    enum: ["Presolve", "Parallelize"]
                }
            }
        }
    };

    const uiSchema = {
        "features": {
            "ui:widget": "checkboxes"
        }
    }

    const context = React.useContext(WebAMLContext);

    const [solution, setSolution] = useState(null);
    const [details, setDetails] = useState(null);

    const  handleCallSolver = async (aml, solver) => {
        await axios({
            baseURL: '/model/solve',
            method: 'POST',
            timeout: 1000,
            data: context.model,
            params: {
                aml: aml,
                solver: solver
            }
        })
        .then(response => {
            setSolution(response.data.result);
            setDetails(response.data.verboseOutput);
        })
        .catch(error => {
            console.log(error);
        });
    }
    return (
        <>
            <Form
                schema={schema}
                uiSchema={uiSchema}
                onSubmit={({formData}, _e) => handleCallSolver(formData.aml, formData.solver)}
            >
                <div>
                    <button type="submit" className="btn btn-info btn-block">Solve</button>
                </div>
            </Form>

            <div className="panel">
            </div>

            <div className="panel panel-default" style={solution ? {} : { display: 'none' }}>
                <div className="panel-heading"><strong>Solution</strong></div>
                <div className="panel-body">{solution}</div>
            </div>

            <div className="panel panel-default" style={details ? {} : { display: 'none' }}>
                <div className="panel-heading"><strong>Details</strong></div>
                <div className="panel-body">{details}</div>
            </div>
        </>
    );
}

export default Solver;
