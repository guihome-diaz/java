-- Environment specifics values
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, DESCRIPTION, VERSION) VALUES (SEQ_PARAMETERS.nextval, 'environment', 'dev', 'current environment', 1);

-- Configuration for the public
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, DESCRIPTION, VERSION) VALUES (SEQ_PARAMETERS.nextval, 'app.version', '0.1', 'Public version of the application. This is not linked to GIT or Maven versions', 1);
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, DESCRIPTION, VERSION) VALUES (SEQ_PARAMETERS.nextval, 'app.mode', 'maintenance', 'Current mode of the application. To get the allowed values see the corresponding Java ENUM in the code: ', 1);

-- Technical configuration
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, DESCRIPTION, VERSION) VALUES (SEQ_PARAMETERS.nextval, 'db.version', '1', 'Current DB schema version', 1);
