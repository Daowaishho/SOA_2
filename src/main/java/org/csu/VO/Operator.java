package org.csu.VO;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Operator")
public class Operator implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operatorId", nullable = false, length = 10)
    private Integer operatorId;

    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Column(name = "password", nullable = false, length = 10)
    private String password;
}
