package org.csu.service.impl;

import org.csu.VO.Operator;
import org.csu.persistence.OperatorJPA;
import org.csu.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorJPA operatorJPA;

    @Override
    public Operator insertOperator(String name, String password) {
//        当前用户名不存在
        if (operatorJPA.findByName(name) == null){
            Operator operator = new Operator();
            operator.setName(name);
//        todo : 密码加密
            operator.setPassword(password);
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            return operatorJPA.save(operator);
        }
        return null;
    }

    @Override
    public void deleteOperator(String name) {
        Operator operator = operatorJPA.findByName(name);
        if (operator != null){
            operatorJPA.delete(operator);
        }
    }

    @Override
    public Operator updateOperator(String name, String password) {
//        当前用户存在
        Operator operator = operatorJPA.findByName(name);
        if (operator != null){
            Operator newOperator = new Operator();
            newOperator.setName(name);
//        todo : 密码加密
            newOperator.setPassword(password);
            newOperator.setOperatorId(operator.getOperatorId());
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            return operatorJPA.save(newOperator);
        }
        return null;
    }

    @Override
    public List<Operator> selectOperators(String keywords) {
        return operatorJPA.findByNameLike("%" + keywords + "%");
    }
}
