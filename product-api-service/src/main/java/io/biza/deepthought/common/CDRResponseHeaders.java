package io.biza.deepthought.common;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import lombok.Data;

@Data
public class CDRResponseHeaders {
  
  String contentType = MediaType.APPLICATION_JSON_VALUE;
  
  @NotNull
  Integer version;

  @NotNull
  UUID interactionId;
  
  Integer retryAfter;

}
