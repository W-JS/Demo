package com.wjs.demo.interfaces;

public interface DemoDataSource {

    String getWallpaperList(boolean isAgain);

    void updateCurrent(String name, String val, String path);

    void getCurrent();

    void getCurrentWallpaper();
}
