package com.monitor.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("process_info")
public class ProcessInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("pid")
    private Integer pid;
    
    @TableField("name")
    private String name;
    
    @TableField("cpu_percent")
    private Double cpu_percent;
    
    @TableField("memory_percent")
    private Double memory_percent;
    
    @TableField("monitor_data_id")
    private Long monitorDataId;
    
    @TableField("node_identifier")
    private String nodeIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCpu_percent() {
        return cpu_percent;
    }

    public void setCpu_percent(Double cpu_percent) {
        this.cpu_percent = cpu_percent;
    }

    public Double getMemory_percent() {
        return memory_percent;
    }

    public void setMemory_percent(Double memory_percent) {
        this.memory_percent = memory_percent;
    }

    public Long getMonitorDataId() {
        return monitorDataId;
    }

    public void setMonitorDataId(Long monitorDataId) {
        this.monitorDataId = monitorDataId;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }
}