package com.panda.system.service;

import com.panda.system.domin.SysSession;
import com.panda.system.domin.vo.SysSessionVo;

import java.util.List;

public interface SysSessionService {

    /**
     * 根据查询条件封装对象 `SysSessionVo` 查询场次列表
     * <p>
     * 该方法通过传入的 `SysSessionVo` 查询条件，从数据库中获取符合条件的场次数据。
     * 通常在前端展示时用于分页和筛选。
     *
     * @param sysSessionVo 查询条件封装对象，包含查询条件
     * @return 返回符合条件的场次信息列表
     */
    List<SysSession> findByVo(SysSessionVo sysSessionVo);

    /**
     * 根据电影ID或影厅ID查询场次信息
     * <p>
     * 该方法根据传入的 `SysSession` 对象中的 `movieId` 或 `hallId` 查询相应的场次信息。
     * 适用于查询特定电影在特定影厅的场次。
     *
     * @param sysSession 包含电影ID或影厅ID的 `SysSession` 对象
     * @return 返回符合条件的场次信息列表
     */
    List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession);

    /**
     * 根据场次ID查询场次的详细信息
     * <p>
     * 该方法通过场次的唯一标识符（ID），查询指定的场次详细信息。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息
     */
    SysSession findSessionById(Long id);

    /**
     * 根据场次ID查询场次的基本信息（不查询关联的电影和影厅）
     * <p>
     * 该方法与 `findSessionById` 方法类似，但返回的场次对象中不包含与场次相关的电影、影厅等信息。
     * 适用于只关心场次本身的场景。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息（不含关联数据）
     */
    SysSession findOneSession(Long id);

    /**
     * 添加一个新的场次信息
     * <p>
     * 该方法用于将一个新的场次信息添加到数据库中。
     *
     * @param sysSession 要添加的场次信息对象
     * @return 返回影响的行数，通常为1表示成功，0表示失败
     */
    int addSession(SysSession sysSession);

    /**
     * 更新现有场次信息
     * <p>
     * 该方法通过传入的 `SysSession` 对象中的 `sessionId` 更新已有场次的相关信息。
     *
     * @param sysSession 更新后的场次信息对象
     * @return 返回影响的行数，通常为1表示成功，0表示失败
     */
    int updateSession(SysSession sysSession);

    /**
     * 删除多个场次信息
     * <p>
     * 该方法接受一个场次ID数组，逐个删除指定的场次记录。
     *
     * @param id 场次的ID数组
     * @return 返回受影响的行数，表示删除的记录数量
     */
    int deleteSession(Long[] id);

    /**
     * 根据电影ID查询未来5天内的放映场次
     * <p>
     * 该方法用于查询指定电影的未来5天内所有放映的场次，适用于获取电影的近五天上映安排。
     *
     * @param movieId 电影的唯一标识符（ID）
     * @return 返回未来5天内该电影的所有场次信息
     */
    List<SysSession> findSessionByMovieId(Long movieId);
}
/*
各个方法的作用：
findByVo(SysSessionVo sysSessionVo):

根据传入的查询条件 SysSessionVo，返回符合条件的场次信息列表。适用于条件查询、分页等功能。
findSessionByMovieIdOrHallId(SysSession sysSession):

根据电影ID或影厅ID查询场次信息，适用于根据电影或影厅进行筛选的场景。
findSessionById(Long id):

根据场次ID查询场次的详细信息，返回完整的场次数据，通常用于查看某个场次的详细信息。
findOneSession(Long id):

查询指定ID的场次信息，但不返回与该场次相关的电影、影厅等信息。通常用于查询场次的基本信息时使用。
addSession(SysSession sysSession):

向数据库添加一个新的场次信息对象，通常用于场次创建时。
updateSession(SysSession sysSession):

更新现有场次的信息，通常用于修改某个场次的细节。
deleteSession(Long[] id):

删除多个场次记录，支持批量删除。适用于批量删除场次时使用。
findSessionByMovieId(Long movieId):

根据电影ID查询未来5天内该电影的所有放映场次，适用于获取电影上映安排的功能。
总结：
这个接口定义了与场次信息相关的业务方法，包括查询、添加、更新、删除等操作。它是业务逻辑层的核心接口，提供了多个功能，允许上层控制器层通过这个接口与底层的数据访问层（Mapper）进行交互
 */