package org.csu.VO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", nullable = false, length = 10)
    private Integer roomId;
    @Column(name = "available", nullable = false)
    private Boolean available;
}
