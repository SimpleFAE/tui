package com.panda.system.service.impl;

import com.panda.system.domin.SysCinema;
import com.panda.system.mapper.SysCinemaMapper;
import com.panda.system.service.SysCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCinemaServiceImpl implements SysCinemaService {

    // 注入 SysCinemaMapper，负责与数据库交互
    @Autowired
    private SysCinemaMapper sysCinemaMapper;

    /**
     * 查询所有影院的信息
     * @return 返回包含影院信息的 SysCinema 对象
     */
    @Override
    public SysCinema findCinema() {
        return sysCinemaMapper.findCinema();
    }

    /**
     * 更新指定影院的信息
     * @param sysCinema 要更新的影院对象
     * @return 更新操作影响的行数
     */
    @Override
    public int updateCinema(SysCinema sysCinema) {
        return sysCinemaMapper.updateCinema(sysCinema);
    }

    /**
     * 查询单个影院的详细信息，返回包含该影院和上映中的所有电影信息
     * @param id 影院的 ID
     * @return 返回包含影院及上映电影信息的 SysCinema 对象
     */
    @Override
    public SysCinema findCinemaById(Long id) {
        return sysCinemaMapper.findCinemaById(id);
    }
}
/*
注释说明：
类注解 @Service：

该类被标注为服务层（@Service 注解），Spring 会自动将其注册为一个服务 bean，供其他组件自动注入使用。
字段注解 @Autowired：

SysCinemaMapper 是数据库访问层的接口，通过 @Autowired 注解将其自动注入到服务类中，用来实现与数据库的交互。
findCinema 方法：

该方法调用了 sysCinemaMapper.findCinema() 来查询所有影院的信息，并返回一个 SysCinema 对象。
updateCinema 方法：

该方法接收一个 SysCinema 对象，调用 sysCinemaMapper.updateCinema() 方法更新影院信息，并返回更新操作影响的行数。
findCinemaById 方法：

该方法根据 id 查询一个指定影院的详细信息，包括该影院以及上映中的电影列表。
总结：
SysCinemaServiceImpl 类是服务层的实现类，负责业务逻辑的处理。它通过调用 SysCinemaMapper 来进行数据库操作。这个服务类提供了三个主要的方法：

查询所有影院信息 (findCinema)
更新指定影院信息 (updateCinema)
根据影院 ID 查询影院及其电影信息 (findCinemaById)
这些方法使得前端可以方便地调用并获取或更新影院相关信息。
 */