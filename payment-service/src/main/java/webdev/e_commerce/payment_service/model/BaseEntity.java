package webdev.e_commerce.payment_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    @Id
    private UUID id;
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
