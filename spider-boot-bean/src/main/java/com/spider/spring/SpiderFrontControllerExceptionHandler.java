package com.spider.spring;

import com.spider.model.vo.JsonResult;
import com.spider.model.vo.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 前端异常控制器
 *
 * @author liuzhongkai
 */
@RestControllerAdvice
@ConditionalOnProperty(prefix = "spider.common.try-web-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SpiderFrontControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderFrontControllerExceptionHandler.class);

    /**
     * 捕获异常以json方式显示
     *
     * @param e 异常
     * @return json包装类
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Throwable.class)
    public JsonResult defaultErrorHandler(Throwable e) {
        LOGGER.error("front controller to capture exception:", e);
        return new JsonResult(new ResultInfo(String.format("Exception{class=%s, message=%s}", e.getClass().getCanonicalName(), e.getMessage()), "2"));
    }

}
