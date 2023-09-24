package ru.bardinpetr.itmo.lab2.web.dto.serdes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface HttpRequestDeserializer<T> {
    Optional<T> deserialize(HttpServletRequest request);
}
