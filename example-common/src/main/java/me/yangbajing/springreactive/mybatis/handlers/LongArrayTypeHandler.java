package me.yangbajing.springreactive.mybatis.handlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author yangjing (yang.xunjing@qq.com)
 * @date 2020-07-31 15:03
 */
@MappedTypes(Long[].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class LongArrayTypeHandler extends org.apache.ibatis.type.ArrayTypeHandler {
}
