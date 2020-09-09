package ru.paradigma.newsbot.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class Response<T> {

    public static final String STATUES_SUCCESS = "success";
    public static final String STATUES_FAILURE = "failure";

    private String status;
    private String message;

    private static final Logger logger = Logger.getLogger("");

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    public void send(HttpServletResponse response, int code) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String errorMessage;

        errorMessage = toJson();

        response.getWriter().println(errorMessage);
        response.getWriter().flush();
    }
}
