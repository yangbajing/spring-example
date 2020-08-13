package me.yangbajing.springreactive.mybatis.handlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author yangjing (yang.xunjing@qq.com)
 * @date 2020-07-31 14:58
 */
@MappedTypes(Integer[].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class IntegerArrayTypeHandler extends org.apache.ibatis.type.ArrayTypeHandler {
}
