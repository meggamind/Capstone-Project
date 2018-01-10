package com.example.aniket.capstone_project.data.todo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aniket on 1/4/18.
 */

public class FakeData1 {


    public static List<ActivityToDo> getData(String title, Context context) {
        List<ActivityToDo> mDatas = new ArrayList<>();

        switch (title) {
            case "Alaska":
                mDatas.add(new ActivityToDo(
                        "Nothern lights",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_alaska_1.jpg")),
                        "Fairbanks",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Glacier Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_alaska_2.jpg")),
                        "Anchorage",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Dog Sledging Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_alaska_3.jpg")),
                        "Anchorage",
                        "Something"));
                break;

            case "San Francisco":
                mDatas.add(new ActivityToDo(
                        "Nothern lights",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_sf_1.jpg")
                        ),
                        "Fairbanks",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Glacier Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_sf_2.jpg")),
                        "Anchorage",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Dog Sledging Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_sf_3.jpg")
                        ),
                        "Anchorage",
                        "Something"));
                break;

            case "New York":
                mDatas.add(new ActivityToDo(
                        "Juniors CheeseCake",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_nyc_1.jpg")),
                        "Fairbanks",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Glacier Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_nyc_2.jpg")),
                        "Anchorage",
                        "Something"));

                mDatas.add(new ActivityToDo(
                        "Dog Sledging Tour",
                        new ArrayList<String>(
                                Arrays.asList("things_to_do_nyc_3.jpg")),
                        "Anchorage",
                        "Something"));
                break;
        }
        return mDatas;
    }
}
