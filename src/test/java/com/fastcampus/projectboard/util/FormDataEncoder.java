package com.fastcampus.projectboard.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@TestComponent
public class FormDataEncoder {

    private final ObjectMapper mapper; // ObjectMapper : 직렬화(객체 -> 전송 가능한 형태), 역직렬화를 할 때 사용하는 라이브러리

    public FormDataEncoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public String encode(Object obj) { // 객체(obj) -> Form data로 인코딩
        Map<String, String> fieldMap = mapper.convertValue(obj, new TypeReference<>() {});
        // mapper를 활용해 obj -> Map<String, String> 형식으로 변환
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>(); // MultiValueMap : 하나의 키에 여러 값을 가질 수 있음
        valueMap.setAll(fieldMap); //  각 키에 대한 여러 값을 가지는 MultiValueMap이 생성

        return UriComponentsBuilder.newInstance() // UriComponentsBuilder: URI 구성
                .queryParams(valueMap)
                .encode() // 인코딩, 특수 문자나 공백 등을 안전하게 변환
                .build()
                .getQuery();
    }

}
