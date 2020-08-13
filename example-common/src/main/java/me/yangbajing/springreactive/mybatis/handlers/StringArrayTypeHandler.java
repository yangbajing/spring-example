package me.yangbajing.springreactive.mybatis.handlers;

import org.apache.ibatis.type.ArrayTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author yangjing (yang.xunjing@qq.com)
 * @date 2020-07-31 15:31
 */
@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class StringArrayTypeHandler extends ArrayTypeHandler {
}
