package com.panda.system.service.impl;

import com.panda.system.domin.SysHall;
import com.panda.system.mapper.SysHallMapper;
import com.panda.system.service.SysHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysHallServiceImpl 类实现了 SysHallService 接口。
 * 该服务类处理与影厅 (SysHall) 相关的业务逻辑，调用对应的 Mapper 进行数据库操作。
 */
@Service
public class SysHallServiceImpl implements SysHallService {

    @Autowired
    private SysHallMapper sysHallMapper; // 注入 SysHallMapper，用于与数据库进行交互

    /**
     * 查询所有影厅信息。
     * @param sysHall 查询条件，可以根据影厅的属性进行筛选（如影厅ID、影院ID等）
     * @return 返回符合条件的影厅列表
     */
    @Override
    public List<SysHall> findAllHalls(SysHall sysHall) {
        return sysHallMapper.findAllHalls(sysHall); // 调用 Mapper 层查询影厅数据
    }

    /**
     * 根据影厅ID查询单个影厅信息。
     * @param sysHall 包含影厅ID的对象
     * @return 返回指定ID的影厅信息
     */
    @Override
    public SysHall findHallById(SysHall sysHall) {
        return sysHallMapper.findHallById(sysHall); // 调用 Mapper 层获取影厅信息
    }

    /**
     * 添加新的影厅。
     * @param sysHall 包含影厅信息的对象
     * @return 返回影响的行数，1表示添加成功
     */
    @Override
    public int addHall(SysHall sysHall) {
        return sysHallMapper.addHall(sysHall); // 调用 Mapper 层添加影厅
    }

    /**
     * 更新影厅信息。
     * @param sysHall 包含影厅信息的对象
     * @return 返回影响的行数，1表示更新成功
     */
    @Override
    public int updateHall(SysHall sysHall) {
        return sysHallMapper.updateHall(sysHall); // 调用 Mapper 层更新影厅
    }

    /**
     * 删除多个影厅。
     * @param sysHalls 需要删除的影厅对象数组
     * @return 返回影响的行数，表示删除的影厅数量
     */
    @Override
    public int deleteHall(SysHall[] sysHalls) {
        int rows = 0; // 用于累加删除的影厅数量
        // 遍历每个影厅并调用 Mapper 层删除
        for (SysHall sysHall : sysHalls) {
            rows += sysHallMapper.deleteHall(sysHall); // 调用 Mapper 层删除影厅
        }
        return rows; // 返回删除的影厅数量
    }
}
/*
代码解析：
类注释：

SysHallServiceImpl 是 SysHallService 接口的实现类，主要处理影厅（SysHall）相关的业务逻辑。
该类通过注入 SysHallMapper 与数据库进行交互，执行影厅的增、删、改、查操作。
构造器和字段注入：

使用 @Autowired 注解注入了 SysHallMapper，这个 Mapper 类负责与数据库的直接交互。
方法注释：

findAllHalls(SysHall sysHall)：查询所有影厅信息，可以通过传入 sysHall 对象作为查询条件进行筛选。
findHallById(SysHall sysHall)：根据影厅的 ID 查询单个影厅的详细信息。
addHall(SysHall sysHall)：向数据库中添加新的影厅，返回影响的行数。
updateHall(SysHall sysHall)：更新指定影厅的信息，返回影响的行数。
deleteHall(SysHall[] sysHalls)：删除多个影厅，传入包含多个影厅对象的数组，遍历每个影厅并删除，返回影响的行数。
处理逻辑：

deleteHall(SysHall[] sysHalls) 方法通过遍历 sysHalls 数组调用 sysHallMapper.deleteHall(sysHall) 删除每个影厅，返回删除的行数。
返回值：

int：表示操作影响的行数。例如，在 addHall 或 updateHall 方法中，返回 1 表示操作成功。
List<SysHall>：findAllHalls 方法返回一个符合条件的影厅列表。
依赖注入：
@Autowired 注解将 SysHallMapper 自动注入到服务类中，以便通过它进行数据库的增、删、改、查操作。

该实现类是典型的服务层实现，专注于业务逻辑处理，而实际的数据库操作则交给 SysHallMapper 完成。
 */