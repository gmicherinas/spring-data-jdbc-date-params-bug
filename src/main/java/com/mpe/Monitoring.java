package com.mpe;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author George Micherinas
 * Date 12/05/2021
 */
@Table("monitoring")
@AccessType(AccessType.Type.PROPERTY)
public class Monitoring implements Serializable {
    @Id
    private Long id;

    private final String msg;

    @Column("preview_start")
    private final LocalDateTime previewStart;

    @Column("actual_start")
    private final LocalDateTime actualStart;

    public Monitoring(String msg, LocalDateTime previewStart, LocalDateTime actualStart) {
        this.msg = msg;
        this.previewStart = previewStart;
        this.actualStart = actualStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Monitoring))
            return false;
        Monitoring that = (Monitoring) o;
        return Objects.equals(id, that.id) && Objects.equals(msg, that.msg) && Objects.equals(previewStart, that.previewStart) && Objects
                .equals(actualStart, that.actualStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msg, previewStart, actualStart);
    }

    public Long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getPreviewStart() {
        return previewStart;
    }

    public LocalDateTime getActualStart() {
        return actualStart;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Monitoring{");
        sb.append("id=").append(id);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", previewStart=").append(previewStart);
        sb.append(", actualStart=").append(actualStart);
        sb.append('}');
        return sb.toString();
    }
}
