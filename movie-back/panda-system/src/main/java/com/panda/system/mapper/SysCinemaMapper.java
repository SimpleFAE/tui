package com.panda.system.mapper;

import com.panda.system.domin.SysCinema;

public interface SysCinemaMapper {

    /**
     * 查询所有影院的信息
     * @return 返回包含所有影院信息的 SysCinema 对象
     */
    SysCinema findCinema();

    /**
     * 更新指定影院的信息
     * @param sysCinema 要更新的影院对象
     * @return 更新操作影响的行数
     */
    int updateCinema(SysCinema sysCinema);

    /**
     * 查询单个影院的详细信息，返回包含该影院和上映中的所有电影信息
     * @param id 影院的 ID
     * @return 返回包含影院及上映电影信息的 SysCinema 对象
     */
    SysCinema findCinemaById(Long id);

}
/*
注释说明：
findCinema：

该方法用于查询所有影院的信息，返回的是一个包含所有影院的 SysCinema 对象。
updateCinema：

该方法用于更新指定影院的信息。接收一个 SysCinema 对象并返回更新操作影响的行数。
findCinemaById：

该方法查询指定影院的详细信息，并返回该影院以及上映中的所有电影信息。返回值是一个 SysCinema 对象，其中包含影院信息和相关的电影数据。
主要功能：
SysCinemaMapper 接口是数据库访问层（Mapper），提供了用于查询和更新影院信息的方法。
findCinemaById 方法是特定影院查询方法，返回的是影院信息以及所有正在上映的电影，通常用于前台展示单个影院的详细信息。
这些方法通常会映射到 MyBatis 的 XML 文件中，定义 SQL 查询操作来访问数据库。
 */