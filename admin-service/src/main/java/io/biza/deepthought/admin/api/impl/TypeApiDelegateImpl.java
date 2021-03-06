package io.biza.deepthought.admin.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.support.LabelValueEnumInterface;
import io.biza.deepthought.admin.Constants;
import io.biza.deepthought.admin.api.delegate.TypeApiDelegate;
import io.biza.deepthought.admin.support.LabelValueDerivedInterface;
import io.biza.deepthought.admin.support.LabelValueGeneratorOpenApiDescription;
import io.biza.deepthought.data.enumerations.FormFieldType;
import io.biza.deepthought.data.payloads.FormLabelValue;
import io.biza.deepthought.data.payloads.ResponseGetTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class TypeApiDelegateImpl implements TypeApiDelegate {

  @Override
  public ResponseEntity<ResponseGetTypes> getTypes(List<FormFieldType> labelTypes) {
    ResponseGetTypes getTypesResponse = ResponseGetTypes.builder().build();

    labelTypes.forEach(oneFieldType -> {
      if (Constants.TYPE_FIELD_MAPPINGS.containsKey(oneFieldType)) {
        Class<?> targetClass = Constants.TYPE_FIELD_MAPPINGS.get(oneFieldType);
        List<FormLabelValue> fieldValue = new ArrayList<FormLabelValue>();

        LOG.debug("Check {} says {} and enum assignable {} and ApiModel present {}", targetClass,
            targetClass.isEnum(), LabelValueEnumInterface.class.isAssignableFrom(targetClass),
            targetClass.isAnnotationPresent(Schema.class));
        if (targetClass.isEnum() && LabelValueEnumInterface.class.isAssignableFrom(targetClass)) {
          for (Object b : targetClass.getEnumConstants()) {
            LabelValueEnumInterface value = (LabelValueEnumInterface) b;
            fieldValue
                .add(FormLabelValue.builder().label(value.label()).value(value.toString()).build());
          }

          getTypesResponse.setTypeField(oneFieldType.name(), fieldValue);
        } else if (LabelValueDerivedInterface.class.isAssignableFrom(targetClass)) {
          try {
            LabelValueDerivedInterface value = LabelValueDerivedInterface.class
                .cast(targetClass.getDeclaredConstructor().newInstance());
            getTypesResponse.setTypeField(oneFieldType.name(), value.getFormLabels());
          } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
              | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            LOG.error(
                "Encountered an error when attempting to cast {} to a LabelValueDerivedInterface class with error: {}",
                targetClass.getSimpleName(), e.getMessage());
          }
        } else if (targetClass.isAnnotationPresent(Schema.class)) {
          getTypesResponse.setTypeField(oneFieldType.name(),
              new LabelValueGeneratorOpenApiDescription().getFormLabels(targetClass));
        } else {
          LOG.error(
              "Unable to create type fields response, found object but unable to determine how to interogate it");
        }
      }
    });

    return ResponseEntity.ok(getTypesResponse);
  }

}
