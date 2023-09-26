package ru.bardinpetr.itmo.lab2.web.auth.dto.serdes;

import jakarta.servlet.http.HttpServletRequest;
import ru.bardinpetr.itmo.lab2.utils.MapUtils;
import ru.bardinpetr.itmo.lab2.utils.dto.HttpRequestDeserializer;
import ru.bardinpetr.itmo.lab2.web.auth.dto.LoginRequestDTO;

import java.util.List;
import java.util.Optional;

public class LoginRequestDeserializer implements HttpRequestDeserializer<LoginRequestDTO> {

    @Override
    public Optional<LoginRequestDTO> deserialize(HttpServletRequest request) {
        var params = request.getParameterMap();
        if (!MapUtils.containsKeys(params, List.of("login", "password")))
            return Optional.empty();

        return Optional.of(new LoginRequestDTO(
                params.get("login")[0], params.get("password")[0]
        ));
    }
}
