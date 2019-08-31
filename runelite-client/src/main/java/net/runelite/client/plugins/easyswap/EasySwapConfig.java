package net.runelite.client.plugins.easyswap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.util.DigsiteMode;
import net.runelite.client.plugins.util.DuelingRingMode;
import net.runelite.client.plugins.util.GamesNecklaceMode;
import net.runelite.client.plugins.util.GloryMode;
import net.runelite.client.plugins.util.MaxCapeMode;
import net.runelite.client.plugins.util.WealthRingMode;
import net.runelite.client.plugins.util.PharaohSceptreMode;

@ConfigGroup("easyswap")
public interface EasySwapConfig extends Config
{
	@ConfigItem(
		keyName = "swapTanning",
		name = "Swap Tanning",
		description = "Enables swapping of tan-1 and tan-all options.",
		position = 1
	)
	default boolean getSwapTanning()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCrafting",
		name = "Swap Crafting",
		description = "Enables swapping of Make-1 and Make-all options.",
		position = 2
	)
	default boolean getSwapCrafting()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapArdougneCape",
		name = "Swap Ardougne Cape",
		description = "Enables swapping of teleport and wear.",
		position = 3
	)
	default boolean getSwapArdougneCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapSawmill",
		name = "Swap Sawmill Operator",
		description = "Makes Buy-plank the default option on the sawmill operator.",
		position = 4
	)
	default boolean getSwapSawmill()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapSawmillPlanks",
		name = "Swap Buy Planks",
		description = "Makes Buy All the default option in buy planks.",
		position = 5
	)
	default boolean getSwapSawmillPlanks()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapPuroPuro",
		name = "Swap Puro Puro Wheat",
		description = "",
		position = 6
	)
	default boolean getSwapPuro()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCake",
		name = "Swap Dwarf Rock Cake",
		description = "Swaps the left click option on rock cakes from eat to guzzle",
		position = 7
	)
	default boolean getSwapCake()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapPyramidPlunder",
		name = "Swap Pyramid Plunder Start",
		description = "Start the Pyramid Plunder minigame with left click, shift click to talk normally",
		position = 8
	)
	default boolean getSwapPyramidPlunder()
	{
		return false;
	}

	@ConfigItem(
		keyName = "teleportInventory",
		name = "Teleport Inventory",
		description = "Enables left click teleport inside inventory for teleport capes enabled",
		position = 9
	)
	default boolean getTeleportInventory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapConCape",
		name = "Swap Construction Cape",
		description = "Sets left click to teleport to POH",
		position = 10
	)
	default boolean getSwapConCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCraftCape",
		name = "Swap Crafting Cape",
		description = "Left Click Teleport for Crafting Cape",
		position = 11
	)
	default boolean getSwapCraftCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapMythCape",
		name = "Swap Mythical Cape",
		description = "Left click Teleport for Mythical Cape",
		position = 12
	)
	default boolean getSwapMythCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapNPCContact",
		name = "Swap NPC Contact",
		description = "Swaps NPC Contact",
		position = 13
	)
	default boolean getSwapNPC()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapOfferEss",
		name = "Swap Offer Essence",
		description = "Swaps the left click option for offering pure essence in a trade",
		position = 14
	)
	default boolean getSwapEss()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapTalisman",
		name = "Swap Offer Talisman",
		description = "Swaps the left click option for offering earth talisman in a trade",
		position = 15
	)
	default boolean getSwapTalisman()
	{
		return false;
	}

	@ConfigItem(
		keyName = "disableCraftAltar",
		name = "Disable Crafting Altar",
		description = "Prevents accidental crafting of fire runes",
		position = 15
	)
	default boolean disableCraftAltar()
	{
		return false;
	}

	@ConfigItem(
		keyName = "cancelTrades",
		name = "Cancel Teleport & Pouches",
		description = "Stops teleports and emptying pouches while waiting for runecrafter",
		position = 16
	)
	default boolean cancelTrades()
	{
		return false;
	}

	@ConfigItem(
		keyName = "tradesOnly",
		name = "Trades Only",
		description = "Show only trade option (toggle using shift) with clan members",
		position = 17
	)
	default boolean tradesOnly()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapEssencePounch",
		name = "Swap Essence Pouch",
		description = "Empty / Fill Pouches inside bank interface",
		position = 18
	)
	default boolean getSwapEssencePouch()
	{
		return false;
	}

	@ConfigItem(
		keyName = "removeAllButFill",
		name = "Fill Only",
		description = "Removess all entries except fill for pouches in bank",
		position = 19
	)
	default boolean getFillOnly()
	{
		return false;
	}

	@ConfigItem(
		keyName = "edgevilleBank",
		name = "Prioritize Bankers",
		description = "Prioritize the southern two bankers in edgeville over the bank booth",
		position = 20
	)
	default boolean edgevilleBankers()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapGamesNecklace",
		name = "Swap Games Necklace",
		description = "Swap Teleport for Games Necklace",
		position = 21
	)
	default boolean getGamesNecklace()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gamesNecklaceMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 22
	)
	default GamesNecklaceMode getGamesNecklaceMode()
	{
		return GamesNecklaceMode.BURTHORPE;
	}

	@ConfigItem(
		keyName = "sgamesNecklaceMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 23
	)
	default GamesNecklaceMode getSGamesNecklaceMode()
	{
		return GamesNecklaceMode.BARBARIAN_OUTPOST;
	}

	@ConfigItem(
		keyName = "swapDuelingRing",
		name = "Swap Dueling Ring",
		description = "Swap Teleport for Dueling Ring",
		position = 24
	)
	default boolean getDuelingRing()
	{
		return false;
	}

	@ConfigItem(
		keyName = "duelingRingMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 25
	)
	default DuelingRingMode getDuelingRingMode()
	{
		return DuelingRingMode.DUEL_ARENA;
	}

	@ConfigItem(
		keyName = "sduelingRingMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 26
	)
	default DuelingRingMode getSDuelingRingMode()
	{
		return DuelingRingMode.CASTLE_WARS;
	}

	@ConfigItem(
		keyName = "swapGlory",
		name = "Swap Glory",
		description = "Swap Teleport for Amulet of Glory",
		position = 27
	)
	default boolean getGlory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gloryMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 28
	)
	default GloryMode getGloryMode()
	{
		return GloryMode.EDGEVILLE;
	}

	@ConfigItem(
		keyName = "sgloryMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 29
	)
	default GloryMode getSGloryMode()
	{
		return GloryMode.KARAMJA;
	}

	@ConfigItem(
		keyName = "swapDigsite",
		name = "Swap Digsite",
		description = "Swap Teleport for Digsite Pendant",
		position = 30
	)
	default boolean getDigsite()
	{
		return false;
	}

	@ConfigItem(
		keyName = "digsiteMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 31
	)
	default DigsiteMode getDigsiteMode()
	{
		return DigsiteMode.FOSSIL_ISLAND;
	}

	@ConfigItem(
		keyName = "sdigsiteMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 32
	)
	default DigsiteMode getSDigsiteMode()
	{
		return DigsiteMode.LITHKREN_DUNGEON;
	}

	@ConfigItem(
		keyName = "swapMaxCape",
		name = "Swap Max Cape",
		description = "Swaps Options for Max Cape",
		position = 33
	)
	default boolean getMaxCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "maxCapeMode",
		name = "Mode",
		description = "Left Click Option for Max Cape",
		position = 34
	)
	default MaxCapeMode getMaxCapeMode()
	{
		return MaxCapeMode.CRAFTING_GUILD;
	}

	@ConfigItem(
		keyName = "smaxCapeMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Max Cape",
		position = 35
	)
	default MaxCapeMode getSMaxCapeMode()
	{
		return MaxCapeMode.CRAFTING_GUILD;
	}

	@ConfigItem(
		keyName = "ringOfWealth",
		name = "Swap Ring Of Wealth",
		description = "Swaps Options for Ring of Wealth",
		position = 36
	)
	default boolean getRingOfWealth()
	{
		return false;
	}

	@ConfigItem(
		keyName = "ringOfWealthMode",
		name = "Mode",
		description = "Left Click Option for Ring of Wealth",
		position = 37
	)
	default WealthRingMode getRingOfWealthMode()
	{
		return WealthRingMode.GRAND_EXCHANGE;
	}

	@ConfigItem(
		keyName = "sringOfWealthMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Ring of Wealth",
		position = 38
	)
	default WealthRingMode getSRingOfWealthMode()
	{
		return WealthRingMode.REMOVE;
	}

	@ConfigItem(
		keyName = "pharaohSceptre",
		name = "Swap Pharaoh's Sceptre",
		description = "Swaps Options for Pharaoh's Sceptre",
		position = 39
	)
	default boolean getPharaohSceptre()
	{
		return false;
	}

	@ConfigItem(
		keyName = "pharaohSceptreMode",
		name = "Mode",
		description = "Left Click Option for Pharaoh's Sceptre",
		position = 40
	)
	default PharaohSceptreMode getPharaohSceptrehMode()
	{
		return PharaohSceptreMode.JALSAVRAH;
	}

	@ConfigItem(
		keyName = "spharaohSceptreMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Pharaoh's Sceptre",
		position = 41
	)
	default PharaohSceptreMode getSPharaohSceptreMode()
	{
		return PharaohSceptreMode.JALDRAOCHT;
	}
}
