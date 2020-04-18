package pl.lodz.p.it.ssbd2020.ssbd04.utils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Abstrakcyjna encja pozwalająca zdefiniować wspólne dane dla wszystkich encji.
 */
@MappedSuperclass
public class AbstractEntity {
    @NotNull
    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    @PrePersist
    private void prePersist() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        modificationDateTime = LocalDateTime.now();
    }

    @Version
    private Long version;

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public Long getVersion() {
        return version;
    }
}
