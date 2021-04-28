/*
 * Copyright (c) 2021, Kerpackie <https://github.com/Kerpackie/>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.questhelper.achievementdiaries.fremennik;

import com.questhelper.ItemCollections;
import com.questhelper.QuestDescriptor;
import com.questhelper.QuestHelperQuest;
import com.questhelper.Zone;
import com.questhelper.banktab.BankSlotIcons;
import com.questhelper.panel.PanelDetails;
import com.questhelper.questhelpers.ComplexStateQuestHelper;
import com.questhelper.requirements.Requirement;
import com.questhelper.requirements.ZoneRequirement;
import com.questhelper.requirements.conditional.Conditions;
import com.questhelper.requirements.item.ItemRequirement;
import com.questhelper.requirements.player.SkillRequirement;
import com.questhelper.requirements.quest.QuestRequirement;
import com.questhelper.requirements.util.LogicType;
import com.questhelper.requirements.var.VarplayerRequirement;
import com.questhelper.steps.ConditionalStep;
import com.questhelper.steps.QuestStep;
import net.runelite.api.ItemID;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@QuestDescriptor(
    quest = QuestHelperQuest.FREMENNIK_EASY
)

public class FremennikEasy extends ComplexStateQuestHelper
{
    //Items Required
    ItemRequirement birdSnare, coins500, tiaraMould, pickaxe, bucket, climbingBoots, axe, tinderbox, combatGear;

    //Items Recommended
    ItemRequirement teleportRelekka;

    ItemRequirement silverOre, silverbar;

    Requirement notCaughtBird, notChangedShoes, notKilledRockCrabs, notCrafedTiara, notBrowsedStonemason,
            notCollect5SnapeGrass, notStealFromStall, notFillBucketFromWell, notEnterStronghold, notChopAndBurnOakLogs;

    QuestStep claimReward;

    Zone huntingArea, rellekka, keldagrim, waterbirthIsle;

    ZoneRequirement inHuntingArea, inRellekka, inKeldagrim, inWaterbirthIsle;

    @Override
    public QuestStep loadStep()
    {
        loadZones();
        setupRequirements();
        setupSteps();

        ConditionalStep doEasy = new CondtionalStep(this, claimReward);

        return doEasy;
    }

    public void setupRequirements()
    {
        birdSnare = new ItemRequirement("Birdsnare", ItemID.BIRD_SNARE).showConditioned(notCaughtBird);
        coins500 = new ItemRequirement("Coins", ItemID.COINS_995, 500).showConditioned(notChangedShoes);
        tiaraMould = new ItemRequirement("Tiara Mould", ItemID.TIARA_MOULD).showConditioned(notCrafedTiara);
        pickaxe = new ItemRequirement("Pickaxe", ItemCollections.getPickaxes()).showConditioned(notCrafedTiara);
        bucket = new ItemRequirement("Bucket", ItemID.BUCKET).showConditioned(notFillBucketFromWell);
        climbingBoots = new ItemRequirement("Climbing Boots", ItemID.CLIMBING_BOOTS).showConditioned(notEnterStronghold);
        axe = new ItemRequirement("Axe", ItemCollections.getAxes()).showConditioned(notChopAndBurnOakLogs);
        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX).showConditioned(notChopAndBurnOakLogs);
        combatGear = new ItemRequirement("Combat Gear", -1, -1).showConditioned(notKilledRockCrabs);
        combatGear.setDisplayItemId(BankSlotIcons.getCombatGear());

        teleportRelekka = new ItemRequirement("Teleport to Relekka POH", ItemID.RELLEKKA_TELEPORT);

        silverbar = new ItemRequirement("Siver bar", ItemID.SILVER_BAR);
        silverOre = new ItemRequirement("Silver ore", ItemID.SILVER_ORE);

        notCaughtBird = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 0));
        notChangedShoes = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 1));
        notKilledRockCrabs = new Conditions(LogicType.NOR,  new VarplayerRequirement(1184, true, 2));
        notCrafedTiara = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 3));
        notBrowsedStonemason = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 4));
        notCollect5SnapeGrass = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 5));
        notStealFromStall = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 7));
        notFillBucketFromWell = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 7));
        notEnterStronghold = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 8));
        notChopAndBurnOakLogs = new Conditions(LogicType.NOR, new VarplayerRequirement(1184, true, 9));

        inHuntingArea = new ZoneRequirement(huntingArea);
        inRellekka = new ZoneRequirement(rellekka);
        inKeldagrim = new ZoneRequirement(keldagrim);
        inWaterbirthIsle = new ZoneRequirement(waterbirthIsle);

    }

    public void loadZones()
    {
        huntingArea = new Zone(new WorldPoint(2702, 3752, 0), new WorldPoint(2744, 3798, 0));
        rellekka = new Zone(new WorldPoint(2590, 3626, 0), new WorldPoint(2750, 3790, 0));
        keldagrim = new Zone(new WorldPoint(2816, 10177, 0), new WorldPoint(2943, 10239, 0));
        waterbirthIsle = new Zone(new WorldPoint(2496, 3712, 0), new WorldPoint(2559, 3774, 0));
    }

    public void setupSteps()
    {

    }

    @Override
    public List<ItemRequirement> getItemRequirements()
    {
        return Arrays.asList(axe, pickaxe, coins500, birdSnare, tiaraMould, bucket, climbingBoots, tinderbox, combatGear);
    }

    @Override
    public List<ItemRequirement> getItemRecommended()
    {
        return Arrays.asList(teleportRelekka);
    }


    @Override
    public List<Requirement> getGeneralRequirements()
    {
        ArrayList<Requirement> req = new ArrayList<>();

        req.add(new SkillRequirement(Skill.CRAFTING, 23, true));
        req.add(new SkillRequirement(Skill.FIREMAKING, 15, true));
        req.add(new SkillRequirement(Skill.HUNTER, 11, true));
        req.add(new SkillRequirement(Skill.MINING, 20, true));
        req.add(new SkillRequirement(Skill.SMITHING, 20, true));
        req.add(new SkillRequirement(Skill.THIEVING, 5, true));
        req.add(new SkillRequirement(Skill.WOODCUTTING, 15, true));

        req.add(new QuestRequirement(QuestHelperQuest.THE_FREMENNIK_TRIALS, QuestState.FINISHED));
        req.add(new QuestRequirement(QuestHelperQuest.THE_GIANT_DWARF, QuestState.FINISHED));
        req.add(new QuestRequirement(QuestHelperQuest.TROLL_STRONGHOLD, QuestState.FINISHED));

        return req;
    }

    public List<PanelDetails> getPanels()
    {
        List<PanelDetails> allSteps = new ArrayList<>();

        return allSteps;
    }
}
