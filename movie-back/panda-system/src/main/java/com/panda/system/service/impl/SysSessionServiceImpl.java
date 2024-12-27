package com.panda.system.service.impl;

import com.panda.system.domin.SysSession;
import com.panda.system.domin.vo.SysSessionVo;
import com.panda.system.mapper.SysSessionMapper;
import com.panda.system.service.SysSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 标记为Spring的服务类，交给Spring容器管理
public class SysSessionServiceImpl implements SysSessionService {

    @Autowired  // 自动装配SysSessionMapper实例，用于操作数据库
    private SysSessionMapper sysSessionMapper;

    /**
     * 根据查询条件封装对象 SysSessionVo 查询场次列表
     * <p>
     * 该方法根据传入的 `SysSessionVo` 查询条件调用 `SysSessionMapper` 中的 `findByVo` 方法，
     * 并返回符合条件的场次列表。
     *
     * @param sysSessionVo 查询条件封装对象，包含查询的各种条件
     * @return 返回符合条件的场次列表
     */
    @Override
    public List<SysSession> findByVo(SysSessionVo sysSessionVo) {
        return sysSessionMapper.findByVo(sysSessionVo); // 调用Mapper方法进行数据库查询
    }

    /**
     * 根据电影ID或影厅ID查询场次信息
     * <p>
     * 该方法调用 `SysSessionMapper` 中的 `findSessionByMovieIdOrHallId` 方法，
     * 返回匹配的场次信息。适用于判断某部电影是否在某个影厅放映。
     *
     * @param sysSession 包含电影ID或影厅ID的 `SysSession` 对象
     * @return 返回符合条件的场次信息列表
     */
    @Override
    public List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession) {
        return sysSessionMapper.findSessionByMovieIdOrHallId(sysSession); // 调用Mapper查询方法
    }

    /**
     * 根据场次ID查询具体的场次信息
     * <p>
     * 该方法根据传入的场次ID，调用 `SysSessionMapper` 的 `findSessionById` 方法，
     * 获取指定ID的场次详细信息。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息
     */
    @Override
    public SysSession findSessionById(Long id) {
        return sysSessionMapper.findSessionById(id); // 调用Mapper方法查询单个场次
    }

    /**
     * 根据场次ID查询场次信息，不查询关联的电影和影厅等信息
     * <p>
     * 该方法与 `findSessionById` 类似，但不查询与场次关联的其他信息（如电影、影厅等）。
     * 适用于只需要场次基本信息的场景。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息（不含关联数据）
     */
    @Override
    public SysSession findOneSession(Long id) {
        return sysSessionMapper.findOneSession(id); // 调用Mapper查询方法，但不包含关联数据
    }

    /**
     * 添加新的场次信息
     * <p>
     * 该方法通过调用 `SysSessionMapper` 的 `addSession` 方法，
     * 将一个新的 `SysSession` 对象添加到数据库中。
     *
     * @param sysSession 要添加的场次信息
     * @return 返回影响的行数，通常为1表示成功，0表示失败
     */
    @Override
    public int addSession(SysSession sysSession) {
        return sysSessionMapper.addSession(sysSession); // 调用Mapper插入方法
    }

    /**
     * 更新现有的场次信息
     * <p>
     * 该方法通过调用 `SysSessionMapper` 的 `updateSession` 方法，
     * 根据传入的 `SysSession` 对象中的 `sessionId` 更新相应的场次信息。
     *
     * @param sysSession 包含更新数据的场次信息
     * @return 返回影响的行数，通常为1表示成功，0表示失败
     */
    @Override
    public int updateSession(SysSession sysSession) {
        return sysSessionMapper.updateSession(sysSession); // 调用Mapper更新方法
    }

    /**
     * 删除指定场次ID的场次信息
     * <p>
     * 该方法接受一个包含多个ID的数组，遍历删除多个场次信息。每次删除时，
     * 调用 `SysSessionMapper` 中的 `deleteSession` 方法，删除指定ID的场次。
     *
     * @param ids 要删除的场次ID数组
     * @return 返回受影响的行数，表示删除的场次数量
     */
    @Override
    public int deleteSession(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysSessionMapper.deleteSession(id); // 对每个ID调用删除方法
        }
        return rows; // 返回删除的记录数量
    }

    /**
     * 根据电影ID查询该电影的近5天放映场次
     * <p>
     * 该方法用于查询指定电影ID的未来5天内的所有放映场次，调用 `SysSessionMapper` 的
     * `findSessionByMovieId` 方法获取相关场次信息。
     *
     * @param movieId 电影的唯一标识符（ID）
     * @return 返回未来5天内该电影的所有场次信息
     */
    @Override
    public List<SysSession> findSessionByMovieId(Long movieId) {
        return sysSessionMapper.findSessionByMovieId(movieId); // 调用Mapper查询该电影的放映场次
    }
}
/*
详细注释说明：
findByVo(SysSessionVo sysSessionVo):

调用 SysSessionMapper 的 findByVo 方法，使用 SysSessionVo 中封装的条件查询符合要求的场次信息。
findSessionByMovieIdOrHallId(SysSession sysSession):

根据传入的 SysSession 对象中的 movieId 或 hallId 查询场次信息，适用于根据电影或影厅查询场次。
findSessionById(Long id):

根据场次ID查询具体的场次信息。通过调用 SysSessionMapper 的 findSessionById 方法。
findOneSession(Long id):

根据场次ID查询场次信息，但不查询与场次相关的电影、影厅等信息。适用于仅查询场次本身的场景。
addSession(SysSession sysSession):

添加一个新的场次信息记录。通过调用 SysSessionMapper 的 addSession 方法，将场次信息插入到数据库。
updateSession(SysSession sysSession):

更新场次信息。通过 SysSessionMapper 的 updateSession 方法，更新已有场次的相关数据。
deleteSession(Long[] ids):

删除多个场次记录。遍历给定的ID数组，逐个删除对应的场次。
findSessionByMovieId(Long movieId):

查询指定电影ID的未来5天内所有放映的场次信息。该方法帮助获取电影的近五天放映安排。
总结：
该实现类提供了 SysSessionService 接口的具体实现，包括了场次的查询、添加、更新和删除等功能。通过依赖 SysSessionMapper，所有数据库操作都被委托给了Mapper层，确保了逻辑的清晰和分离
 */