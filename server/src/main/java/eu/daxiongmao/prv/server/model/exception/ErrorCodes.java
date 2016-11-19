package eu.daxiongmao.prv.server.model.exception;

public interface ErrorCodes {

    final static String TECHNICAL_ERROR = "TECH_ERR_0001";
    final static String TECHNICAL_ERROR_DUPLICATE_KEY = "TECH_ERR_0002";

    final static String REQUEST_ERROR_NO_ID = "RQST_ERR_0001";
    final static String REQUEST_ERROR_INVALID_EMAIL = "RQST_ERR_0002";

    final static String BUSINESS_ERROR_ITEM_NOT_FOUND = "FUNC_ERR_0001";
    final static String BUSINESS_ERROR_RELATED_ITEM_NOT_FOUND = "FUNC_ERR_0002";

}
