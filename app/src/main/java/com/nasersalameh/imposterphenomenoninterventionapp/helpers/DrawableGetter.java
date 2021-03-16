package com.nasersalameh.imposterphenomenoninterventionapp.helpers;

import com.nasersalameh.imposterphenomenoninterventionapp.R;

public class DrawableGetter {

    public static int getAchievementDrawable(String drawableName){
        int iconID = 0;
        switch(drawableName){
            case "Small Goal":
                iconID =  R.drawable.ic_achievement_small_goal;
                break;
            case "Medium Goal":
                iconID = R.drawable.ic_achievement_medium_goal;
                break;
            case "Large Goal":
                iconID = R.drawable.ic_achievement_large_goal;
                break;
            case "Meeting Deadline":
                iconID = R.drawable.ic_achievement_cips_completion;
                break;
            case "Overcoming Blocker":
                iconID = R.drawable.ic_achievement_information_completion;
                break;
            case "Task Size":
                iconID = R.drawable.ic_achievement_streak;
                break;
            case "Ability Boost":
                iconID = R.drawable.ic_achievement_growth;
                break;
            case "Aligned Expectation":
                iconID = R.drawable.ic_achievement_growth;
                break;

            case "Test1":
                iconID = R.drawable.ic_achievement_growth;
                break;

            case "Test2":
                iconID = R.drawable.ic_achievement_growth;
                break;
        }

        return iconID;
    }

}
