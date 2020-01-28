package io.biza.deepthought.admin.support;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.biza.deepthought.data.payload.FormLabelValue;
import io.swagger.v3.oas.annotations.media.Schema;


public class LabelValueGeneratorOpenApiDescription {
  Logger LOG = LoggerFactory.getLogger(LabelValueGeneratorOpenApiDescription.class);

  public List<FormLabelValue> getFormLabels(Class<?> typeClass) {
    return getFormLabels(typeClass, "");
  }

  public List<FormLabelValue> getFormLabels(Class<?> typeClass, String prefix) {
    LOG.debug("Processing for {} with prefix of {}", typeClass.getName(), prefix);
    List<FormLabelValue> labels = new ArrayList<FormLabelValue>();
    Field[] allFields = typeClass.getDeclaredFields();
    for (Field field : allFields) {
      if (field.isAnnotationPresent(JsonUnwrapped.class)) {
        labels.addAll(getFormLabels(field.getType()));
      } else if (field.isAnnotationPresent(Schema.class)) {
        Schema modelProperty = field.getAnnotation(Schema.class);
        if (!modelProperty.hidden()) {
          Class<?> fieldType = field.getType();

          if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterType = (ParameterizedType) field.getGenericType();
            fieldType = (Class<?>) parameterType.getActualTypeArguments()[0];
            // LOG.debug("Field type has been reset to {} with name of {}", parameterType,
            // (Class<?>) parameterType.getActualTypeArguments()[0]);
          }

          if (fieldType.isAnnotationPresent(Schema.class)) {
            if (fieldType.isEnum()) {
              if (StringUtils.isEmpty(modelProperty.description())) {
                if (!StringUtils.isEmpty(fieldType.getAnnotation(Schema.class).description())) {
                  labels.add(FormLabelValue.builder()
                      .label(fieldType.getAnnotation(Schema.class).description())
                      .value(field.getName()).build());
                }
              }
            } else {
              labels.addAll(getFormLabels(fieldType, field.getName()));
            }
          } else {
            labels.add(FormLabelValue.builder().label(modelProperty.description())
                .value((StringUtils.isEmpty(prefix) ? "" : prefix + ".") + field.getName())
                .build());
          }
        }
      }
    }
    return labels;
  }

}
