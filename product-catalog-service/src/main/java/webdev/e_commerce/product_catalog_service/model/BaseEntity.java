package webdev.e_commerce.product_catalog_service.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data

public abstract class BaseEntity implements Serializable {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime lastModifiedTime;

}