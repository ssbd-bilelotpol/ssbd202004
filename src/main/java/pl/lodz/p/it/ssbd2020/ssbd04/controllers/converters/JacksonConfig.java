package pl.lodz.p.it.ssbd2020.ssbd04.controllers.converters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonConfig() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleModule module = new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        objectMapper.registerModule(module);
    }

    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }

}