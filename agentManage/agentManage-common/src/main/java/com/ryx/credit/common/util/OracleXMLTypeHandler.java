package com.ryx.credit.common.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.SQLXML;  
  
import oracle.sql.OPAQUE;  
import oracle.xdb.XMLType;  
  
import org.apache.ibatis.type.BaseTypeHandler;  
import org.apache.ibatis.type.JdbcType;  
  
public class OracleXMLTypeHandler extends BaseTypeHandler<Object> {  
  
    @Override  
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {  
        ps.setSQLXML(i, XMLType.createXML(ps.getConnection(), (String)parameter));
    }  
  
    @Override  
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {  
        return getXMLType(rs.getObject(columnName));  
    }  
  
    @Override  
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {  
        return getXMLType(rs.getObject(columnIndex));  
    }  
  
    @Override  
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {  
        return getXMLType(cs.getObject(columnIndex));  
    }  
  
    private String getXMLType(Object obj) throws SQLException {  
        OPAQUE o = (OPAQUE)obj;  
        XMLType xt = XMLType.createXML(o); 
        return xt.getString();  
    }  
}  