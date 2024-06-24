package com.xc.myblog.controller;

import com.xc.myblog.common.lang.Result;
import com.xc.myblog.entity.Label;
import com.xc.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classic")
public class ClassicController {
    @Autowired
    LabelService labelService;
    @GetMapping("/labels")
    public Result getAllLabels() {
        List<Label> allLabel = labelService.getAllLabel();
        return Result.success(allLabel);
    }

    @GetMapping("/labelsAndRecords")
    public Result getAllLabelsAndRecords() {
        List<Label> allLabel = labelService.getAllLabelAndNum();
        return Result.success(allLabel);
    }
}
