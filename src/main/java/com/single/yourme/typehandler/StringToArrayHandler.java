package com.single.yourme.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 将apk_update中的message按照@_@分割成String数组
 *
 * @author: 1999single
 * @date: 2020/8/26 17:58
 */
@MappedJdbcTypes(JdbcType.VARCHAR)  //数据库类型
@MappedTypes({List.class})          //java数据类型
public class StringToArrayHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        StringBuffer sb = new StringBuffer();
        for (String s : strings) {
            sb.append(s).append("@_@");
        }
        preparedStatement.setString(i, sb.toString().substring(0, sb.toString().length() - 3));
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String[] arr = resultSet.getString(s).split("@_@");
        for (int p = 0; p < arr.length; p++) {
            arr[p] = "" + (p+1) + '.' + arr[p];
        }
        return Arrays.asList(arr);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String[] arr = resultSet.getString(i).split("@_@");
        for (int p = 0; p < arr.length; p++) {
            arr[p] = "" + (p+1) + '.' + arr[p];
        }
        return Arrays.asList(arr);
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String[] arr = callableStatement.getString(i).split("@_@");
        for (int p = 0; p < arr.length; p++) {
            arr[p] = "" + (p+1) + '.' + arr[p];
        }
        return Arrays.asList(arr);
    }
}
