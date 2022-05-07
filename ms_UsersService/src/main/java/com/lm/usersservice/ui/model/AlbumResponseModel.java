package com.lm.usersservice.ui.model;

import lombok.*;

/**
 * @author el_le
 * @since 07/05/2022 21:49
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseModel {

    private String id;
    private String userId;
    private String name;
    private String description;

}
