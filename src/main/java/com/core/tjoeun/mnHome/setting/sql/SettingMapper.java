package com.core.tjoeun.mnHome.setting.sql;

import java.util.List;
import java.util.Map;

public interface SettingMapper {
    
    List<Map<String, Object>> selectSettingUserStorage(String userNickname);
    
}