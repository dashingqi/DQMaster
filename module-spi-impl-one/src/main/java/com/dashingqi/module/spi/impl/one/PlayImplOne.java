package com.dashingqi.module.spi.impl.one;

import com.dashingqi.module.spi.intyerface.Play;

/**
 * @author : zhangqi
 * @time : 2020/7/6
 * desc :
 */
public class PlayImplOne implements Play {

    @Override
    public void play() {
        System.out.println("PlayImplOne -----> play perform");
    }
}
