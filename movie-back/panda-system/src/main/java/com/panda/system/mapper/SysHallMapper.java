package com.panda.system.mapper;

import com.panda.system.domin.SysHall;

import java.util.List;

/**
 * SysHallMapper 接口用于定义与影厅 (SysHall) 相关的数据库操作。
 * 包括影厅的查询、添加、更新和删除操作。
 */
public interface SysHallMapper {

    /**
     * 查询所有影厅信息。
     * @param sysHall 查询条件，可以为筛选的影厅属性（如影院ID、影厅名称等）
     * @return 返回符合条件的影厅列表
     */
    List<SysHall> findAllHalls(SysHall sysHall);

    /**
     * 根据影厅ID查询单个影厅信息。
     * @param sysHall 包含影厅ID的对象，返回该ID对应的影厅信息
     * @return 返回找到的影厅对象
     */
    SysHall findHallById(SysHall sysHall);

    /**
     * 添加新的影厅。
     * @param sysHall 包含影厅详细信息的对象，需包含所有必要的字段，如影厅名称、座位数等
     * @return 返回影响的行数，1表示添加成功
     */
    int addHall(SysHall sysHall);

    /**
     * 更新已有的影厅信息。
     * @param sysHall 包含影厅ID及要更新的字段的对象，更新影厅的具体信息
     * @return 返回影响的行数，1表示更新成功
     */
    int updateHall(SysHall sysHall);

    /**
     * 删除影厅。
     * @param sysHall 包含影厅ID的对象，删除指定ID的影厅
     * @return 返回影响的行数，1表示删除成功
     */
    int deleteHall(SysHall sysHall);
}
/*
代码解析：
接口功能：

该接口定义了与影厅（SysHall）相关的基本数据库操作方法。
每个方法对应一个常见的数据库操作，如查询所有影厅、根据ID查询影厅、添加影厅、更新影厅和删除影厅。
方法注释：

findAllHalls(SysHall sysHall)：
用于查询所有影厅信息，可以通过 sysHall 作为查询条件来过滤结果（例如，根据影院ID、影厅名称等筛选影厅）。
findHallById(SysHall sysHall)：
通过影厅ID查找具体的影厅信息，返回该影厅的详细数据。
addHall(SysHall sysHall)：
添加新的影厅信息，传入包含影厅详细信息的 SysHall 对象，返回操作成功后影响的行数。
updateHall(SysHall sysHall)：
更新指定影厅的信息，传入更新后的影厅对象，返回更新操作影响的行数。
deleteHall(SysHall sysHall)：
删除影厅，传入要删除的影厅对象，返回删除操作影响的行数。
返回值：

所有方法的返回值类型基本上都为 int 或者 List<SysHall>。
List<SysHall>：查询操作返回符合条件的影厅列表。
int：对影厅进行增、删、改操作时，返回影响的行数。通常，返回 1 表示操作成功，0 表示操作失败。
参数说明：

SysHall sysHall：用于传递影厅相关的查询条件或者更新的字段，确保能够根据指定条件进行筛选或更新。
该接口将与数据库交互的具体操作委托给 MyBatis 框架，通过 SQL 映射文件与数据库进行交互，执行影厅的 CRUD 操作
 */