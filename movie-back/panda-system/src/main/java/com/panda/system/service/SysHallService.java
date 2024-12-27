package com.panda.system.service;

import com.panda.system.domin.SysHall;

import java.util.List;

/**
 * SysHallService 接口定义了影厅（SysHall）相关的业务逻辑方法。
 * 这个接口是服务层的核心接口，提供了增、删、改、查操作的抽象。
 */
public interface SysHallService {

    /**
     * 查询所有影厅信息，根据传入的条件进行筛选。
     * @param sysHall 影厅筛选条件，可能包括影厅ID、影院ID等
     * @return 返回符合条件的影厅列表
     */
    List<SysHall> findAllHalls(SysHall sysHall);

    /**
     * 根据影厅ID查询单个影厅的信息。
     * @param sysHall 包含影厅ID的对象
     * @return 返回指定影厅ID的影厅信息
     */
    SysHall findHallById(SysHall sysHall);

    /**
     * 添加一个新的影厅。
     * @param sysHall 包含影厅信息的对象
     * @return 返回影响的行数，1表示添加成功
     */
    int addHall(SysHall sysHall);

    /**
     * 更新影厅的信息。
     * @param sysHall 包含影厅信息的对象
     * @return 返回影响的行数，1表示更新成功
     */
    int updateHall(SysHall sysHall);

    /**
     * 删除多个影厅。
     * @param sysHall 需要删除的影厅对象数组
     * @return 返回影响的行数，表示删除的影厅数量
     */
    int deleteHall(SysHall[] sysHall);
}
/*
代码解析：
接口注释：

SysHallService 接口定义了影厅（SysHall）的基本操作，包括查询、添加、更新和删除影厅。
该接口为服务层提供了业务逻辑的抽象，具体的实现由 SysHallServiceImpl 类完成。
方法注释：

findAllHalls(SysHall sysHall)：根据传入的 sysHall 对象（作为查询条件），查询所有影厅信息。如果 sysHall 中包含特定属性值，可以根据这些属性进行筛选。
findHallById(SysHall sysHall)：根据影厅的 id 查询影厅的详细信息。
addHall(SysHall sysHall)：添加一个新的影厅，返回值为影响的行数，通常为 1 表示操作成功。
updateHall(SysHall sysHall)：更新指定影厅的信息，返回值为影响的行数，通常为 1 表示操作成功。
deleteHall(SysHall[] sysHall)：删除多个影厅，传入一个影厅对象数组，返回值为影响的行数，表示删除了多少个影厅。
总结：
SysHallService 是业务逻辑层的接口，它定义了与影厅（SysHall）相关的操作。
这个接口的具体实现由 SysHallServiceImpl 类完成，该实现类负责与数据库进行交互，并处理实际的业务逻辑。
 */