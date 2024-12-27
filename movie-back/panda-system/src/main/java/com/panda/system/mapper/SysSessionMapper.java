package com.panda.system.mapper;

import com.panda.system.domin.SysSession;
import com.panda.system.domin.vo.SysSessionVo;

import java.util.List;

public interface SysSessionMapper {

    /**
     * 根据查询条件封装对象 SysSessionVo 查询场次列表
     * <p>
     * 该方法会根据传入的 `SysSessionVo` 对象中的条件进行动态查询，
     * 可以用于获取满足指定条件的场次信息。
     * <p>
     * 例如，可以根据电影ID、影厅ID、时间等过滤条件来查询相关的场次。
     *
     * @param sysSessionVo 查询条件封装对象，包含各种过滤条件
     * @return 返回符合条件的场次列表
     */
    List<SysSession> findByVo(SysSessionVo sysSessionVo);

    /**
     * 根据电影ID或影厅ID查询场次信息
     * <p>
     * 该方法用于根据 `SysSession` 中的 `movieId` 或 `hallId` 来查询场次信息，
     * 并且返回符合条件的场次列表。用于判断某个电影在某个影厅是否有放映。
     *
     * @param sysSession 传入的 `SysSession` 对象，其中包含电影ID和/或影厅ID
     * @return 返回符合条件的场次列表
     */
    List<SysSession> findSessionByMovieIdOrHallId(SysSession sysSession);

    /**
     * 根据场次ID查询场次信息
     * <p>
     * 该方法根据传入的场次ID查找具体的场次信息，并返回该场次的详细信息。
     * 用于根据ID查询某个特定场次的数据。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息
     */
    SysSession findSessionById(Long id);

    /**
     * 根据场次ID查询场次信息，不查询关联的电影和影厅等信息
     * <p>
     * 该方法与 `findSessionById` 方法类似，但返回的数据不会包括与场次相关联的其他信息，
     * 比如电影、影厅等。
     * 用于查询场次信息时，不需要获取所有关联的细节信息的情况。
     *
     * @param id 场次的唯一标识符（ID）
     * @return 返回与指定ID匹配的场次信息（不含关联数据）
     */
    SysSession findOneSession(Long id);

    /**
     * 添加新的场次信息
     * <p>
     * 该方法用于将一个新的 `SysSession` 对象添加到数据库中，
     * 即将新的电影场次信息保存到数据库。
     * 在调用时，`sysSession` 对象应该包含所有必要的信息（如影厅ID、电影ID、放映时间等）。
     *
     * @param sysSession 要添加的场次信息对象
     * @return 返回受影响的行数，通常是1表示成功，0表示失败
     */
    int addSession(SysSession sysSession);

    /**
     * 更新现有的场次信息
     * <p>
     * 该方法用于更新数据库中已经存在的场次信息。通过传入修改后的 `SysSession` 对象，
     * 系统会根据 `sessionId` 更新对应的记录。
     *
     * @param sysSession 要更新的场次信息对象
     * @return 返回受影响的行数，通常是1表示成功，0表示失败
     */
    int updateSession(SysSession sysSession);

    /**
     * 删除指定场次ID的场次信息
     * <p>
     * 该方法用于根据场次ID删除对应的场次记录。删除场次时，会同时删除与场次相关的所有数据。
     *
     * @param id 要删除的场次ID
     * @return 返回受影响的行数，通常是1表示成功，0表示失败
     */
    int deleteSession(Long id);

    /**
     * 根据电影ID查询近5天上映的场次信息
     * <p>
     * 该方法用于查询指定电影在未来5天内的所有放映场次，
     * 通过传入电影ID，系统会返回该电影近5天的所有场次信息。
     *
     * @param movieId 电影的唯一标识符（ID）
     * @return 返回未来5天内该电影的所有场次信息列表
     */
    List<SysSession> findSessionByMovieId(Long movieId);
}



/*
详细注释说明：
findByVo(SysSessionVo sysSessionVo):

根据传入的 SysSessionVo 对象中的条件查询场次数据。SysSessionVo 封装了多个查询条件，能够支持动态查询。
findSessionByMovieIdOrHallId(SysSession sysSession):

根据 SysSession 中的 movieId 或 hallId 查询场次信息。用于判断某部电影是否在某个影厅放映。
findSessionById(Long id):

根据场次的 ID 查询场次的详细信息。用于获取某个特定场次的完整信息。
findOneSession(Long id):

根据场次的 ID 查询场次信息，但不包括与场次关联的其他数据，如电影信息、影厅信息等。适用于只需要场次基本信息的场景。
addSession(SysSession sysSession):

添加一个新的场次记录到数据库中。SysSession 对象中应该包含该场次的所有必要信息，如影厅、电影、放映时间等。
updateSession(SysSession sysSession):

更新数据库中的场次信息。通过 SysSession 对象中的 sessionId 来定位更新的记录。
deleteSession(Long id):

删除指定 ID 的场次记录。调用此方法会删除场次对应的数据库记录。
findSessionByMovieId(Long movieId):

根据电影 ID 查询该电影在未来五天的所有放映场次。适用于获取指定电影的近期放映安排。
 */
