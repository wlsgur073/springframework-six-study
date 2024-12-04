package springsix.spring6.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor {
    @Override
    public String execute(URI uri) throws IOException {
        String resp;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        // `Reader`는 `AutoCloseable`를 상속하기에 자동으로 close 해준다.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            resp = br.lines().collect(Collectors.joining());
        }
        return resp;
    }
}
