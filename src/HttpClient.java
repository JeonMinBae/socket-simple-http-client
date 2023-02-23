import java.io.IOException;

public interface HttpClient {
    void setUri(String uri);
    void setMethod(HttpMethod method);
    void setHeader(String name, String value);
    String send(String msg) throws IOException;

}
