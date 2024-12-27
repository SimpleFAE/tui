package com.panda.system.mapper;

import com.panda.system.domin.SysBill;

import java.util.List;

public interface SysBillMapper {

    /**
     * 查询所有电影订单
     * @param sysBill 查询条件
     * @return 订单列表
     */
    List<SysBill> findAllBills(SysBill sysBill);

    /**
     * 根据订单ID查询订单信息
     * @param id 订单ID
     * @return 单个订单
     */
    SysBill findBillById(Long id);

    /**
     * 添加一个新的电影订单
     * @param sysBill 订单对象
     * @return 影响的行数（一般为1表示添加成功）
     */
    int addBill(SysBill sysBill);

    /**
     * 更新一个订单的信息
     * @param sysBill 订单对象（包括更新后的信息）
     * @return 影响的行数（一般为1表示更新成功）
     */
    int updateBill(SysBill sysBill);

    /**
     * 根据订单ID删除订单
     * @param id 订单ID
     * @return 影响的行数（一般为1表示删除成功）
     */
    int deleteBill(Long id);

    /**
     * 查询所有超时的订单
     * @return 超时订单列表
     */
    List<SysBill> findTimeoutBill();
}
