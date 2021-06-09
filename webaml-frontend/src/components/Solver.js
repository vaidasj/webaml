import Form from '@rjsf/core';
import axios from 'axios';
import React, {useState, useEffect} from 'react';
import {WebAMLContext} from "../App";

function Solver() {

    const context = React.useContext(WebAMLContext);

    useEffect(() => {
        const basicSchema = {
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
                    enum: ["GLPK", "CPLEX", "BARON", "GUROBI"],
                    default: "GLPK"
                }
            }
        };

        console.log("USE effect");
        async function fetchData() {
            await axios({
                baseURL: '/aml',
                method: 'GET',
                timeout: 1000,
            })
                .then(response => {
                    basicSchema.properties.aml.enum = Object.keys(response.data);
                    basicSchema.properties.solver.enum = response.data.GAMS;
                    setSchema(basicSchema);
                })
                .catch(function (error) { });
        }
        fetchData();
    }, []);

    const [formData, setFormData] = useState(null);
    const [schema, setSchema] = useState({});
    const [solution, setSolution] = useState(null);
    const [details, setDetails] = useState(null);

    const handleCallSolver = async (aml, solver) => {
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
            .catch(function (error) {
                if (error.response) {
                    setSolution(error.response.data.error);
                    setDetails(error.response.data.verboseOutput);
                } else if (error.request) {
                    setSolution("Backend did not respond");
                    setDetails(error.request);
                } else {
                    setSolution("Frontend error");
                    setDetails(error.message);
                }
            });
    }

    return (
        <>
            <Form
                schema={schema}
                formData={formData}
                onChange={e => {
                    setFormData(e.formData)
                }}
                onSubmit={({formData}, _e) => handleCallSolver(formData.aml, formData.solver)}
            >
                <div>
                    <button type="submit" className="btn btn-info btn-block">Solve</button>
                </div>
            </Form>

            <div className="panel">
            </div>

            <div className="panel panel-default" style={solution ? {} : {display: 'none'}}>
                <div className="panel-heading"><strong>Solution</strong></div>
                <div className="panel-body">{solution}</div>
            </div>

            <div className="panel panel-default" style={details ? {} : {display: 'none'}}>
                <div className="panel-heading"><strong>Details</strong></div>
                <div className="panel-body">
                    {details &&
                    details.split('\n').map(function (item, key) {
                        return (
                            <span key={key}>
                                {item}
                                <br/>
                            </span>
                        )
                    })
                    }
                </div>
            </div>
        </>
    );
}

export default Solver;
