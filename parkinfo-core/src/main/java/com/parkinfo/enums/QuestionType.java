package com.parkinfo.enums;

import io.swagger.models.auth.In;

public enum QuestionType {
    Radio{
        @Override
        public Integer getIndex() {
            return 0;
        }
    },JUDGE{
        @Override
        public Integer getIndex() {
            return 1;
        }
    };
    public abstract Integer getIndex();
}
