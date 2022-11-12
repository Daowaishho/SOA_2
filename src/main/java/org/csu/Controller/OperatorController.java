package org.csu.Controller;

import org.csu.VO.Operator;
import org.csu.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Operators")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @PostMapping("")
    Operator insertOperator(
            @RequestParam("name") String name,
            @RequestParam("password") String password){
        return operatorService.insertOperator(name, password);
    }

    @DeleteMapping("/{name}")
    void deleteOperator(
            @PathVariable("name") String name){
        operatorService.deleteOperator(name);
    };

    @PutMapping("/{name}")
    Operator updateOperator(
            @RequestParam("name") String name,
            @RequestParam("password") String password){
        return operatorService.updateOperator(name, password);
    }

    @GetMapping("/{name}")
    public List<Operator> selectOperators(
            @PathVariable("name") String name
    ){
        return operatorService.selectOperators(name);
    }



}
