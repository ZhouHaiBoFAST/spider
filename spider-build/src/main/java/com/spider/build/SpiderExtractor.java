package com.spider.build;


import com.spider.build.internals.ServiceBootstrap;

import java.util.Optional;

/**
 * @author liuzhongkai
 */
public class SpiderExtractor {


    public static Extractor getExtractor() {
        return Actual.extractor;

    }

    public static <T> Optional<T> getInstance(Class<T> clazz) {
        return Actual.extractor.getInstance(clazz);
    }

    private static class Actual {

        private volatile static Extractor extractor = ServiceBootstrap.loadFirst(Extractor.class);


    }


}
