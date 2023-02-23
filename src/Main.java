import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        final HttpClient httpClient = new VerySimpleHttpClient("localhost", 8080);

        httpClient.setUri("/test/2");
        httpClient.setMethod(HttpMethod.POST);
        httpClient.setHeader("Content-Type", "text/plain");

        final String response = httpClient.send("사그나드파ㅣㄴㅇ리ㅏ아");

        System.out.println("response = " + response);
    }

}
