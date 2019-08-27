package com.parkinfo.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cnyuchu@gmail.com
 * @date 2018/11/8 9:27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    @ApiModelProperty(value = "请求说明")
    private final String result;

    @ApiModelProperty(value = "200正常500异常")
    private final Integer code;

    @ApiModelProperty(value = "消息说明")
    private final String message;

    @ApiModelProperty(value = "数据")
    private final T data;

    private Result(Builder<T> builder) {
        this.result = builder.result;
        this.code = builder.code;

        this.message = builder.message;
        this.data = builder.data;
    }

    public static<T> Result.Builder<T> builder() {
        return new Result.Builder<>();
    }

    public static class Builder<T> {
        private String result;

        private Integer code;

        private String message;

        private T data;

        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T>  message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T>  result(String result) {
            this.result = result;
            return this;
        }

        public Builder<T>  success() {
            this.result = "SUCCESS";
            this.code = 200;
            return this;
        }

        public Builder<T>  fail() {
            this.result = "FAILURE";
            this.code = 500;
            return this;
        }

        public Builder<T>  data(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            return new Result<>(this);
        }
    }

    @JsonIgnore
    public Boolean isFailed(){
        return this.code==500;
    }
}
