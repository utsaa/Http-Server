package org.example.http;

public class HttpRequest extends HttpMessage{
    private HttpMethod method;
    private String requestTarget;

    private String originalHttpVersion;
    private HttpVersion bestCompatibleHttpVersion;



    public String getRequestTarget() {
        return requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public void setOriginalHttpVersion(String originalHttpVersion) {
        this.originalHttpVersion = originalHttpVersion;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public void setBestCompatibleHttpVersion(HttpVersion bestCompatibleHttpVersion) {
        this.bestCompatibleHttpVersion = bestCompatibleHttpVersion;
    }


    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method: HttpMethod.values()){
            if (methodName.equals(method.name()))
                this.method=method;
            return;
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }



    HttpRequest(){

    }

    public void setHttpVersion(String originalHttpVersion) throws HttpParsingException, BadHttpVersionException {
        this.originalHttpVersion=originalHttpVersion;
        bestCompatibleHttpVersion=HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (bestCompatibleHttpVersion==null){
            throw new HttpParsingException(
                    HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED
            );
        }
    }

    public void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget==null || requestTarget.length()==0){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget=requestTarget;
    }
}
