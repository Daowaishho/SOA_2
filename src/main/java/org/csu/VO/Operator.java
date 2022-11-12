package org.csu.VO;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Operator")
@GenericGenerator(name = "user-uuid", strategy = "uuid")
// uuid为通用唯一识别码
public class Operator implements Serializable {
    @Id
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @Column(name = "password", nullable = false, length = 10)
    private String password;
}
