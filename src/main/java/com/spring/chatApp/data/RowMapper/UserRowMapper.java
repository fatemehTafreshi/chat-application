package com.spring.chatApp.data.RowMapper;

import com.spring.chatApp.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.spring.chatApp.configuration.CommonMethods.uuidFixEndian;

public class UserRowMapper implements RowMapper<UserDto> {
//    static List<String> roles=new ArrayList<>();

    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDto user = new UserDto();

            user.setId(uuidFixEndian(rs.getBytes("id")));

            user.setUsername(rs.getString("username"));

            user.setEnabled(rs.getBoolean("enabled"));



        user.setAuthority(rs.getString("authorities"));


        return user;
    }
}
