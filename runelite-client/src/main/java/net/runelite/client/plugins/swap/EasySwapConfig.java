package net.runelite.client.plugins.swap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.util.DuelingRingMode;
import net.runelite.client.plugins.util.EssenceMode;
import net.runelite.client.plugins.util.GamesNecklaceMode;
import net.runelite.client.plugins.util.GloryMode;

@ConfigGroup("easyswap")
public interface EasySwapConfig extends Config {


    @ConfigItem(
            keyName = "swapSmithing",
            name = "Swap Smithing",
            description = "Enables swapping of smith-1 and smith-all options.",
            position = 1
    )

    default boolean getSwapSmithing() {
        return true;
    }

    @ConfigItem(
            keyName = "swapTanning",
            name = "Swap Tanning",
            description = "Enables swapping of tan-1 and tan-all options.",
            position = 2
    )

    default boolean getSwapTanning() {
        return true;
    }

    @ConfigItem(
            keyName = "swapCrafting",
            name = "Swap Crafting",
            description = "Enables swapping of Make-1 and Make-all options.",
            position = 3
    )

    default boolean getSwapCrafting() {
        return true;
    }

    @ConfigItem(
            keyName = "swapArdougneCape",
            name = "Swap Ardougne Cape",
            description = "Enables swapping of teleport and wear.",
            position = 4
    )

    default boolean getSwapArdougneCape() {
        return true;
    }

    @ConfigItem(
            keyName = "swapSawmill",
            name = "Swap Sawmill Operator",
            description = "Makes Buy-plank the default option on the sawmill operator.",
            position = 5
    )

    default boolean getSwapSawmill() {
        return true;
    }

    @ConfigItem(
            keyName = "swapSawmillPlanks",
            name = "Swap Buy Planks",
            description = "Makes Buy All the default option in buy planks.",
            position = 6
    )

    default boolean getSwapSawmillPlanks() {
        return true;
    }

    @ConfigItem(
            keyName = "swapPuroPuro",
            name = "Swap Puro Puro Wheat",
            description = "",
            position = 7
    )

    default boolean getSwapPuro() {
        return true;
    }

    @ConfigItem(
            keyName = "swapCake",
            name = "Swap Dwarf Rock Cake",
            description = "Swaps the left click option on rock cakes from eat to guzzle",
            position = 8
    )

    default boolean getSwapCake() {
        return true;
    }

    @ConfigItem(
            keyName = "swapFish",
            name = "Drop Barbarian Fish",
            description = "Left click option to drop fish",
            position = 9
    )

    default boolean getSwapFish() {
        return false;
    }

    @ConfigItem(
            keyName = "swapAntidote",
            name = "Use Antidote",
            description = "Left click option to use antidote++",
            position = 10
    )

    default boolean getSwapAntidote() {
        return true;
    }

    @ConfigItem(
            keyName = "swapConCape",
            name = "Swap Construction Cape",
            description = "Sets left click to teleport to POH",
            position = 11
    )

    default boolean getSwapConCape() {
        return true;
    }

    @ConfigItem(
            keyName = "swapCraftCape",
            name = "Swap Crafting Cape",
            description = "Left Click Teleport for Crafting Cape",
            position = 12
    )

    default boolean getSwapCraftCape() {
        return true;
    }

    @ConfigItem(
            keyName = "swapNPCContact",
            name = "Swap NPC Contact",
            description = "Swaps NPC Contact",
            position = 13
    )

    default boolean getSwapNPC() {
        return false;
    }

    @ConfigItem(
            keyName = "swapOfferEss",
            name = "Swap Offer Essence",
            description = "Swaps the left click option for offering pure essence in a trade",
            position = 14
    )

    default boolean getSwapEss() {
        return false;
    }

    @ConfigItem(
            keyName = "BindingNecklaces",
            name = "Examine Binding Necklace",
            description = "Examine Binding Necklace on Left click",
            position = 15
    )

    default boolean getBindingNeck() {
        return false;
    }

    @ConfigItem(
            keyName = "disableCraftAltar",
            name = "Disable Crafting Altar",
            description = "Prevents accidental crafting of fire runes",
            position = 16
    )

    default boolean disableCraftAltar(){
        return false;
    }

    @ConfigItem(
            keyName = "swapEssencePounch",
            name = "Swap Essence Pouch",
            description = "Empty / Fill Pouches inside bank interface",
            position = 17
    )

    default boolean getSwapEssencePouch() {
        return false;
    }

    @ConfigItem(
            keyName = "essenceMode",
            name = "Mode",
            description = "Mode for swapping the essence pouches",
            position = 18
    )

    default EssenceMode getEssenceMode() {
        return EssenceMode.RUNECRAFTING;
    }

    @ConfigItem(
            keyName = "swapEssOutsideBank",
            name = "Swap Ess Pouch Outside Bank",
            description = "Empty on left click outside of the bank",
            position = 19
    )

    default boolean getSwapEssOutsideBank()
    {
        return true;
    }

    @ConfigItem(
            keyName = "swapGamesNecklace",
            name = "Swap Games Necklace",
            description = "Left Click Teleport for Games Necklace",
            position = 20
    )
    default boolean getGamesNecklace() {
        return true;
    }

    @ConfigItem(
            keyName = "gamesNecklaceMode",
            name = "Mode",
            description = "Left Click Teleport Option",
            position = 21
    )

    default GamesNecklaceMode getGamesNecklaceMode() {
        return GamesNecklaceMode.BURTHORPE;
    }
    @ConfigItem(
            keyName = "sgamesNecklaceMode",
            name = "Shift Mode",
            description = "Shift + Left Click Teleport Option",
            position = 22
    )

    default GamesNecklaceMode getSGamesNecklaceMode() {
        return GamesNecklaceMode.BARBARIAN_OUTPOST;
    }

    @ConfigItem(
            keyName = "swapDuelingRing",
            name = "Swap Dueling Ring",
            description = "Left Click Teleport for Dueling Ring",
            position = 23
    )

    default boolean getDuelingRing() {
        return true;
    }

    @ConfigItem(
            keyName = "duelingRingMode",
            name = "Mode",
            description = "Left Click Teleport Option",
            position = 24
    )

    default DuelingRingMode getDuelingRingMode() {
        return DuelingRingMode.DUEL_ARENA;
    }

    @ConfigItem(
            keyName = "shiftduelingRingMode",
            name = "Shift Mode",
            description = "Shift + Left Click Teleport Option",
            position = 25
    )

    default DuelingRingMode getSDuelingRingMode() {
        return DuelingRingMode.CASTLE_WARS;
    }

    @ConfigItem(
            keyName = "swapGlory",
            name = "Swap Glory",
            description = "Left Click Teleport for Amulet of Glory",
            position = 26
    )

    default boolean getGlory() {
        return true;
    }

    @ConfigItem(
            keyName = "gloryMode",
            name = "Mode",
            description = "Left Click Teleport Option",
            position = 27
    )

    default GloryMode getGloryMode() {
        return GloryMode.EDGEVILLE;
    }
    @ConfigItem(
            keyName = "sgloryMode",
            name = "Shift Mode",
            description = "Shift + Left Click Teleport Option",
            position = 28
    )

    default GloryMode getSGloryMode() {
        return GloryMode.KARAMJA;
    }

}
