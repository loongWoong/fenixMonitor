package com.monitor.server.controller;

import com.monitor.server.model.MonitorData;
import com.monitor.server.model.ProcessInfo;
import com.monitor.server.mapper.MonitorDataMapper;
import com.monitor.server.mapper.ProcessInfoMapper;
import com.monitor.server.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MonitorController {

    @Autowired
    private MonitorDataMapper monitorDataMapper;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private ProcessInfoMapper processInfoMapper;

    @PostMapping("/monitor")
    public ResponseEntity<?> receiveMonitorData(@RequestBody MonitorData data) {
        // 保存监控数据
        int result = monitorDataMapper.insert(data);
        System.out.println("Received monitor data: {}"+data);
        
        if (result > 0) {
            // 获取插入后的ID
            Long monitorDataId = data.getId();
            
            // 保存进程信息
            if (data.getProcesses() != null && !data.getProcesses().isEmpty()) {
                for (ProcessInfo process : data.getProcesses()) {
                    process.setMonitorDataId(monitorDataId);
                    process.setNodeIdentifier(data.getNodeIdentifier());
                    processInfoMapper.insert(process);
                }
            }
            
            // 通过WebSocket推送数据给前端
            webSocketService.sendMonitorData(data);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/monitor/latest")
    public ResponseEntity<MonitorData> getLatestMonitorData(@RequestParam String nodeIdentifier) {
        MonitorData latestData = monitorDataMapper.findTopByNodeIdentifierOrderByTimestampDesc(nodeIdentifier);
        return ResponseEntity.ok(latestData);
    }
}