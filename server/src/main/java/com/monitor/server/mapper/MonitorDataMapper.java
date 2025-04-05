package com.monitor.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monitor.server.model.MonitorData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MonitorDataMapper extends BaseMapper<MonitorData> {
    @Select("SELECT * FROM monitor_data ORDER BY timestamp DESC LIMIT 1")
    MonitorData findTopByOrderByTimestampDesc();
    @Select("SELECT * FROM monitor_data WHERE node_identifier = #{nodeIdentifier} ORDER BY timestamp DESC LIMIT 1")
    MonitorData findTopByNodeIdentifierOrderByTimestampDesc(String nodeIdentifier);
}