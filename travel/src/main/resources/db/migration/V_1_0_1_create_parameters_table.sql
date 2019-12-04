-- parameters table
CREATE TABLE PARAMETERS
  (
    PARAM_NAME                  VARCHAR2(100 CHAR)   CONSTRAINT "C_NN_PARAMS_NAME" NOT NULL,
    PARAM_VALUE                 VARCHAR2(255 CHAR)   CONSTRAINT "C_NN_PARAMS_VALUE" NOT NULL,
    IS_ACTIVE                   NUMBER(1, 0)         DEFAULT 1 CONSTRAINT "C_NN_PARAMS_IS_ACTIVE" NOT NULL,
    DESCRIPTION                 VARCHAR2(1500 CHAR),
    CREATION_DATE               TIMESTAMP            CONSTRAINT "C_NN_PARAMS_CREATION_DATE" NOT NULL,
    MODIFICATION_DATE           TIMESTAMP            CONSTRAINT "C_NN_PARAMS_MODIFICATION_DATE" NOT NULL,
    CONSTRAINT PK_PARAMS PRIMARY KEY (PARAM_NAME)
  );

-- Indexes (performances). Use "UPPER" or "LOWER" for items that must be unique no matter the case
CREATE INDEX PARAMS_GET_ACTIVE_PARAM_IDX     ON PARAMETERS (UPPER(PARAM_NAME), IS_ACTIVE);
CREATE INDEX PARAMS_ACTIVE_IDX               ON PARAMETERS (IS_ACTIVE);

-- Object description
COMMENT ON COLUMN PARAMETERS.PARAM_NAME          IS 'Parameter name (upper-case)';
COMMENT ON COLUMN PARAMETERS.PARAM_VALUE         IS 'Parameter value';
COMMENT ON COLUMN PARAMETERS.IS_ACTIVE           IS 'Boolean flag. 1=parameter is enabled and should be taken into account, 0=ignore parameter';
COMMENT ON COLUMN PARAMETERS.DESCRIPTION         IS 'Parameter description';
COMMENT ON COLUMN PARAMETERS.CREATION_DATE       IS 'Object creation date-time';
COMMENT ON COLUMN PARAMETERS.MODIFICATION_DATE   IS 'Object last modification date-time';
