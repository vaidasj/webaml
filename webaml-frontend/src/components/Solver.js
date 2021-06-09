import Form from '@rjsf/core';
import axios from 'axios';
import React, {useState, useEffect} from 'react';
import {WebAMLContext} from "../App";

function Solver() {

    const context = React.useContext(WebAMLContext);

    useEffect(() => {
        const basicSchema = {
            "title": "Solve",
            "description": "Solve WebAML model using specific AML, solver and features",
            "type": "object",
            "properties": {
                "aml": {
                    "title": "Algebraic Modeling Language",
                    "type": "string",
                    "enum": ["GAMS"],
                    "default": "GAMS"
                }
            },
            "required": [
                "aml", "solver"
            ],
            "dependencies": {
                "aml": {
                    "oneOf": []
                }
            }
        };

        async function fetchData() {
            await axios({
                baseURL: '/aml',
                method: 'GET',
                timeout: 1000,
            })
                .then(response => {

                    const amls = Object.keys(response.data);

                    basicSchema.properties.aml.enum = amls;

                    amls.forEach(function (item, index) {
                        basicSchema.dependencies.aml.oneOf.push( {
                            "properties": {
                                "aml": {
                                    "type": "string",
                                    "title": "Algebraic Modeling Language",
                                    "enum": [item]
                                },
                                "solver": {
                                    "type": "string",
                                    "title": "Solver",
                                    "enum": response.data[item]
                                }
                            }
                        })
                    });
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
            timeout: 10000,
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
