package webdev.e_commerce.order_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntityAudited extends BaseEntity {

    @Column(name = "created_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
