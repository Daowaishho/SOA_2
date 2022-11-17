package org.csu.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

// 通用响应类
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> implements Serializable {
//    返回状态信息和数据
    private int status;
    private String msg;
    private JsonNode apiList;
    private T data;

    public JsonNode getApiList(){
        return this.apiList;
    }


    public CommonResponse(int status){
        this.apiList = getAPIList();
        this.status = status;
    }

    public CommonResponse(int status,String msg){
        this.apiList = getAPIList();
        this.status = status;
        this.msg = msg;
    }

    public CommonResponse(int status,String msg,T data){
        this.apiList = getAPIList();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public CommonResponse(int status,T data){
        this.apiList = getAPIList();
        this.status = status;
        this.data = data;
    }

    public static <T> CommonResponse <T> createForSuccess(){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> CommonResponse <T> createForSuccess(T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> CommonResponse <T> createForSuccessMessage(String msg){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> CommonResponse <T> createForSuccess(String msg,T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> CommonResponse <T> createForError(){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDescription());
    }

    public static <T> CommonResponse <T> createForError(String msg){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> CommonResponse <T> createForError(int code,String msg){
        return new CommonResponse<T>(code,msg);
    }

    public static JsonNode getAPIList() {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            // 创建一个Document对象
            Document doc = db.parse("src/main/resources/config.xml");
            NodeList nodeList = doc.getElementsByTagName("JsonFileLocation");
            Node location = nodeList.item(0).getFirstChild();
            String jsonLocation = location.getNodeValue();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(new File(jsonLocation));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}


