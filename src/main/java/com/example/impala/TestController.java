package com.example.impala;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridge.impalaswitch.GetAuth;
import com.bridge.impalaswitch.LoginFormDto;
import com.bridge.impalaswitch.MnoTransfer;

@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/")
    public String index() throws Exception {
        LoginFormDto loginForm = new LoginFormDto();
        loginForm.username = "mugambi";
        loginForm.password = "mugambi";

        logger.info(loginForm.toString());

        GetAuth getAuth = new GetAuth();
        String sessionId = getAuth.send(loginForm);

        MnoTransfer testMnoTransfer = new MnoTransfer();
        return testMnoTransfer.send(sessionId);
    }

}
