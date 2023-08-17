package org.example.http;

public enum HttpStatusCode {
//    Client errors
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method not allowed"),
    CLIENT_ERROR_414_BAD_REQUEST(414, "Bad Request"),

//    Server Errors,
SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "Http Version Not Supported")
    ;

    @Override
    public String toString() {
        return "HttpStatusCode{" +
                "STATUS_CODE=" + STATUS_CODE +
                ", MESSAGE='" + MESSAGE + '\'' +
                '}';
    }

    private final int STATUS_CODE;

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    private final String MESSAGE;

    HttpStatusCode(int statusCode, String message) {
        STATUS_CODE = statusCode;
        MESSAGE = message;
    }
}
