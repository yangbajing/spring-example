package me.yangbajing.springreactive.mybatis.handlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Objects;

public abstract class AbstractPgTypeHandler<T extends Object> extends BaseTypeHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(AbstractPgTypeHandler.class);
    public final String pgType;
    protected final Class<T> rawClass;

    public AbstractPgTypeHandler(String pgType, Class<T> rawClass) {
        if (log.isDebugEnabled()) {
            log.trace("{}({})", getClass().getSimpleName(), pgType);
        }
        this.pgType = Objects.requireNonNull(pgType, "The pgType argument cannot be null.");
        this.rawClass = Objects.requireNonNull(rawClass, "The rawClass argument cannot be null.");
    }

    abstract protected String write(T value);

    abstract protected T parse(String text);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGobject jsonbObject = new PGobject();
        jsonbObject.setType(pgType);
        jsonbObject.setValue(write(parameter));
        ps.setObject(i, parameter, Types.OTHER);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGobject obj = rs.getObject(columnName, PGobject.class);
        if (obj == null) {
            return null;
        }
        if (!pgType.equals(obj.getType())) {
            throw new IllegalStateException(String.format("The result type of column name %s need be %s", columnName, pgType));
        }

        return parse(obj.getValue());
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGobject obj = rs.getObject(columnIndex, PGobject.class);
        if (obj == null) {
            return null;
        }
        if (!pgType.equals(obj.getType())) {
            throw new IllegalStateException(String.format("The result type of column index %d need be %s", columnIndex, pgType));
        }

        return parse(obj.getValue());
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        PGobject obj = cs.getObject(columnIndex, PGobject.class);
        if (obj == null) {
            return null;
        }
        if (!pgType.equals(obj.getType())) {
            throw new IllegalStateException(String.format("The callable type of column index %s need be %s", columnIndex, pgType));
        }

        return parse(obj.getValue());
    }
}
