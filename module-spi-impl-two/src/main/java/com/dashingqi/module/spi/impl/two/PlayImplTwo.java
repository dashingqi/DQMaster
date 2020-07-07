package com.dashingqi.module.spi.impl.two;

import com.dashingqi.module.spi.intyerface.Play;

/**
 * @author : zhangqi
 * @time : 2020/7/6
 * desc :
 */
public class PlayImplTwo implements Play {
    @Override
    public void play() {
        System.out.println("PlayImplTwo ------> play perform");
    }
}
