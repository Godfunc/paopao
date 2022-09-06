package com.godfunc.paopao.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(@NonNull PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull ResultSet rs, String columnName) throws SQLException {
        return ts2LocalDateTime(rs.getLong(columnName));
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull ResultSet rs, int columnIndex) throws SQLException {
        return ts2LocalDateTime(rs.getLong(columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(@NonNull CallableStatement cs, int columnIndex) throws SQLException {
        return ts2LocalDateTime(cs.getLong(columnIndex));
    }

    @NonNull
    private LocalDateTime ts2LocalDateTime(@NonNull Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timeStamp));
        return LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

}