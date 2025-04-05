package com.monitor.server.service;

import com.monitor.server.model.MonitorData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMonitorData(MonitorData data) {
        //System.out.println("data"+data);
        messagingTemplate.convertAndSend("/topic/monitor"+data.getNodeIdentifier(), data);
    }
}