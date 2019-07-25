package net.runelite.client.plugins.easyswap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.util.DigsiteMode;
import net.runelite.client.plugins.util.DuelingRingMode;
import net.runelite.client.plugins.util.GamesNecklaceMode;
import net.runelite.client.plugins.util.GloryMode;
import net.runelite.client.plugins.util.MaxCapeMode;

@ConfigGroup("easyswap")
public interface EasySwapConfig extends Config
{


	@ConfigItem(
		keyName = "swapSmithing",
		name = "Swap Smithing",
		description = "Enables swapping of smith-1 and smith-all options.",
		position = 0
	)

	default boolean getSwapSmithing()
	{
		return true;
	}

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
		keyName = "swapConCape",
		name = "Swap Construction Cape",
		description = "Sets left click to teleport to POH",
		position = 8
	)

	default boolean getSwapConCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCraftCape",
		name = "Swap Crafting Cape",
		description = "Left Click Teleport for Crafting Cape",
		position = 9
	)

	default boolean getSwapCraftCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapMythCape",
		name = "Swap Mythical Cape",
		description = "Left click Teleport for Mythical Cape",
		position = 10
	)

	default boolean getSwapMythCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapNPCContact",
		name = "Swap NPC Contact",
		description = "Swaps NPC Contact",
		position = 11
	)

	default boolean getSwapNPC()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapOfferEss",
		name = "Swap Offer Essence",
		description = "Swaps the left click option for offering pure essence in a trade",
		position = 12
	)

	default boolean getSwapEss()
	{
		return false;
	}

	@ConfigItem(
		keyName = "disableCraftAltar",
		name = "Disable Crafting Altar",
		description = "Prevents accidental crafting of fire runes",
		position = 13
	)

	default boolean disableCraftAltar()
	{
		return false;
	}

	@ConfigItem(
		keyName = "cancelTrades",
		name = "Cancel Teleport",
		description = "Stops teleports while in trade screen",
		position = 14
	)

	default boolean cancelTrades()
	{
		return false;
	}

	@ConfigItem(
		keyName = "tradesOnly",
		name = "Trades Only",
		description = "Show only trade option (toggle using shift) with clan members",
		position = 15
	)

	default boolean tradesOnly()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapEssencePounch",
		name = "Swap Essence Pouch",
		description = "Empty / Fill Pouches inside bank interface",
		position = 16
	)

	default boolean getSwapEssencePouch()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapEssOutsideBank",
		name = "Swap Ess Pouch Outside Bank",
		description = "Empty on left click outside of the bank",
		position = 17
	)

	default boolean getSwapEssOutsideBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapGamesNecklace",
		name = "Swap Games Necklace",
		description = "Swap Teleport for Games Necklace",
		position = 18
	)
	default boolean getGamesNecklace()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gamesNecklaceMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 19
	)

	default GamesNecklaceMode getGamesNecklaceMode()
	{
		return GamesNecklaceMode.BURTHORPE;
	}

	@ConfigItem(
		keyName = "sgamesNecklaceMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 20
	)

	default GamesNecklaceMode getSGamesNecklaceMode()
	{
		return GamesNecklaceMode.BARBARIAN_OUTPOST;
	}

	@ConfigItem(
		keyName = "swapDuelingRing",
		name = "Swap Dueling Ring",
		description = "Swap Teleport for Dueling Ring",
		position = 21
	)

	default boolean getDuelingRing()
	{
		return false;
	}

	@ConfigItem(
		keyName = "duelingRingMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 22
	)

	default DuelingRingMode getDuelingRingMode()
	{
		return DuelingRingMode.DUEL_ARENA;
	}

	@ConfigItem(
		keyName = "sduelingRingMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 23
	)

	default DuelingRingMode getSDuelingRingMode()
	{
		return DuelingRingMode.CASTLE_WARS;
	}

	@ConfigItem(
		keyName = "swapGlory",
		name = "Swap Glory",
		description = "Swap Teleport for Amulet of Glory",
		position = 24
	)

	default boolean getGlory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gloryMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 25
	)

	default GloryMode getGloryMode()
	{
		return GloryMode.EDGEVILLE;
	}

	@ConfigItem(
		keyName = "sgloryMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 26
	)

	default GloryMode getSGloryMode()
	{
		return GloryMode.KARAMJA;
	}

	@ConfigItem(
		keyName = "swapDigsite",
		name = "Swap Digsite",
		description = "Swap Teleport for Digsite Pendant",
		position = 27
	)
	default boolean getDigsite()
	{
		return false;
	}

	@ConfigItem(
		keyName = "digsiteMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 28
	)

	default DigsiteMode getDigsiteMode()
	{
		return DigsiteMode.FOSSIL_ISLAND;
	}

	@ConfigItem(
		keyName = "sdigsiteMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 29
	)
	default DigsiteMode getSDigsiteMode()
	{
		return DigsiteMode.LITHKREN_DUNGEON;
	}

	@ConfigItem(
		keyName = "swapMaxCape",
		name = "Swap Max Cape",
		description = "Swaps Options for Max Cape",
		position = 30
	)
	default boolean getMaxCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "maxCapeMode",
		name = "Mode",
		description = "Left Click Option for Max Cape",
		position = 31
	)
	default MaxCapeMode getMaxCapeMode()
	{
		return MaxCapeMode.CRAFTING_GUILD;
	}

	@ConfigItem(
		keyName = "smaxCapeMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Max Cape",
		position = 32
	)
	default MaxCapeMode getSMaxCapeMode()
	{
		return MaxCapeMode.TELE_TO_POH;
	}
}
