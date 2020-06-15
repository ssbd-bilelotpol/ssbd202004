package pl.lodz.p.it.ssbd2020.ssbd04.controllers.converters;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Objects;

@Provider
public class ConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (Objects.equals(rawType, LocalDateTime.class)) {
            return (ParamConverter<T>) new LocalDateConverter();
        }
        return null;
    }
}
