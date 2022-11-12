package org.csu.service;

import org.csu.VO.Operator;

import java.util.List;

public interface OperatorService {
    Operator insertOperator(String name, String password);
    void deleteOperator(String name);
    Operator updateOperator(String name, String password);
    List<Operator>selectOperators(String keywords);
}
