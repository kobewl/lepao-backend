package com.wangliang.lepao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangliang.lepao.common.BaseResponse;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.model.enums.ErrorCode;
import com.wangliang.lepao.common.ResultUtils;
import com.wangliang.lepao.exception.BusinessException;
import com.wangliang.lepao.model.domain.Team;
import com.wangliang.lepao.model.dto.TeamQuery;
import com.wangliang.lepao.model.request.TeamAddRequest;
import com.wangliang.lepao.model.request.TeamJoinRequest;
import com.wangliang.lepao.model.request.TeamUpdateRequest;
import com.wangliang.lepao.model.vo.TeamUserVO;
import com.wangliang.lepao.service.TeamService;
import com.wangliang.lepao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
public class TeamController {

    @Resource
    private TeamService teamService;
    @Resource
    private UserService userService;

    /**
     * 添加团队
     * @param teamAddRequest (团队信息)
     * @param request (登录用户)
     * @return BaseResponse<Long>
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request) {
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Team team = new Team();
        BeanUtils.copyProperties(teamAddRequest, team);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }

    /**
     * 删除团队
     * @param id (团队id)
     * @return BaseResponse<Boolean>
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        boolean save = teamService.removeById(id);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 更新团队
     * @param teamUpdateRequest (团队信息)
     * @return BaseResponse<Boolean>
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 获取团队
     * @param id (团队id)
     * @return BaseResponse<Team>
     */
    @GetMapping("/get")
    public BaseResponse<Team> getTeamById(Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Team team = teamService.getById(id);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }

    /**
     * 获取团队列表
     * @param teamQuery (团队信息)
     * @param request (登录用户)
     * @return BaseResponse<List<TeamUserVO>>
     */
    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> listTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean admin = userService.isAdmin(request);
        if (!admin && teamQuery.getUserId() != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, true);
        return ResultUtils.success(teamList);
    }

    /**
     * 获取分页队伍列表
     * @param teamQuery 分页请求参数
     * @return BaseResponse<Page<Team>>
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamsByPage(TeamQuery teamQuery) {
        Page<Team> resultPage = teamService.listPage(teamQuery);
        return ResultUtils.success(resultPage);
    }

    /**
     * 处理加入团队的请求
     *
     * @param teamJoinRequest 请求体，包含加入团队的必要信息
     * @param request HTTP请求对象，用于获取当前登录的用户信息
     * @return 返回一个布尔值，表示加入团队操作是否成功
     * @throws BusinessException 如果请求参数为空，则抛出此异常
     */
    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request) {
        if (teamJoinRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.success(result);
    }


}
