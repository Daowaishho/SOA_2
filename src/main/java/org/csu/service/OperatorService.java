package org.csu.service;

import org.csu.VO.Operator;
import org.csu.common.CommonResponse;

import java.util.List;

public interface OperatorService {
    CommonResponse<Operator> insertOperator(String name, String password);
    CommonResponse<Operator> deleteOperator(String name);
    CommonResponse<Operator> updateOperator(String name, String password);
    CommonResponse<List<Operator>>selectOperators(String keywords);
}
