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
import com.questhelper.steps.*;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@QuestDescriptor(
    quest = QuestHelperQuest.FREMENNIK_EASY
)

public class FremennikEasy extends ComplexStateQuestHelper
{
    //Items Required
    ItemRequirement birdSnare, coins500, tiaraMould, pickaxe, bucket, climbingBoots, axe, tinderbox, combatGear;

    //Items Recommended
    ItemRequirement teleportRellekka;

    ItemRequirement silverOre, silverBar, snapeGrass, oakLogs;

    Requirement notCaughtBird, notChangedShoes, notKilledRockCrabs, notCrafedTiara, notBrowsedStonemason,
        notCollect5SnapeGrass, notStealFromStall, notFillBucketFromWell, notEnterStronghold, notChopAndBurnOakLogs;

    QuestStep claimReward, goToHuntingArea, catchBird, changeBoots, goToRockCrabs, getPickaxe, enterTunnelNearFairyRing,
        enterCaveEntranceToKeldagrim, speakToDwarvenBoatman, goToStonemasons, talkToStonemason, talkToJarvald,
        pickupSnapeGrass, goToStallArea, goDownStairsToEastSide, goUpstairsFromEastSide, goDownStairsToWestSide,
        goUpstairsFromWestSide, chopOakTree, burnOakLog, returnWithJarvald, goThroughTrapdoorGE;

    NpcStep killRockCrabs;

    ObjectStep stealFromStall, mineSilver, smeltSilverOre, smeltSilverBar, fillBucketFromWell;

    Zone huntingArea, rellekka, keldagrim, waterbirthIsle, rockCrabArea, caveToKeldagrim, keldRiver, stallArea,
        stonemasonsShop, keldCentreF1, varrockArea;

    ZoneRequirement inHuntingArea, inRellekka, inKeldagrim, inWaterbirthIsle, inRockCrabArea, inCaveToKeldagrim,
        inKledRiver, inStallArea, inStonemasonsShop, inKeldCentreF1, inVarrockArea;

    @Override
    public QuestStep loadStep()
    {
        loadZones();
        setupRequirements();
        setupSteps();

        ConditionalStep doEasy = new ConditionalStep(this, claimReward);
        doEasy.addStep(new Conditions(notChopAndBurnOakLogs, oakLogs), burnOakLog);
        doEasy.addStep(notChopAndBurnOakLogs, chopOakTree);
        doEasy.addStep(notFillBucketFromWell, fillBucketFromWell);
        doEasy.addStep(new Conditions(notKilledRockCrabs, inRockCrabArea), killRockCrabs);
        doEasy.addStep(notKilledRockCrabs, goToRockCrabs);
        doEasy.addStep(new Conditions(notCrafedTiara, silverBar), smeltSilverBar);
        doEasy.addStep(new Conditions(notCrafedTiara, silverOre), smeltSilverOre);
        doEasy.addStep(new Conditions(notCrafedTiara, pickaxe), mineSilver);
        doEasy.addStep(notCrafedTiara, getPickaxe);
        doEasy.addStep(notChangedShoes, changeBoots);
        doEasy.addStep(new Conditions(notCollect5SnapeGrass, inWaterbirthIsle), pickupSnapeGrass);
        doEasy.addStep(notCollect5SnapeGrass, talkToJarvald);
        doEasy.addStep(inWaterbirthIsle, returnWithJarvald);
        doEasy.addStep(new Conditions(notCaughtBird, inHuntingArea), catchBird);
        doEasy.addStep(notCaughtBird, goToHuntingArea);
        doEasy.addStep(new Conditions(notStealFromStall, inStallArea), stealFromStall);
        doEasy.addStep(new Conditions(notStealFromStall, inKeldCentreF1), goDownStairsToEastSide);
        doEasy.addStep(new Conditions(notStealFromStall, inKeldagrim), goToStallArea);
        doEasy.addStep(new Conditions(notStealFromStall, inVarrockArea), goThroughTrapdoorGE);
        doEasy.addStep(new Conditions(notStealFromStall, inKledRiver), speakToDwarvenBoatman);
        doEasy.addStep(new Conditions(notStealFromStall, inCaveToKeldagrim), enterCaveEntranceToKeldagrim);
        doEasy.addStep(notStealFromStall, enterTunnelNearFairyRing);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inStonemasonsShop), talkToStonemason);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inKeldCentreF1), goDownStairsToWestSide);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inStallArea), goUpstairsFromEastSide);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inKeldagrim), goToStonemasons);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inVarrockArea), goThroughTrapdoorGE);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inKledRiver), speakToDwarvenBoatman);
        doEasy.addStep(new Conditions(notBrowsedStonemason, inCaveToKeldagrim), enterCaveEntranceToKeldagrim);
        doEasy.addStep(notBrowsedStonemason, enterTunnelNearFairyRing);


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

        teleportRellekka = new ItemRequirement("Teleport to Rellekka POH", ItemID.RELLEKKA_TELEPORT);

        silverBar = new ItemRequirement("Siver bar", ItemID.SILVER_BAR);
        silverOre = new ItemRequirement("Silver ore", ItemID.SILVER_ORE);
        snapeGrass = new ItemRequirement("Snape Grass", ItemID.SNAPE_GRASS, 5);
        oakLogs = new ItemRequirement("Oak Log", ItemID.OAK_LOGS);

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
        inRockCrabArea = new ZoneRequirement(rockCrabArea);
        inCaveToKeldagrim = new ZoneRequirement(caveToKeldagrim);
        inKledRiver = new ZoneRequirement(keldRiver);
        inStonemasonsShop = new ZoneRequirement(stonemasonsShop);
        inKeldCentreF1 = new ZoneRequirement(keldCentreF1);
        inStallArea = new ZoneRequirement(stallArea);
        inVarrockArea = new ZoneRequirement(varrockArea);
    }

    public void loadZones()
    {

        rockCrabArea = new Zone(new WorldPoint(2653, 3712, 0), new WorldPoint(2724, 3732, 0));
        huntingArea = new Zone(new WorldPoint(2702, 3752, 0), new WorldPoint(2744, 3798, 0));
        rellekka = new Zone(new WorldPoint(2590, 3626, 0), new WorldPoint(2750, 3790, 0));
        keldagrim = new Zone(new WorldPoint(2816, 10177, 0), new WorldPoint(2943, 10239, 0));
        waterbirthIsle = new Zone(new WorldPoint(2496, 3712, 0), new WorldPoint(2559, 3774, 0));
        caveToKeldagrim = new Zone(new WorldPoint(2674, 10165, 0), new WorldPoint(2806, 10127, 0));
        keldRiver = new Zone(new WorldPoint(2811, 10174, 0), new WorldPoint(2901, 10116, 0));
        stonemasonsShop = new Zone(new WorldPoint(2844, 10187, 0), new WorldPoint(2853, 10181, 0));
        keldCentreF1 = new Zone(new WorldPoint(2860, 10231, 1), new WorldPoint(2898, 10184, 1));
        stallArea = new Zone(new WorldPoint(2884, 10212, 0), new WorldPoint(2899, 10188, 0));
        varrockArea = new Zone(new WorldPoint(3067, 3512, 0), new WorldPoint(3328, 3343, 0));
    }

    public void setupSteps()
    {
        //Going to & navigating Keldagrim
        enterTunnelNearFairyRing = new ObjectStep(this, ObjectID.TUNNEL_5008, new WorldPoint(2731, 3712, 0),
                "Enter the tunnel north east of Rellekka, near the Fairy Ring (DKS).");
        enterCaveEntranceToKeldagrim = new ObjectStep(this, ObjectID.CAVE_ENTRANCE_5973, new WorldPoint(2781, 10161, 0),
        "Go through the cave entrance, to get to Keldagrim.");
        speakToDwarvenBoatman = new NpcStep(this, NpcID.DWARVEN_BOATMAN_7726, new WorldPoint(2840, 10128, 0),
                "Speak to the Dwarven Boatman to get to Keldagrim City.");
        speakToDwarvenBoatman.addDialogStep("Yes, please take me.");
        goDownStairsToEastSide = new ObjectStep(this, ObjectID.STAIRS_6088, new WorldPoint(2895, 10209, 0),
                "Go down the stairs to the east.");
        goUpstairsFromEastSide = new ObjectStep(this, ObjectID.STAIRS_6087, new WorldPoint(2894, 10209, 0),
                "Go up the stairs.");
        goDownStairsToWestSide = new ObjectStep(this, ObjectID.STAIRS_6088, new WorldPoint(2863, 10209, 0),
                "Go down the stairs to the west.");
        goUpstairsFromWestSide = new ObjectStep(this, ObjectID.STAIRS_6087, new WorldPoint(2863, 10209, 0),
                "Go up the stairs.");
        goThroughTrapdoorGE = new ObjectStep(this, ObjectID.TRAPDOOR_16168, new WorldPoint(3140, 3504, 0),
                "Go through the Trapdoor the Grand Exchange to go to Keldagrim.");

        //Catch Cerulean Twitch
        goToHuntingArea = new DetailedQuestStep(this, new WorldPoint(2730, 3763, 0),
                "Go to the hunting area north of Rellekka.", birdSnare);
        catchBird = new NpcStep(this, NpcID.CERULEAN_TWITCH, new WorldPoint(2730, 2763, 0),
                "Place your bird snare to catch a Cerulean Twitch. It may take multiple attempts.", birdSnare.highlighted());

        //Change Boots
        changeBoots = new NpcStep(this, NpcID.YRSA, new WorldPoint(2625, 3676, 0),
                "Change your boots with Yrsa. Right Click her to access her store and complete the task.", coins500);

        //Kill Rock Crabs
        goToRockCrabs = new DetailedQuestStep(this, new WorldPoint(2681, 3722, 0),
                "Go to the Rock Crabs in the northern part of Rellekka.", combatGear);
        killRockCrabs = new NpcStep(this, NpcID.ROCK_CRAB,
                "Kill 5 Rock Crabs. You will need to run over them to make them agressive.", combatGear);
        killRockCrabs.addAlternateNpcs(NpcID.ROCK_CRAB_102, NpcID.ROCKS_103, NpcID.ROCKS);

        //Craft Tiara
        getPickaxe = new DetailedQuestStep(this, new WorldPoint(2672, 3728, 0),
                "Pick up the pickaxe north of the mines. Watch out for the Rock Crabs and Hob Goblins.");
        mineSilver = new ObjectStep(this, ObjectID.ROCKS_11368, new WorldPoint(2686, 3728, 0),
                "Mine a Silver Ore in the mines in Rellekka.", pickaxe, tiaraMould);
        mineSilver.addAlternateObjects(ObjectID.ROCKS_11369);
        mineSilver.addIcon(ItemID.RUNE_PICKAXE);
        smeltSilverOre = new ObjectStep(this, ObjectID.FURNACE_4304, new WorldPoint(2616, 3666, 0),
                "Use the Silver Ore on the furnace to smelt it into a Silver Bar.", silverOre.highlighted(), tiaraMould);
        smeltSilverBar = new ObjectStep(this, ObjectID.FURNACE_4304, new WorldPoint(2616, 3666, 0),
                "Use the silver bar on the furnace to smelt it into a Silver Tiara.", silverBar.highlighted(), tiaraMould);
        smeltSilverOre.addIcon(ItemID.SILVER_ORE);
        smeltSilverBar.addIcon(ItemID.SILVER_BAR);

        //Browse Stonemason
        goToStonemasons = new DetailedQuestStep(this, new WorldPoint(2849, 10184, 0),
                "Go to the Stonemasons Store in Western Keldagrim.");
        talkToStonemason = new NpcStep(this, NpcID.STONEMASON,
                "Trade the Stonemason to browse his store and complete the task.");

        //Pickup Snape Grass
        talkToJarvald = new NpcStep(this, NpcID.JARVALD, new WorldPoint(2620, 3685, 0),
                "Speak to Jarvald to Travel to Waterbirth Island.");
        pickupSnapeGrass = new DetailedQuestStep(this, new WorldPoint(2553, 3754, 0),
                "Pick up 5 Snape Grass on Waterbirth Island. You can pick up and drop the same one again.", snapeGrass);
        returnWithJarvald = new NpcStep(this, NpcID.JARVALD_10407, new WorldPoint(2544, 3761, 0),
                "Speak to Jarvald to Return to the Mainland.");

        //Steal from stall
        goToStallArea = new DetailedQuestStep(this, new WorldPoint(2819, 10210, 0),
                "Go to the stalls in Eastern Keldagrim.");
        stealFromStall = new ObjectStep(this, ObjectID.BAKERY_STALL_6163, new WorldPoint(2819, 10210, 0),
                "Steal from the Bakery or Crafting Stall.");
        stealFromStall.addAlternateObjects(ObjectID.CRAFTING_STALL_6166);

        //Fill bucket from well
        fillBucketFromWell = new ObjectStep(this, ObjectID.WELL_8927, new WorldPoint(2668, 3660, 0),
                "Fill the bucket from the well in Rellekka.", bucket.highlighted());
        fillBucketFromWell.addIcon(ItemID.BUCKET);

        //Cut and burn an oak log
        chopOakTree = new ObjectStep(this, ObjectID.OAK_10820, new WorldPoint(2681, 3626, 0),
                "Chop an Oak tree near the POH Portal south of Rellekka.", axe.highlighted(), tinderbox);
        burnOakLog = new DetailedQuestStep(this, new WorldPoint(2653, 3623, 0),
                "Use the tinderbox on the oak logs.", oakLogs.highlighted(), tinderbox.highlighted());

        //Claim Reward
        claimReward = new NpcStep(this, NpcID.THORODIN, new WorldPoint(2658, 3627, 0),
                "Talk to Thorodin south of Rellekka to claim your reward!");
        claimReward.addDialogStep("I have a question about my Achievement Diary.");
    }

    @Override
    public List<ItemRequirement> getItemRequirements()
    {
        return Arrays.asList(axe, pickaxe, coins500, birdSnare, tiaraMould, bucket, climbingBoots, tinderbox, combatGear);
    }

    @Override
    public List<ItemRequirement> getItemRecommended()
    {
        return Arrays.asList(teleportRellekka);
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
        allSteps.add(new PanelDetails("Morning Wood", Arrays.asList(chopOakTree, burnOakLog), tinderbox, axe));
        allSteps.add(new PanelDetails("Pressure Buildup", Collections.singletonList(fillBucketFromWell), bucket));
        allSteps.add(new PanelDetails("Hard as a Rock.. Crab", Arrays.asList(goToRockCrabs, killRockCrabs), combatGear));
        allSteps.add(new PanelDetails("I could pawn this..", Arrays.asList(getPickaxe, mineSilver, smeltSilverOre, smeltSilverBar), pickaxe, tiaraMould));
        allSteps.add(new PanelDetails("New Kicks", Collections.singletonList(changeBoots), coins500));
        allSteps.add(new PanelDetails("Collecting 'Grass'", Arrays.asList(talkToJarvald, pickupSnapeGrass)));
        allSteps.add(new PanelDetails("Twitch Star", Arrays.asList(goToHuntingArea, catchBird), birdSnare));
        allSteps.add(new PanelDetails("Munchies", Arrays.asList(goToStallArea, stealFromStall)));
        allSteps.add(new PanelDetails("House upgrade", Arrays.asList(goToStonemasons, talkToStonemason)));
        allSteps.add(new PanelDetails("Congratulations!", Collections.singletonList(claimReward)));

        return allSteps;
    }
}
