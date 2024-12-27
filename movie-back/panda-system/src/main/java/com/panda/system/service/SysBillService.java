package com.panda.system.service;

import com.panda.system.domin.SysBill;

import java.util.List;

public interface SysBillService {

    /**
     * 查询所有电影订单
     * @param sysBill 查询条件对象，可能用于查询的条件（如订单状态、订单时间等）
     * @return 订单列表
     */
    List<SysBill> findAllBills(SysBill sysBill);

    /**
     * 根据订单ID查询订单信息
     * @param id 订单ID
     * @return 订单对象
     */
    SysBill findBillById(Long id);

    /**
     * 添加一个新的电影订单
     * @param sysBill 订单对象
     * @return 插入成功后的订单对象或者影响的行数
     */
    Object addBill(SysBill sysBill);

    /**
     * 更新订单信息
     * @param sysBill 更新后的订单对象
     * @return 影响的行数
     */
    int updateBill(SysBill sysBill);

    /**
     * 删除指定ID的订单
     * @param ids 订单ID数组
     * @return 删除成功的总行数
     */
    int deleteBill(Long[] ids);

    /**
     * 查询所有超时的订单
     * @return 超时订单列表
     */
    List<SysBill> findTimeoutBill();

}
/*
解释各个方法：
findAllBills(SysBill sysBill):

功能：查询所有电影订单。
参数：sysBill（查询条件，可能是某些字段的值，例如订单状态、创建时间等）。
返回：返回一个包含所有符合条件的 SysBill 对象的列表。
findBillById(Long id):

功能：根据订单 ID 查询订单详细信息。
参数：id（订单的唯一标识符）。
返回：返回对应的 SysBill 对象。
addBill(SysBill sysBill):

功能：添加一个新的电影订单。
参数：sysBill（需要插入的订单对象）。
返回：返回插入操作的结果。成功时返回插入后的订单对象，失败时返回影响的行数。
updateBill(SysBill sysBill):

功能：更新一个已存在的订单。
参数：sysBill（包含新值的订单对象）。
返回：返回影响的行数，通常为 1 表示更新成功。
deleteBill(Long[] ids):

功能：根据订单 ID 删除多个订单。
参数：ids（订单 ID 的数组）。
返回：返回删除操作影响的行数，即成功删除的订单数量。
findTimeoutBill():

功能：查询所有超时的订单。
返回：返回超时订单的列表。
总结：
SysBillService 接口定义了电影票订单相关的业务逻辑方法。它提供了对订单的查询、添加、更新、删除等功能的支持。实现类 SysBillServiceImpl 会具体实现这些方法，处理具体的业务逻辑，并调用数据访问层（Mapper）执行数据库操作。
 */
