package com.wangliang.lepao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangliang.lepao.model.domain.Team;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.model.dto.TeamQuery;
import com.wangliang.lepao.model.request.TeamJoinRequest;
import com.wangliang.lepao.model.request.TeamQuitRequest;
import com.wangliang.lepao.model.request.TeamUpdateRequest;
import com.wangliang.lepao.model.vo.TeamUserVO;

import java.util.List;

/**
 * 队伍服务
 *
 * @author wangliang
 */

public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     *
     * @param team 队伍
     * @param loginUser 登录用户
     * @return long
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     *
     * @param teamQuery 队伍查询条件
     * @param isAdmin 是否是管理员
     * @return List<TeamUserVO>
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍
     *
     * @param teamUpdateRequest 更新队伍请求
     * @param loginUser   登录用户
     * @return boolean
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     *
     * @param teamJoinRequest 加入队伍请求
     * @return boolean
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     *
     * @param teamQuitRequest 退出队伍请求
     * @param loginUser 登录用户
     * @return boolean
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除（解散）队伍
     *
     * @param id 队伍id
     * @param loginUser 登录用户
     * @return boolean
     */
    boolean deleteTeam(long id, User loginUser);

    /**
     * 获取分页列表分页查询队伍
     * @param teamQuery 查询条件
     * @return Page<Team>
     */
    Page<Team> listPage(TeamQuery teamQuery);
}
