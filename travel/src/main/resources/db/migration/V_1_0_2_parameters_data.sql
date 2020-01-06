-- Environment specifics values
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'environment', 'dev', 'java.lang.String', 'current environment', 1, 0);

-- Configuration for the public
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'app.version', '0.1', 'java.lang.String', 'Public version of the application. This is not linked to GIT or Maven versions', 1, 0);
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'app.mode', 'maintenance', 'java.lang.String', 'Current mode of the application. To get the allowed values see the corresponding Java ENUM in the code: ', 1, 0);

-- Technical configuration
INSERT INTO PARAMETERS(PARAM_ID, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DESCRIPTION, VERSION, IS_SENSITIVE) VALUES (SEQ_PARAMETERS.nextval, 'db.version', '1', 'java.lang.String', 'Current DB schema version', 1, 0);
