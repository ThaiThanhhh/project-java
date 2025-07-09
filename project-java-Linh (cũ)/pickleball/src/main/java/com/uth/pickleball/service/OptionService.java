package com.uth.pickleball.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.uth.pickleball.repositories.IOptionRepository;
import com.uth.pickleball.model.Option;

@Service
public class OptionService {
    @Autowired
    private IOptionRepository optionRepository;

    public Option findOptionByQuestionIdAndValue(Long questionId, String value) {
        return optionRepository.findByQuestionIdAndValue(questionId, value);
    }

}
