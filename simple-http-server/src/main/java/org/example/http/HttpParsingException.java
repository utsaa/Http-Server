package org.example.http;

public class HttpParsingException extends  Exception{

    private final HttpStatusCode errorCode;

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }

    public HttpParsingException(HttpStatusCode errorCode) {
        super(errorCode.getMESSAGE());
        this.errorCode = errorCode;
    }
}
