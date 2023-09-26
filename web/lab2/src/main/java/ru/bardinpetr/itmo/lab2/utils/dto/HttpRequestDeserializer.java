package ru.bardinpetr.itmo.lab2.utils.dto;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface HttpRequestDeserializer<T> {
    Optional<T> deserialize(HttpServletRequest request);
}
