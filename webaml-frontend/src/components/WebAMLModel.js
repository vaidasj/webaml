import Form from '@rjsf/core';
import React, { useState, useContext } from 'react';
import {WebAMLContext} from "../App";
import {LatexWidget} from "./LatexWidget";

function WebAMLModel() {

    const schema = require('../../../webaml-schema/webaml.schema.json');
    const uiSchema =  {
        "webamlVersion": {
            "ui:widget": "hidden",
        },
        "model": {
            "objectives": {
                "items": {
                    "value": {
                        "ui:widget": "latexWidget"
                    }
                }
            },
            "constraints": {
                "items": {
                    "value": {
                        "ui:widget": "latexWidget"
                    }
                }
            },
            "parameters": {
                "items": {
                    "values": {
                        "items": {
                            "ui:widget": "latexWidget"
                        }
                    }
                }
            }
        }
    };

    const widgets = {
        latexWidget: LatexWidget
    }

    const {setModel} = useContext(WebAMLContext)

    const [formData, setFormData] = useState(null);

    const handleSaveToFile = jsonData => {
        const fileData = JSON.stringify(jsonData);
        const blob = new Blob([fileData], {type: "text/plain"});
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.download = 'model.json';
        link.href = url;
        link.click();
    }

    const handleLoadFromFile = e => {
        const fileReader = new FileReader();
        fileReader.readAsText(e.target.files[0], "UTF-8");
        fileReader.onload = e => {
            const model = JSON.parse(e.target.result)
            setModel(model);
            setFormData(model);
            window.scrollTo(0, 0);
        }
    };

    return (
        <div>
            <Form schema={schema}
              uiSchema={uiSchema} formData={formData} widgets={widgets}
              onChange={e => {
                  setFormData(e.formData)
                  setModel(e.formData)
              }}
              onSubmit={({formData}, e) => handleSaveToFile(formData)}
            >
            <div>
                <div className="input-group">
                    <label className="input-group-btn">
                    <span className="btn btn-default">
                        Choose File <input type="file" onChange={handleLoadFromFile} id="model-file" name="model_file" style={{display: 'none'}}/>
                    </span>
                    </label>
                    <input type="text" className="form-control" id="model-name" placeholder="Select existing WebAML file to import"
                           readOnly/>
                </div>
                <div>&nbsp;</div>
                <button type="submit" className="btn btn-info btn-block">Export</button>
            </div>
            </Form>
        </div>
    );
}

export default WebAMLModel;