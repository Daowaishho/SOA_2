package org.csu.VO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "Reserve")
public class Reserve implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reserveId", nullable = false, length = 10)
    private Integer reserveId;
    @Column(name = "roomId", nullable = false, length = 10)
    private Integer roomId;
    @Column(name = "operatorId", nullable = false, length = 10)
    private Integer operatorId;
    @Column(name = "beginTime", nullable = false)
    private Date beginTime;
    @Column(name = "endTime", nullable = false)
    private Date endTime;
}
