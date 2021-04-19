package com.lxzh123.wechatmoment.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public final class HostNameManager {
    private final static class ManagerHolder {
        private final static HostNameManager instance = new HostNameManager();
    }

    public static HostNameManager get() {
        return ManagerHolder.instance;
    }

    private List<String> hostNames;

    public HostNameManager() {
        this.hostNames = new ArrayList<>();
    }

    void addHost(String host) {
        if (!hostNames.contains(host)) {
            hostNames.add(host);
        }
    }

    boolean isHostValid(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        return hostNames.contains(host);
    }
}
