package org.csu.Controller;

import org.csu.VO.Operator;
import org.csu.common.CommonResponse;
import org.csu.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Operators")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @PostMapping("/")
    CommonResponse<Operator> insertOperator(
            @RequestParam("name") String name,
            @RequestParam("password") String password){
        return operatorService.insertOperator(name, password);
    }

    @DeleteMapping("/{name}")
    CommonResponse<Operator> deleteOperator(
            @PathVariable("name") String name){
        return operatorService.deleteOperator(name);
    }

    @PutMapping("/{name}")
    CommonResponse<Operator> updateOperator(
            @PathVariable("name") String name,
            @RequestParam("password") String password){
        return operatorService.updateOperator(name, password);
    }

    @GetMapping("/{name}")
    CommonResponse<List<Operator>> selectOperators(
            @PathVariable("name") String name
    ){
        return operatorService.selectOperators(name);
    }
}
