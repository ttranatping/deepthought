package io.biza.deepthought.shared.util;

import java.util.Map;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import io.biza.deepthought.shared.exception.NotLoggedInException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPrincipalUtil {
        public static Jwt getJwtToken() throws NotLoggedInException {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (authentication instanceof AnonymousAuthenticationToken) {
            throw new NotLoggedInException("Anonymous user detected");
          }

          if(authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwt = (JwtAuthenticationToken)authentication;
            return jwt.getToken();
          }

          LOG.error("Presented an invalid token format, I can only handle JwtAuthenticationToken!");
          throw new NotLoggedInException("Invalid Token Format");

        }

        public static String getSubject() {
          try {
            return getJwtToken().getSubject();
          } catch (NotLoggedInException e) {
            return null;
          }
        }

        public static Map<String, Object> getClaimDetails() throws NotLoggedInException {
          return getJwtToken().getClaims();
        }

}
