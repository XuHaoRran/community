package com.learnspringboot.study2.cache;

import com.learnspringboot.study2.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO e = new TagDTO();
        e.setCategoryName("开发语言");
        e.setTags(Arrays.asList("js","php","css","html","java","node","python"));
        tagDTOS.add(e);
        return tagDTOS;
    }
}
