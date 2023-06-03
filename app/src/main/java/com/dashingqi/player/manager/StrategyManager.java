package com.dashingqi.player.manager;

import com.dashingqi.player.IPlayer;

/**
 * @author : zhangqi
 * @desc :
 * @time : 2023/5/30 22:57
 */
public class StrategyManager {

    private IPlayer mIPlayer;

    public void setStrategy(IPlayer player) {
        mIPlayer = player;
    }

    public void playVideo() {
        mIPlayer.playVideo();
    }

    // 中间接
}
