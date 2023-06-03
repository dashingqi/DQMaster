package com.dashingqi.player.init;

import com.dashingqi.player.manager.StrategyManager;
import com.dashingqi.player.strategy.TencentStrategy;

/**
 * @author : zhangqi
 * @desc :
 * @time : 2023/5/30 23:05
 */
class ZYInit {

    // 入口
    void init() {
        StrategyManager strategyManager = new StrategyManager();
        // strategyManager.setStrategy(new BiliBiliStrategy());
        strategyManager.setStrategy(new TencentStrategy());
    }
}
