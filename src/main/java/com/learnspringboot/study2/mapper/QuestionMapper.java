package com.learnspringboot.study2.mapper;

import com.learnspringboot.study2.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into public.question(title,description,gmt_create,gmt_modified,creator,tags) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    void create(Question question);
    @Select("select * from public.question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset ,@Param(value = "size")  Integer size);
    @Select("select count(1) from public.question")
    Integer count();
    @Select("select * from public.question where creator =#{id} limit #{offset},#{size}")
    List<Question> listById(@Param(value = "id") Integer id, @Param(value = "offset") Integer offset ,@Param(value = "size")  Integer size);
    @Select("select count(1) from public.question where creator=#{id}")
    Integer countById(@Param(value = "id") Integer id);
    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id") Integer id);
    @Update("update question set description = #{description},tags=#{tags},gmt_modified=#{gmtModified},title=#{title} where id=#{id}")
    void update(Question question);
    @Update("update question set view_count = view_count+1 where id = #{id}")
    void updateByExampleSelective(Question updateQuestion);
    @Update("update public.question set comment_count = comment_count+1 where id = #{id}")
    void inQuesCommentCount(Question updateQuestion);
    @Select("select comment_count from question where id = #{id}")
    int getCommentCount(@Param(value = "id") Integer id);
    @Select("select * from question where id !=#{id} and tags regexp #{tags}")
    List<Question> selectRelated(Question question);
    @Select("select count(*) from question where #{search} !=null and title regexp #{search} ")
    int countBySearch(@Param(value = "search") String search );
    @Select("select        *    from question where           title regexp #{search}")
    List<Question> selectBySearch(@Param(value = "search") String search );
}
