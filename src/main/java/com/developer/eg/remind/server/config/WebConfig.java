package com.developer.eg.remind.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("com.developer.eg.remind.server")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { //Конвертируем данные в JSON
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON)); //Указали в какой тип конвертить

        converters.add(converter); //Добавили наш конвертер к конвертерам
    }
}
