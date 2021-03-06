package com.dashingqi.dqlaunchstarter;

import android.os.Looper;
import android.os.MessageQueue;

import com.dashingqi.dqlaunchstarter.dispatch.DispatchRunnable;
import com.dashingqi.dqlaunchstarter.task.Task;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : zhangqi
 * @time : 2020/10/10
 * desc :
 */
public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
