package com.haiyishuzi.haiyishuzijcm.vo;

/**
 * FileName:ExamplesListEntity.java
 * 类的详细说明
 *  ExamplesFragment 测试组件列表用到的listEntity
 * @author lavine
 * @Date 2019-07-26 14:44
 * @version:1.0.0
 */
public class ExamplesListEntity {
    private String author;//作者
    private String componentName;//组件名称
    private Integer number;//编号

    public ExamplesListEntity(String author,String componentName,Integer number){
        this.author = author;
        this.componentName = componentName;
        this.number = number;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
