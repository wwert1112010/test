package org.example.httpTest.get;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MyResponse {
    private String version;
    private int code;
    private String status;

    private Map<String, String> headers;

    private String message;




    public static String buildResponse(Request request, String response) {
        MyResponse httpResponse = new MyResponse();
        httpResponse.setCode(200);
        httpResponse.setStatus("ok");
        httpResponse.setVersion(request.getVersion());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
//        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBytes().length));
        httpResponse.setHeaders(headers);
        request.getHeaders().forEach((key, value) -> httpResponse.getHeaders().put(key, value));
        httpResponse.setMessage(response);

        StringBuilder builder = new StringBuilder();
        buildResponseLine(httpResponse, builder);
        buildResponseHeaders(httpResponse, builder);
        buildResponseMessage(httpResponse, builder);
        return builder.toString();
    }


    private static void buildResponseLine(MyResponse response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getVersion()).append(" ").append(response.getCode()).append(" ")
                .append(response.getStatus()).append("\n");
    }

    private static void buildResponseHeaders(MyResponse response, StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        stringBuilder.append("\n");
    }

    private static void buildResponseMessage(MyResponse response, StringBuilder stringBuilder) {
        stringBuilder.append(response.getMessage());
    }

}


