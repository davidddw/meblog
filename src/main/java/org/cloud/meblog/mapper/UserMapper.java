package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.User;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllOrderBy(@Param("column") String orderColumn, @Param("orderDir") String orderDir);

    int deleteRelationshipFromUser(@Param("id") Long id);

    User selectRoleByUsername(@Param("name") String username);
}
