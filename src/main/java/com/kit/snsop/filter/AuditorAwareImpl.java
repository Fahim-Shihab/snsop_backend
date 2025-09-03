package com.kit.snsop.filter;

import com.kit.snsop.common.util.Defs;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component("auditorProvider")
@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        try {

            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

            if (attributes instanceof ServletRequestAttributes servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                String userIdStr = request.getHeader(Defs.HEADER.X_USER_ID);
                if (userIdStr != null && !userIdStr.isEmpty()) {
                    Long userId = Long.valueOf(userIdStr);
                    return Optional.of(userId);
                }else{
                    return Optional.of(Defs.DEFAULT_USER_ID);
                }
            }else{
                Long userFromMq = RequestUserContext.getUser();
                if (userFromMq != null) {
                    return Optional.of(userFromMq);
                }
            }
            return Optional.of(Defs.DEFAULT_USER_ID);
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
        return Optional.of(Defs.DEFAULT_USER_ID);
    }
}
