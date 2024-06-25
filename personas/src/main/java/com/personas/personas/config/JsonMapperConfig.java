package com.personas.personas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapperConfig
{
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeEnumCoercion() {
        return builder -> builder.postConfigurer(this::configureEnumCoercion);
    }
    private void configureEnumCoercion(ObjectMapper objectMapper) {
        objectMapper.coercionConfigFor(LogicalType.Enum)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    }
}