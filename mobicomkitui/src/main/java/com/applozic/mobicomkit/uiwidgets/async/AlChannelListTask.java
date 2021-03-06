package com.applozic.mobicomkit.uiwidgets.async;

import android.content.Context;
import android.os.AsyncTask;

import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicommons.people.channel.Channel;

import java.lang.ref.WeakReference;
import java.util.List;

public class AlChannelListTask extends AsyncTask<Void, Void, List<Channel>> {
    private WeakReference<Context> context;
    private TaskListener callback;

    public AlChannelListTask(Context context, TaskListener callback) {
        this.context = new WeakReference<>(context);
        this.callback = callback;
    }

    @Override
    protected List<Channel> doInBackground(Void... voids) {
        return ChannelService.getInstance(context.get()).getAllChannelList();
    }

    @Override
    protected void onPostExecute(List<Channel> channelList) {
        super.onPostExecute(channelList);
        if (callback != null) {
            if (channelList == null) {
                callback.onFailure("Failed to fetch the channel list");
            } else {
                callback.onSuccess(channelList);
            }
        }
    }

    public interface TaskListener {
        void onSuccess(List<Channel> channelList);

        void onFailure(String error);
    }
}
