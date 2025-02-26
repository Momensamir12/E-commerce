package webdev.e_commerce.product_catalog_service.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static webdev.e_commerce.product_catalog_service.utils.Constants.DTO_DATE_TIME_FORMAT;

public record BaseResponseDTO(
        String id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DTO_DATE_TIME_FORMAT)
        LocalDateTime createdTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DTO_DATE_TIME_FORMAT)
        LocalDateTime lastModifiedTime

) {
}
