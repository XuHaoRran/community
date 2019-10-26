package com.learnspringboot.study2.mapper;

import com.learnspringboot.study2.model.Comment;
import com.learnspringboot.study2.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into public.comment(id,parent_id,gmt_create,gmt_modified,content,type,commentator) values (#{id},#{parentId},#{gmtCreate},#{gmtModified},#{content},#{type},#{commentator})")
    void insert(Comment comment);
    @Update("update public.comment set comment_count = comment_count+1 where id = #{parentId}")
    void inCommentCount(@Param(value = "parentId") Integer parentId);
    @Select("select * from comment where parent_id = #{parentId} and type =#{type}")
    List<Comment> listByQuestionId(@Param(value = "parentId") Integer parentId,@Param(value = "type") Integer type);
    //获取上一级评论，用于二级评论
    @Select("select * from comment where id = #{id}")
    Comment getById(long id);

}
