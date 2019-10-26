package com.learnspringboot.study2.service;

import com.learnspringboot.study2.dto.PageDTO;
import com.learnspringboot.study2.dto.QuestionDTO;
import com.learnspringboot.study2.exception.CustomizeErrorCode;
import com.learnspringboot.study2.exception.CustomizeException;
import com.learnspringboot.study2.mapper.QuestionMapper;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import com.sun.deploy.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question的属性拷贝到questionDTO上面的了啊
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,page,size);
        return pageDTO;
    }
    public List<QuestionDTO> listBySearch(String search){
        /*
        搜索列表
         */
        if (search!=null||search!=""){
            String[] tags = StringUtils.splitString(search," ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        int searchQuestionCount = questionMapper.countBySearch(search);
        List<Question> questions = questionMapper.selectBySearch(search);
        List<QuestionDTO> questionDTOList = questions.stream().map(question -> {

            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            BeanUtils.copyProperties(question,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
    public PageDTO listById(Integer id,Integer page, Integer size){
        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.listById(id,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question的属性拷贝到questionDTO上面的了啊
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.countById(id);
        pageDTO.setPagination(totalCount,page,size);

        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {


        Question question = questionMapper.getById(id);
        if (question == null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = dateFormat.format(new Date());

        //判断文章的id是否存在
        if (question.getId() == null) {
            question.setGmtCreate(datetime);
            question.setGmtModified(datetime);
            questionMapper.create(question);
        } else  {
            question.setGmtModified(datetime);
            questionMapper.update(question);
        }
    }
    //增加访问数
    public void inView(Integer id) {
        Question updateQuestion = questionMapper.getById(id);
//        Question updateQuestion = new Question();
        questionMapper.updateByExampleSelective(updateQuestion);
    }

    public List<QuestionDTO> selectRealated(QuestionDTO questionDTO) {
        if (questionDTO.getTags()==""||questionDTO.getTags()==null){
            return new ArrayList<>();
        }
        new StringJoiner("|");
        String[] tags = StringUtils.splitString(questionDTO.getTags(),",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTags(regexpTag);

        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q->{
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
