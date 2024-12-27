package com.panda.system.service;

import com.panda.system.domin.SysCinema;

public interface SysCinemaService {

    /**
     * 查询所有影院的信息
     * @return 返回一个 SysCinema 对象，包含所有影院的信息
     */
    SysCinema findCinema();

    /**
     * 更新指定影院的信息
     * @param sysCinema 要更新的影院对象
     * @return 返回更新操作影响的行数
     */
    int updateCinema(SysCinema sysCinema);

    /**
     * 根据影院 ID 查询影院的详细信息，并返回该影院的相关信息
     * @param id 影院的 ID
     * @return 返回一个 SysCinema 对象，包含该影院和上映中的电影信息
     */
    SysCinema findCinemaById(Long id);

}
/*
注释说明：
findCinema() 方法：

用于查询所有影院的信息，返回一个 SysCinema 对象，通常包含一个影院列表或其他相关信息。
updateCinema() 方法：

用于更新指定的影院信息。接收一个 SysCinema 对象作为参数，更新该影院的属性，并返回更新操作的影响行数（通常是受影响的数据库记录数）。
findCinemaById() 方法：

用于根据影院的 id 查询一个指定影院的详细信息，并返回包含该影院及上映中的电影信息的 SysCinema 对象。
总结：
SysCinemaService 接口定义了三个主要方法，分别用于：

查询所有影院信息
更新指定影院信息
根据影院 ID 查询影院详细信息
该接口为影院相关业务提供了服务层的抽象，业务逻辑的实现由 SysCinemaServiceImpl 类完成
 */