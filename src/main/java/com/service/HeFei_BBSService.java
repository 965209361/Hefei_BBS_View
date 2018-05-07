package com.service;

import com.model.Hefei_bbs;

import java.util.List;

public interface HeFei_BBSService {

    void hefei_BBs();

    List<Hefei_bbs> QueryNews(int page, int pageCount);

    Integer QueryPageCount();

}
