package com.poly.petfoster.request.posts;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostMediaUpdateRequest {
    private Integer id;
    private Integer index;
    private MultipartFile file;
}
