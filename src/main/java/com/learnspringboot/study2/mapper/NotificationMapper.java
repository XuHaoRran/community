package com.learnspringboot.study2.mapper;

import com.learnspringboot.study2.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(notifier,receiver,outerid,type,gmt_create,status) values (#{notifier},#{receiver},#{outerid},#{type},#{gmt_create},#{status})")
    void insert(Notification notification);
    //查看自己未读通知
    @Select("select * from notification where receiver = #{receiver} and status = 0")
    List<Notification> list(@Param("receiver") Integer receiver);
    //讲未读通知变成已读的
    @Update("update notification set status = 1 where receiver = #{receiver} and outerid = #{outerid} and status = 0")
    void haveNotificationRead(@Param(value = "receiver") Integer receiver, @Param("outerid") Integer outerid);
    @Select("select count(1) from public.notification where status = 0 and notifier = #{notifier}")
    Integer count(@Param(value = "notifier")Integer notifier);
}
