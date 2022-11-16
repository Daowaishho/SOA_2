package org.csu.service.impl;

import org.csu.VO.Operator;
import org.csu.VO.Reserve;
import org.csu.common.CommonResponse;
import org.csu.persistence.OperatorJPA;
import org.csu.persistence.ReserveJPA;
import org.csu.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorJPA operatorJPA;
    @Autowired
    private ReserveJPA reserveJPA;

    /**
     * @param name Operator的名字
     * @param password Operator的密码
     * @Description: 插入新的Operator
     * @return CommonResponse<Operator> 成功则返回插入的新Operator，失败则返回失败信息
     */
    @Override
    public CommonResponse<Operator> insertOperator(String name, String password) {
//        当前用户名不存在
        if (operatorJPA.findByNameAndDelFlagEquals(name, Boolean.TRUE) == null){
            Operator operator = new Operator();
            operator.setName(name);
//        todo : 密码加密
            operator.setPassword(password);
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            return CommonResponse.createForSuccess(operatorJPA.save(operator));
        }
        return CommonResponse.createForError("该用户名\""+name+"\"已存在");
    }

    /**
     * @param name Operator的名字
     * @Description: 删除Operator
     * @return CommonResponse<Operator> 将该已存在的Operator的有效字段置False，且对该用户的所有Reserve字段也置False
     */
    @Override
    public CommonResponse<Operator> deleteOperator(String name) {
        Operator operator = operatorJPA.findByNameAndDelFlagEquals(name, Boolean.TRUE);
        if (operator != null){
            operator.setDelFlag(Boolean.FALSE);
            List<Reserve> reserveList = reserveJPA.findReservesByOperatorIdAndDelFlagEquals(operator.getOperatorId(), Boolean.TRUE);
            if (!reserveList.isEmpty()){
                for (Reserve reserve : reserveList){
                    reserve.setDelFlag(Boolean.FALSE);
                }
                reserveJPA.saveAll(reserveList);
            }
            CommonResponse.createForSuccess(operatorJPA.save(operator));
        }
        return CommonResponse.createForError("当前要删除的用户\""+name+"\"不存在");
    }

    /**
     * @param name Operator的名字
     * @param password Operator的密码
     * @Description: 修改operator的密码
     * @return CommonResponse<Operator> 修改该已存在的Operator的密码
     */
//    todo ： 可以同时修改账号和密码
    @Override
    public CommonResponse<Operator> updateOperator(String name, String password) {
//        当前用户存在
        Operator operator = operatorJPA.findByNameAndDelFlagEquals(name, Boolean.TRUE);
        if (operator != null){
            operator.setName(name);
//        todo : 密码加密
            operator.setPassword(password);
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            return CommonResponse.createForSuccess(operatorJPA.save(operator));
        }
        return CommonResponse.createForError("当前要更新的用户\""+name+"\"不存在");
    }

    /**
     * @param keywords 查询operator的关键字
     * @Description: 按照ketwords关键字模糊查询所有的operator
     * @return CommonResponse<List<Operator>> 所有满足keywords关键字的operator
     */
    @Override
    public CommonResponse<List<Operator>> selectOperators(String keywords) {
        return CommonResponse.createForSuccess(operatorJPA.findByNameLikeAndDelFlagEquals("%" + keywords + "%", Boolean.TRUE));
    }
}
