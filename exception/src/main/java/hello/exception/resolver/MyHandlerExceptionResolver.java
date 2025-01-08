package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); //서블릿 컨테이너에는 400오류로 되는거임
                return new ModelAndView(); //그냥 새로운 modelandview를 반환해줘 (채워서 반환할 수도 있고, 빈 채로 반환도 가능)
                // 빈값으로 넘기면 그냥 계속 리턴돼서 서블릿 컨테이너, WAS까지 정상적으로 리턴됨. 예외를 그냥 먹어버린거야
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
