package com.flightdetails.Payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min = 2,message = "Title should contain min 2 character")
    private String title;
    @Size(min = 2,message = "description should contain min 2 character")
    private String description;
    @NotEmpty
    @Size(min = 2,message = "content should contain min 2 character")
    private String content;
}
