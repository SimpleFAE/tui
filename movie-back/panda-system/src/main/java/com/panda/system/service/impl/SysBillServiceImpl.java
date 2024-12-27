package com.panda.system.service.impl;

import com.panda.system.domin.SysBill;
import com.panda.system.mapper.SysBillMapper;
import com.panda.system.service.SysBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBillServiceImpl implements SysBillService {

    @Autowired
    private SysBillMapper sysBillMapper;

    /**
     * 查询所有电影订单
     * @param sysBill 查询条件
     * @return 订单列表
     */
    @Override
    public List<SysBill> findAllBills(SysBill sysBill) {
        return sysBillMapper.findAllBills(sysBill);
    }

    /**
     * 根据订单ID查询订单信息
     * @param id 订单ID
     * @return 订单对象
     */
    @Override
    public SysBill findBillById(Long id) {
        return sysBillMapper.findBillById(id);
    }

    /**
     * 添加一个新的电影订单
     * @param sysBill 订单对象
     * @return 添加后的订单对象，或者影响的行数（如果插入失败）
     */
    @Override
    public Object addBill(SysBill sysBill) {
        int rows = sysBillMapper.addBill(sysBill);
        return rows > 0 ? sysBill : rows;  // 如果插入成功，返回订单对象，否则返回影响的行数
    }

    /**
     * 更新订单信息
     * @param sysBill 更新后的订单对象
     * @return 影响的行数
     */
    @Override
    public int updateBill(SysBill sysBill) {
        return sysBillMapper.updateBill(sysBill);
    }

    /**
     * 删除指定ID的订单
     * @param ids 订单ID数组
     * @return 删除成功的总行数
     */
    @Override
    public int deleteBill(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += sysBillMapper.deleteBill(id);  // 遍历删除每个订单
        }
        return rows;
    }

    /**
     * 查询所有超时的订单
     * @return 超时订单列表
     */
    @Override
    public List<SysBill> findTimeoutBill() {
        return sysBillMapper.findTimeoutBill();
    }
}
/*
解释每个方法：
findAllBills(SysBill sysBill):

查询所有电影订单。它会调用 SysBillMapper 的 findAllBills 方法，传入一个 SysBill 对象作为查询条件。返回一个包含多个 SysBill 对象的列表。
findBillById(Long id):

根据订单 ID 查询订单。返回一个 SysBill 对象，它是通过 SysBillMapper 的 findBillById 方法从数据库中查找出来的。
addBill(SysBill sysBill):

添加一个新的电影订单。调用 SysBillMapper 的 addBill 方法，向数据库中插入一个新的订单。成功插入后，返回该订单对象；如果插入失败，则返回影响的行数（通常是 0 或 1）。
updateBill(SysBill sysBill):

更新一个现有的电影订单信息。调用 SysBillMapper 的 updateBill 方法，传入一个 SysBill 对象，更新订单信息。返回影响的行数，通常是 1，表示更新成功。
deleteBill(Long[] ids):

根据订单 ID 数组删除多个订单。通过遍历 ids 数组，调用 SysBillMapper 的 deleteBill 方法删除每个订单。返回删除操作影响的总行数。
findTimeoutBill():

查询所有超时的订单，调用 SysBillMapper 的 findTimeoutBill 方法，返回所有超时订单的列表。这些订单可能是由于未支付或超时未取消的订单。
总结：
SysBillServiceImpl 类提供了电影票订单的增、删、改、查服务逻辑。它通过调用 SysBillMapper 中的方法来进行数据库操作，并对外提供服务接口（SysBillService）。这些服务方法主要用于：

获取订单列表或单个订单，
添加、更新和删除订单，
查询超时的订单。
它将数据访问层（Mapper）与业务逻辑层（Service）分离，便于维护和测试。
 */