package org.anonymous.chapter01;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Request {

    private final InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        // Read a set of characters from the socket
        //StringBuilder request = new StringBuilder(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        String request = new String(buffer, StandardCharsets.UTF_8);

        /*for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }*/
        System.out.println("request = " + request);
        uri = parseUri(request);
        System.out.println("uri = " + uri);
    }

    // GET /index.html HTTP/1.1
    // 两个空格之间即为 uri
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public String getUri() {
        return uri;
    }

}