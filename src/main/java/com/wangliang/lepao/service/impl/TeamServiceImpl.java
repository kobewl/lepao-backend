package com.wangliang.lepao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangliang.lepao.mapper.TeamMapper;
import com.wangliang.lepao.model.domain.Team;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.model.dto.TeamQuery;
import com.wangliang.lepao.model.request.TeamJoinRequest;
import com.wangliang.lepao.model.request.TeamQuitRequest;
import com.wangliang.lepao.model.request.TeamUpdateRequest;
import com.wangliang.lepao.model.vo.TeamUserVO;
import com.wangliang.lepao.service.TeamService;
import com.wangliang.lepao.service.UserService;
import com.wangliang.lepao.service.UserTeamService;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 队伍服务实现类
 *
 * @author wangliang
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
        implements TeamService {

    @Resource
    private UserTeamService userTeamService;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建队伍
     *
     * @param team
     * @param loginUser
     * @return
     */
    @Override
    public long addTeam(Team team, User loginUser) {
        return 0;
    }

    /**
     * 搜索队伍
     *
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    @Override
    public List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin) {
        return null;
    }

    /**
     * 更新队伍
     *
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    @Override
    public boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser) {
        return false;
    }

    /**
     * 加入队伍
     *
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    @Override
    public boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser) {
        return false;
    }

    /**
     * 退出队伍
     *
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    @Override
    public boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser) {
        return false;
    }

    /**
     * 删除（解散）队伍
     *
     * @param id
     * @param loginUser
     * @return
     */
    @Override
    public boolean deleteTeam(long id, User loginUser) {
        return false;
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    @Override
    public boolean saveBatch(Collection<Team> entityList, int batchSize) {
        return false;
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     */
    @Override
    public boolean saveOrUpdateBatch(Collection<Team> entityList, int batchSize) {
        return false;
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    @Override
    public boolean updateBatchById(Collection<Team> entityList, int batchSize) {
        return false;
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    @Override
    public boolean saveOrUpdate(Team entity) {
        return false;
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    @Override
    public Team getOne(Wrapper<Team> queryWrapper, boolean throwEx) {
        return null;
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    public Map<String, Object> getMap(Wrapper<Team> queryWrapper) {
        return null;
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @param mapper       转换函数
     */
    @Override
    public <V> V getObj(Wrapper<Team> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }
}




