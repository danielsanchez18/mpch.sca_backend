package com.mpch.control_de_asistencias.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static Map<String, Object> successResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, String> errorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return response;
    }

}
