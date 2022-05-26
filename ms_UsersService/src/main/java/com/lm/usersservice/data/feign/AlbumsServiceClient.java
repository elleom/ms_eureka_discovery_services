package com.lm.usersservice.data.feign;

import com.lm.usersservice.ui.model.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author el_le
 * @since 5/13/2022 11:52 AM
 */
@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    @Retry(name = "albums-ws")
    @CircuitBreaker(name="albums-ws", fallbackMethod = "getAlbumsFallBack")
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);

    default List<AlbumResponseModel> getAlbumsFallBack(String id, Throwable exception) {
        return new ArrayList<>();
    }
}
