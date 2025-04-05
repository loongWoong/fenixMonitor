package com.monitor.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.List;

@Data
@TableName("monitor_data")
public class MonitorData {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long timestamp;
    private Double cpu;
    
    @TableField("memory_total")
    private Long memoryTotal;
    
    @TableField("memory_available")
    private Long memoryAvailable;
    
    @TableField("memory_percent")
    private Double memoryPercent;
    
    @TableField("network_bytes_sent")
    private Long networkBytesSent;
    
    @TableField("network_bytes_recv")
    private Long networkBytesRecv;
    
    @TableField(exist = false)
    private List<ProcessInfo> processes;
    private String nodeIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Long getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(Long memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    public void setMemoryAvailable(Long memoryAvailable) {
        this.memoryAvailable = memoryAvailable;
    }

    public Double getMemoryPercent() {
        return memoryPercent;
    }

    public void setMemoryPercent(Double memoryPercent) {
        this.memoryPercent = memoryPercent;
    }

    public Long getNetworkBytesSent() {
        return networkBytesSent;
    }

    public void setNetworkBytesSent(Long networkBytesSent) {
        this.networkBytesSent = networkBytesSent;
    }

    public Long getNetworkBytesRecv() {
        return networkBytesRecv;
    }

    public void setNetworkBytesRecv(Long networkBytesRecv) {
        this.networkBytesRecv = networkBytesRecv;
    }

    public List<ProcessInfo> getProcesses() {
        return processes;
    }

    public void setProcesses(List<ProcessInfo> processes) {
        this.processes = processes;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }
}