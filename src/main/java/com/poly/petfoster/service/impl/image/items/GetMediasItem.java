package com.poly.petfoster.service.impl.image.items;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetMediasItem {
    private byte[] data;
    private File originaFile;
    private String contentType;
}
