const toOpenApi = require('json-schema-to-openapi-schema');
const fs = require('fs');

const schema = require('../webaml.schema.json');
const convertedSchema = toOpenApi(schema);

let data = JSON.stringify(convertedSchema, null, 2);
fs.writeFileSync('openapi-request-schema.json', data);
