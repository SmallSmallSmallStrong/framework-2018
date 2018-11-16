package com.sdyijia.modules.sys.service;
import com.sdyijia.modules.base.service.BaseService;
import com.sdyijia.modules.sys.bean.Message;
import com.sdyijia.modules.sys.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends BaseService<MessageRepository, Message> {

}
