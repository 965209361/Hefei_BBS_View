package com.dao;

import com.model.Hefei_bbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Hefei_bbsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hefei_bbs record);

    int insertSelective(Hefei_bbs record);

    Hefei_bbs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hefei_bbs record);

    int updateByPrimaryKey(Hefei_bbs record);

    Integer QueryPageCount();

    List<Hefei_bbs> QueryNews(@Param("page")int page,@Param("pageCount")int pageCount);
}