package com.dashingqi.dqmvvmbase.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Method;

/**
 * @author : zhangqi
 * @time : 2/7/21
 * desc : 防止数据倒灌的 MutableLiveData
 */
public class LostMutableLiveData<T> extends MutableLiveData<T> {

    public LostMutableLiveData() {
        super();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, new WrapperObserver<T>(observer, this));
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        super.observeForever(new WrapperObserver<T>(observer, this));
    }

    /**
     * 通过反射调用到LiveData中的getVersion()方法，拿到version
     *
     * @return
     */
    private int reflectGetVersion() {
        try {
            Method method = LiveData.class.getDeclaredMethod("getVersion");
            method.setAccessible(true);
            return (int) method.invoke(this);
        } catch (Exception exception) {
            throw new RuntimeException("Live data exception,perform getVersion");
        }
    }

    static class WrapperObserver<T> implements Observer<T> {

        Observer<? super T> mObserver;
        LostMutableLiveData<T> mLostMutableLiveData;
        int version = 0;

        public WrapperObserver(Observer<? super T> observer, LostMutableLiveData<T> liveData) {
            mObserver = observer;
            mLostMutableLiveData = liveData;
            version = mLostMutableLiveData.reflectGetVersion();
        }

        @Override
        public void onChanged(T t) {
            if (version >= mLostMutableLiveData.reflectGetVersion()) {
                return;
            }
            version++;
            mObserver.onChanged(t);
        }
    }
}
