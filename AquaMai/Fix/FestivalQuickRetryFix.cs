﻿using AquaMai.Attributes;
using HarmonyLib;
using Manager;

namespace AquaMai.Fix;

[GameVersion(23000, 23499)]
public class FestivalQuickRetryFix
{
    // Fix for the game not resetting Fast and Late counts when quick retrying
    // For game version < 1.35.0
    [HarmonyPostfix]
    [HarmonyPatch(typeof(GamePlayManager), "SetQuickRetryFrag")]
    public static void PostGamePlayManagerSetQuickRetryFrag(GamePlayManager __instance, bool flag)
    {
        // Since 1.35.0, `GameScoreList.Initialize()` resets the Fast and Late counts
        if (flag && !Traverse.Create(typeof(GameScoreList)).Methods().Contains("Initialize"))
        {
            for (int i = 0; i < 4; i++)
            {
                var gameScoreList = __instance.GetGameScore(i);
                var traverse = Traverse.Create(gameScoreList);
                traverse.Property("Fast").SetValue((uint)0);
                traverse.Property("Late").SetValue((uint)0);
            }
        }
    }
}
