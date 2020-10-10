package com.dashingqi.dqlaunchstarter.task;

/**
 * @author : zhangqi
 * @time : 2020/10/10
 * desc :
 */
public abstract class MainTask extends Task {
    @Override
    public boolean runOnMainThread() {
        return true;
    }
}