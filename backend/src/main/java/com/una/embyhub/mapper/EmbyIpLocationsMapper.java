package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationMapResponse;
import com.una.embyhub.model.dto.response.embyiplocations.EmbyIpLocationsResponse;
import com.una.embyhub.model.dto.response.embyiplocations.ThresholdUserResponse;
import com.una.embyhub.model.entity.EmbyIpLocations;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EmbyIpLocationsMapper extends BaseMapper<EmbyIpLocations> {
   @Select({"<script>\nSELECT\n    source.emby_info_id AS embyInfoId,\n    source.emby_user_name AS embyUserName,\n    source.remote_address AS ipAddress,\n    source.location AS location,\n    source.client_name AS client,\n    source.last_play_date AS lastPlayDate\nFROM (\n    SELECT\n        emby_info_id,\n        COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) AS emby_user_name,\n        remote_address,\n        SUBSTRING_INDEX(GROUP_CONCAT(location ORDER BY play_date DESC SEPARATOR '|#|'), '|#|', 1) AS location,\n        SUBSTRING_INDEX(GROUP_CONCAT(client_name ORDER BY play_date DESC SEPARATOR '|#|'), '|#|', 1) AS client_name,\n        MAX(play_date) AS last_play_date\n    FROM playback_reporting_record\n    <where>\n        del_flag = 0\n        AND remote_address IS NOT NULL\n        AND remote_address != ''\n        AND COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) IS NOT NULL\n        <if test=\"embyInfoId != null\">\n            AND emby_info_id = #{embyInfoId}\n        </if>\n        <if test=\"embyUserName != null and embyUserName != ''\">\n            AND (\n                user_name LIKE CONCAT('%', #{embyUserName}, '%')\n                OR nick_name LIKE CONCAT('%', #{embyUserName}, '%')\n                OR user_id LIKE CONCAT('%', #{embyUserName}, '%')\n            )\n        </if>\n        <if test=\"ipAddress != null and ipAddress != ''\">\n            AND remote_address = #{ipAddress}\n        </if>\n    </where>\n    GROUP BY emby_info_id, COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id), remote_address\n) source\nORDER BY source.last_play_date DESC\n</script>\n"})
   Page<EmbyIpLocationsResponse> selectPlaybackReportingSources(
      Page<EmbyIpLocationsResponse> page,
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserName") String embyUserName,
      @Param("ipAddress") String ipAddress
   );

   @Select({"<script>\nSELECT\n    emby_info_id AS embyInfoId,\n    COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) AS embyUserName,\n    COUNT(DISTINCT remote_address) AS count,\n    GROUP_CONCAT(DISTINCT remote_address ORDER BY remote_address SEPARATOR ',') AS ipAddressList,\n    GROUP_CONCAT(DISTINCT COALESCE(NULLIF(location, ''), '未知') ORDER BY COALESCE(NULLIF(location, ''), '未知') SEPARATOR ',') AS cityList\nFROM playback_reporting_record\n<where>\n    del_flag = 0\n    AND remote_address IS NOT NULL\n    AND remote_address != ''\n    AND COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) IS NOT NULL\n    <if test=\"embyInfoId != null\">\n        AND emby_info_id = #{embyInfoId}\n    </if>\n</where>\nGROUP BY emby_info_id, COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id)\nHAVING COUNT(DISTINCT remote_address) &gt; #{thresholdUserCount}\nORDER BY count DESC\n</script>\n"})
   Page<ThresholdUserResponse> selectPlaybackReportingThresholdUsers(
      Page<EmbyIpLocations> page, @Param("thresholdUserCount") Integer thresholdUserCount, @Param("embyInfoId") Long embyInfoId
   );

   @Select({"<script>\nSELECT\n    COALESCE(NULLIF(source.location, ''), '未知') AS name,\n    COUNT(*) AS value\nFROM (\n    SELECT\n        emby_info_id,\n        COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) AS emby_user_name,\n        remote_address,\n        SUBSTRING_INDEX(GROUP_CONCAT(location ORDER BY play_date DESC SEPARATOR '|#|'), '|#|', 1) AS location\n    FROM playback_reporting_record\n    <where>\n        del_flag = 0\n        AND remote_address IS NOT NULL\n        AND remote_address != ''\n        AND COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id) IS NOT NULL\n        <if test=\"embyInfoId != null\">\n            AND emby_info_id = #{embyInfoId}\n        </if>\n    </where>\n    GROUP BY emby_info_id, COALESCE(NULLIF(user_name, ''), NULLIF(nick_name, ''), user_id), remote_address\n) source\nGROUP BY COALESCE(NULLIF(source.location, ''), '未知')\nORDER BY value DESC\n</script>\n"})
   List<EmbyIpLocationMapResponse> selectMapSummary(@Param("embyInfoId") Long embyInfoId);
}
