package com.developer.eg.remind.server.controller;

import com.developer.eg.remind.server.entity.Remind;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController //говорит Spring'у, чтобы тот отдавал JSON объекты
@RequestMapping("/reminder")
public class RemindController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Remind getReminder() {
        return createMockRemind();
    }

    private Remind createMockRemind() {
        Remind remind = new Remind();
        remind.setId(1);
        remind.setRemindDate(new Date());
        remind.setTitle("First object");
        return remind;
    }

}
