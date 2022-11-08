package org.csu.persistence;

import org.csu.VO.Operator;
import org.springframework.data.repository.Repository;
import java.util.List;

@org.springframework.stereotype.Repository
interface OperatorRepository extends Repository<Operator, Long> {
    List<Operator> getOperatorsByName(String name);
}
