package com.panda.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: SessionSeatsUtil 工具类用于管理影厅座位状态，包括选座与取消选座的功能。
 * @author: chengho
 * @create: 2021-06-02 15:59
 */
public class SessionSeatsUtil {

    /**
     * 更改影厅座位状态，根据当前影厅座位图和选中的座位进行座位状态更新
     *
     * @param curSessionSeats 当前影厅座位状态（JSON格式），座位被选中标记为1，空座标记为0
     * @param selectSeats     用户选中的座位，格式为“xx排xx座”，多个座位用逗号分隔
     * @return 返回更新后的影厅座位状态（JSON格式）
     */
    public static String changeSessionSeats(String curSessionSeats, String selectSeats) {
        // 将传入的当前影厅座位状态字符串解析为JSONObject
        JSONObject curSessionSeatsJSON = JSONObject.parseObject(curSessionSeats);

        // 定义一个有序的Map，用来存储超时订单的已选座位，键为行号，值为列号（座位号）
        Map<String, Integer> selectedSeatsMap = new LinkedHashMap<>();

        // 将选中的座位字符串（例如"1排3座,2排5座"）拆分为多个单独的座位
        String[] selectedSeats = selectSeats.split(",");
        for (int i = 0; i < selectedSeats.length; i++) {
            // 提取座位的行号，例如从"1排3座"中提取行号1
            String row = selectedSeats[i].substring(selectedSeats[i].indexOf("\"") + 1, selectedSeats[i].indexOf("排"));
            // 提取座位的列号（座位号），例如从"1排3座"中提取座位号3
            Integer col = Integer.parseInt(selectedSeats[i].substring(selectedSeats[i].indexOf("排") + 1, selectedSeats[i].indexOf("座")));
            // 将行号与座位号存储到Map中
            selectedSeatsMap.put(row, col);
        }

        // 输出已选座位的行号和列号信息（用于调试）
        selectedSeatsMap.forEach((key, value) -> {
            System.out.println("key = " + key + " value = " + value);
        });

        // 创建一个Map用于存储当前影厅座位的副本，用于修改座位状态
        Map<String, Object> valueMap = new LinkedHashMap<>();
        valueMap.putAll(curSessionSeatsJSON);

        // 输出当前影厅座位状态的详细信息（用于调试）
        valueMap.forEach((key, value) -> System.out.println("\"" + key + "\":" + "   " + value));

        // 遍历已选座位Map，取消选座操作
        selectedSeatsMap.forEach((index, value) -> {
            // 将选中的座位状态从1（已选）变为0（取消选座）
            ((JSONArray) valueMap.get(index)).set(value - 1, 0);  // value-1 是因为数组下标从0开始
        });

        // 将修改后的座位状态转换为JSON对象
        JSONObject newSessionSeatsJSON = new JSONObject(valueMap);

        // 返回修改后的影厅座位状态的JSON字符串
        return JSONObject.toJSONString(newSessionSeatsJSON);
    }
}

