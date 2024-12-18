package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();


    @Test
    void sessionTest() {

        //Create a session (The server creates the session)
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //Save the response cookie in the request (From the client’s perspective, sending the cookie to the server)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // Add the cookie received from the server to the request and send it

        //Retrieve the session (From the server's perspective, now trying to retrieve it)
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        //Expire the session
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }
}
