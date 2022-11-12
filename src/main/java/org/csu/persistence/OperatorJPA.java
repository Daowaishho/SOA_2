package org.csu.persistence;

import org.csu.VO.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface OperatorJPA extends
//        SpringDataJPA提供的简单数据操作接口
        JpaRepository<Operator, String>,
//SpringDataJPA提供的复杂查询接口
        JpaSpecificationExecutor<Operator>,
//  序列化接口
        Serializable{
}
