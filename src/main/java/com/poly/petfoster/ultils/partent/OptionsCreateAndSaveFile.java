package com.poly.petfoster.ultils.partent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionsCreateAndSaveFile {
    private List<String> acceptExtentions;
    private String defaultExtention;

    public String getDefaultExtention() {
        if (this.defaultExtention == null || this.defaultExtention.isBlank()) {
            return "jpg";
        }
        return this.defaultExtention;
    }
}
