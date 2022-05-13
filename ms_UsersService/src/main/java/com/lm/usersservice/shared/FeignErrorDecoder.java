package com.lm.usersservice.shared;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author el_le
 * @since 5/13/2022 1:33 PM
 */
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (s.contains("getAlbums"))
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Users albums not found");
                break;
            default:
                return new RuntimeException(response.reason());
        }
        return null;
    }
}
