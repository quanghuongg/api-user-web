package com.api.user.mapper;

import com.api.user.entity.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface FeedbackMapper {
    @Insert("insert into feedback(contract_id,content, type,created) " +
            "values(#{contract_id},#{content},#{type},#{created})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void addFeedback(Feedback feedback);
}
