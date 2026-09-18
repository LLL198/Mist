package com.una.embyhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.una.embyhub.model.dto.response.embyuser.EmbyServerUserStatsResponse;
import com.una.embyhub.model.entity.EmbyUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EmbyUserMapper extends BaseMapper<EmbyUser> {
   @Select({"SELECT * FROM emby_user WHERE emby_user_name = #{userName} LIMIT 1"})
   EmbyUser selectByUserName(String userName);

   @Select({"<script>\n    SELECT COUNT(1)\n    FROM emby_user\n    WHERE emby_user_name = #{userName}\n      AND emby_user_password = #{passwordHash}\n      AND del_flag = 0\n    <if test='excludeUserId != null'>\n      AND id &lt;&gt; #{excludeUserId}\n    </if>\n    <if test='excludeIdentityGroupId != null'>\n      AND identity_group_id &lt;&gt; #{excludeIdentityGroupId}\n    </if>\n</script>\n"})
   long countActiveCredentialMatches(
      @Param("userName") String userName,
      @Param("passwordHash") String passwordHash,
      @Param("excludeUserId") Long excludeUserId,
      @Param("excludeIdentityGroupId") Long excludeIdentityGroupId
   );

   @Select({"<script>\nSELECT\n    ei.id AS embyInfoId,\n    ei.server_name AS serverName,\n    COALESCE(COUNT(eu.id), 0) AS totalUserCount,\n    COALESCE(SUM(CASE WHEN eu.user_status = 0 THEN 1 ELSE 0 END), 0) AS activeUserCount,\n    COALESCE(SUM(CASE WHEN eu.user_status = 1 THEN 1 ELSE 0 END), 0) AS disabledUserCount,\n    COALESCE(SUM(CASE WHEN eu.create_datetime >= CURDATE() THEN 1 ELSE 0 END), 0) AS todayNewUserCount\nFROM emby_info ei\nLEFT JOIN emby_user eu ON ei.id = eu.emby_info_id AND eu.del_flag = 0\n<if test='includeAllAdministrators == false'>\n    AND (COALESCE(eu.is_admin, 0) &lt;&gt; 1\n    <if test='currentUserId != null'>\n        OR eu.id = #{currentUserId}\n    </if>)\n</if>\nWHERE ei.del_flag = 0\nGROUP BY ei.id, ei.server_name\nORDER BY ei.id\n</script>\n"})
   List<EmbyServerUserStatsResponse> selectServerUserStats(
      @Param("currentUserId") Long currentUserId, @Param("includeAllAdministrators") boolean includeAllAdministrators
   );

   @Select({"<script>SELECT     ei.id AS embyInfoId,     ei.server_name AS serverName,     COALESCE(COUNT(eu.id), 0) AS totalUserCount,     COALESCE(SUM(CASE WHEN eu.user_status = 0 THEN 1 ELSE 0 END), 0) AS activeUserCount,     COALESCE(SUM(CASE WHEN eu.user_status = 1 THEN 1 ELSE 0 END), 0) AS disabledUserCount,     COALESCE(SUM(CASE WHEN eu.create_datetime &gt;= CURDATE() THEN 1 ELSE 0 END), 0) AS todayNewUserCount FROM emby_info ei LEFT JOIN emby_user eu ON ei.id = eu.emby_info_id AND eu.del_flag = 0     AND eu.register_channel = 2     AND eu.id IN (SELECT DISTINCT csm.user_id FROM card_security_management csm         WHERE csm.user_id IS NOT NULL         AND csm.card_status = 1         AND csm.distributor_id IS NOT NULL         AND csm.distributor_id &gt; 0         <if test='distributorId != null'>            AND csm.distributor_id = #{distributorId}        </if>    ) WHERE ei.del_flag = 0 GROUP BY ei.id, ei.server_name ORDER BY ei.id</script>"})
   List<EmbyServerUserStatsResponse> selectServerUserStatsDistributor(@Param("distributorId") Long distributorId);
}
