package com.hongik.joinhere.domain.qna.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQnaAnswerRequest {

    private Long qnaQuestionId;
    private String qnaAnswerContent;
}
