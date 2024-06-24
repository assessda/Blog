package com.xc.myblog.service;

import com.xc.myblog.entity.Label;

import java.util.List;

public interface LabelService {
    //查询所有的label
    public List<Label> getAllLabel();
    //查询所有的label以为记录数
    public List<Label> getAllLabelAndNum();
}
