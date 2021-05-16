import { withTheme } from '@rjsf/core';
import { Theme as SemanticUITheme } from '@rjsf/semantic-ui';

const Form = withTheme(SemanticUITheme);

const schema = require('../../../webaml-schema/webaml.schema.json');
const uiSchema =  {
    webamlVersion: {
        "ui:widget": "hidden",
    },
};

const handleSaveToPC = jsonData => {
    const fileData = JSON.stringify(jsonData);
    const blob = new Blob([fileData], {type: "text/plain"});
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.download = 'model.json';
    link.href = url;
    link.click();
}

function WebAMLModel() {
    return (
        <Form schema={schema}
              uiSchema={uiSchema}
              onSubmit={({formData}, e) => handleSaveToPC(formData)}
        />
    );
}

export default WebAMLModel;