package com.example.aniket.capstone_project.widgets;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UpdatePostService extends IntentService {

    public UpdatePostService() {
        super("UpdatePostService");
        System.out.println("Aniket6, UpdatePostService");
    }

    public static void startPostWidgetService(Context context, List<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdatePostService.class);
        System.out.println("fromActivityIngredientsList: " + fromActivityIngredientsList.toString());

        intent.putStringArrayListExtra(CapstoneConstants.FROM_ACTIVITY_INGREDIENTS_LIST,
                (ArrayList<String>) fromActivityIngredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList =
                    intent.getExtras().getStringArrayList(
                            CapstoneConstants.FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);

        }
    }


    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        System.out.println("Aniket6, here: " + fromActivityIngredientsList.get(0));

        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(CapstoneConstants.FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}
