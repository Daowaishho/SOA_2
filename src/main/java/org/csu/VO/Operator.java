package org.csu.VO;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Operator")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @Column(name = "password", nullable = false, length = 10)
    private String password;
}
