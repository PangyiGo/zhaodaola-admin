package com.sise.zhaodaola.tool;

import com.sise.zhaodaola.tool.utils.StringUtils;
import org.apache.el.stream.Optional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: PangYi
 * @Date 2020/3/75:40 下午
 */
public class StreamTest {

    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("a", "b", "cc", "dd", "11");
        stringStream.map(String::length).forEach(System.out::println);
    }
}
