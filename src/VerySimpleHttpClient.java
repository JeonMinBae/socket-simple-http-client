import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VerySimpleHttpClient implements HttpClient {

    final String NEW_LINE = "\n";

    private final String host;
    private final int port;
    private String uri;
    private Socket socket;
    private PrintWriter writer = null;

    private Map<String, String> headers;
    private HttpMethod method = HttpMethod.GET;
    private HttpVersion httpVersion = HttpVersion.VER1_1;

    public VerySimpleHttpClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.host = host;
        this.port = port;
    }


    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public void setHeader(String name, String value) {
        if (Objects.isNull(headers)) {
            headers = new HashMap<>(50);
        }

        headers.put(name, value);
    }

    public String send(String body) throws IOException {
        String response = null;
        try {
            request(body);

            response = getResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            writer.close();
            socket.close();

            return response;
        }
    }


    private void request(String body) throws IOException {
        String uri = Objects.nonNull(this.uri) ? this.uri : "/";
        String msg =
            method.name() + " " + uri + " " + httpVersion.getVersion() + NEW_LINE +
                "Connection: Close\n";

        msg = addHeaderToRequest(msg, body);

        boolean autoFlush = true;
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), autoFlush);
        writer.println(msg);
        writer.println();
        writer.println(body);
    }

    private String addHeaderToRequest(String msg, String body) {
        msg += "Host: " + host + ":" + port + NEW_LINE;

        if (Objects.nonNull(body)) {
            msg += "Content-Length: " + (body.length() + 1) + NEW_LINE;
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            msg += entry.getKey() + ": " + entry.getValue() + NEW_LINE;
        }

        return msg;
    }

    private String getResponse() throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String temp = "";
        String response = "";
        while (Objects.nonNull(temp)) {
            response += temp + NEW_LINE;
            temp = reader.readLine();
        }

        return response;
    }

}
