package com.ecarxclub.app.utils.picker.common;


import com.ecarxclub.app.utils.picker.listener.OnItemPickListener;
import com.ecarxclub.app.utils.picker.widget.WheelView;

final public class OnItemPickedRunnable implements Runnable {

    final private WheelView wheelView;
    private OnItemPickListener onItemPickListener;

    public OnItemPickedRunnable(WheelView wheelView, OnItemPickListener onItemPickListener) {
        this.wheelView = wheelView;
        this.onItemPickListener = onItemPickListener;
    }

    @Override
    public final void run() {
        onItemPickListener.onItemPicked(wheelView.getCurrentPosition(),wheelView.getCurrentItem());
    }
}
