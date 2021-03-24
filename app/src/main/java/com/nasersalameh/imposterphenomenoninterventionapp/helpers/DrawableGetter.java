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
                iconID = R.drawable.ic_achievement_deadline;
                break;
            case "Overcoming Blocker":
                iconID = R.drawable.ic_achievement_blocker;
                break;
            case "Task Size":
                iconID = R.drawable.ic_achievement_task_list;
                break;
            case "Ability Boost":
                iconID = R.drawable.ic_achievement_ability;
                break;
            case "Aligned Expectation":
                iconID = R.drawable.ic_achievement_expectation;
                break;
            case "Personal Goal":
                iconID = R.drawable.ic_achievement_personal;
                break;
            case "Completed Assignment":
                iconID = R.drawable.ic_achievement_assignment;
                break;
            case "Received Feedback":
                iconID = R.drawable.ic_achievement_feedback;
                break;
            case "Reached Milestone":
                iconID = R.drawable.ic_achievement_milestone;
                break;
            case "Self-Reflection":
                iconID = R.drawable.ic_achievement_reflection;
                break;
            case "Competition/Award Win":
                iconID = R.drawable.ic_achievement_award;
                break;
            case "Other":
                iconID = R.drawable.ic_achievement_other;
                break;
        }

        return iconID;
    }

}
