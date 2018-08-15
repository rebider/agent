package com.ryx.credit.commons.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description：TreeVO
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class Tree implements java.io.Serializable {

    private static final long serialVersionUID = 980682543891282923L;
    private String id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;
    @JsonInclude(Include.NON_NULL)
    private List<Tree> children; // null不输出
    private String iconCls;
    private String pid;
    private BigDecimal tType;
    /**
     * ajax,iframe,
     */
    private String openMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public void setState(Integer opened) {
        this.state = (null != opened && opened == 1) ? "open" : "closed";
    }
    
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }


    public String getOpenMode() {
        return openMode;
    }

    public void setOpenMode(String openMode) {
        this.openMode = openMode;
    }

    public BigDecimal gettType() {
        return tType;
    }

    public void settType(BigDecimal tType) {
        this.tType = tType;
    }


    public static void setState(Tree tree){
        if(tree==null)return;
        if(tree.getChildren()!=null && tree.getChildren().size()>0){
            tree.setState(0);
            List<Tree> trees = tree.getChildren();
            for (Tree tree1 : trees) {
                setState(tree1);
            }
        }else{
            tree.setState(1);
        }
    }
}
