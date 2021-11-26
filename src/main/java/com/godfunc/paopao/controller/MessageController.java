package com.godfunc.paopao.controller;

import com.godfunc.paopao.result.R;
import com.godfunc.paopao.service.IMessageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Godfunc
 * @date 2020/2/8 17:38
 */
@RestController
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("{token}/send")
    public R send(@PathVariable String token, @RequestParam(required = false) String type, @RequestParam String msg, @RequestParam(required = false) String link) {
        return messageService.send(token, type, msg, link);
    }

    @RequestMapping("{token}/sendToAlias/{alias}")
    public R sendAlias(@PathVariable String token, @PathVariable String alias, @RequestParam(required = false) String type, @RequestParam String msg,@RequestParam(required = false) String link) {
        return messageService.send2Alias(token, alias, type, msg, link);
    }

    @RequestMapping("{token}/sendToGroup/{groupUid}")
    public R send2Group(@PathVariable String token, @PathVariable String groupUid, @RequestParam(required = false) String type, @RequestParam String msg, @RequestParam(required = false) String link) {
        return messageService.send2Group(token, groupUid, type, msg, link);
    }
}
