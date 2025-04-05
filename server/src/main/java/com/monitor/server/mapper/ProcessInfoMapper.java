package com.monitor.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monitor.server.model.ProcessInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcessInfoMapper extends BaseMapper<ProcessInfo> {
}