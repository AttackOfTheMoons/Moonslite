package net.runelite.client.plugins.easyswap;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.plugins.util.DigsiteMode;
import net.runelite.client.plugins.util.DuelingRingMode;
import net.runelite.client.plugins.util.GamesNecklaceMode;
import net.runelite.client.plugins.util.GloryMode;
import net.runelite.client.plugins.util.MaxCapeMode;
import net.runelite.client.plugins.util.PharaohSceptreMode;
import net.runelite.client.plugins.util.WealthRingMode;

@ConfigGroup("easyswap")
public interface EasySwapConfig extends Config
{
	@ConfigItem(
		keyName = "swapArdougneCape",
		name = "Swap Ardougne Cape",
		description = "Enables swapping of teleport and wear.",
		position = 1
	)
	default boolean getSwapArdougneCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCake",
		name = "Swap Dwarf Rock Cake",
		description = "Swaps the left click option on rock cakes from eat to guzzle",
		position = 2
	)
	default boolean getSwapCake()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapPyramidPlunder",
		name = "Swap Pyramid Plunder Start",
		description = "Start the Pyramid Plunder minigame with left click, shift click to talk normally",
		position = 3
	)
	default boolean getSwapPyramidPlunder()
	{
		return false;
	}

	@ConfigItem(
		keyName = "teleportInventory",
		name = "Teleport Inventory",
		description = "Enables left click teleport inside inventory for teleport capes enabled",
		position = 4
	)
	default boolean getTeleportInventory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapConCape",
		name = "Swap Construction Cape",
		description = "Sets left click to teleport to POH",
		position = 5
	)
	default boolean getSwapConCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCraftCape",
		name = "Swap Crafting Cape",
		description = "Left Click Teleport for Crafting Cape",
		position = 6
	)
	default boolean getSwapCraftCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapMythCape",
		name = "Swap Mythical Cape",
		description = "Left click Teleport for Mythical Cape",
		position = 7
	)
	default boolean getSwapMythCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapNPCContact",
		name = "Swap NPC Contact",
		description = "Swaps NPC Contact",
		position = 12
	)
	default boolean getSwapNPC()
	{
		return false;
	}

	@ConfigItem(
			keyName = "swapNecklacet",
			name = "Swap Binding necklace",
			description = "Have binding necklace to Use instead of Wear ",
			position = 13
	)
	default boolean getNecklace()
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
		keyName = "disableMagicImbue",
		name = "Disable Magic Imbue",
		description = "Disables Magic Imbue on the second trade screen",
		position = 16
	)
	default boolean disableMagicImbue()
	{
		return false;
	}

	@ConfigItem(
		keyName = "disableEarthRune",
		name = "Disable Use Earth Rune",
		description = "Disables Use Earth Rune on the second trade screen",
		position = 17
	)
	default boolean disableUseEarthRune()
	{
		return false;
	}

	@ConfigItem(
		keyName = "cancelTrades",
		name = "Cancel Teleport & Pouches",
		description = "Stops teleports and emptying pouches while waiting for runecrafter",
		position = 18
	)
	default boolean cancelTrades()
	{
		return false;
	}

	@ConfigItem(
		keyName = "tradesOnly",
		name = "Trades Only",
		description = "Show only trade option (toggle using shift) with clan members",
		position = 19
	)
	default boolean tradesOnly()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapEssencePounch",
		name = "Swap Essence Pouch",
		description = "Empty / Fill Pouches inside bank interface",
		position = 20
	)
	default boolean getSwapEssencePouch()
	{
		return false;
	}

	@ConfigItem(
		keyName = "removeAllButFill",
		name = "Fill Only",
		description = "Removess all entries except fill for pouches in bank",
		position = 21
	)
	default boolean getFillOnly()
	{
		return false;
	}

	@ConfigItem(
		keyName = "edgevilleBank",
		name = "Prioritize Bankers",
		description = "Prioritize the southern two bankers in edgeville over the bank booth",
		position = 22
	)
	default boolean edgevilleBankers()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapGamesNecklace",
		name = "Swap Games Necklace",
		description = "Swap Teleport for Games Necklace",
		position = 23
	)
	default boolean getGamesNecklace()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gamesNecklaceMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 24
	)
	default GamesNecklaceMode getGamesNecklaceMode()
	{
		return GamesNecklaceMode.BURTHORPE;
	}

	@ConfigItem(
		keyName = "sgamesNecklaceMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 25
	)
	default GamesNecklaceMode getSGamesNecklaceMode()
	{
		return GamesNecklaceMode.BARBARIAN_OUTPOST;
	}

	@ConfigItem(
		keyName = "swapDuelingRing",
		name = "Swap Dueling Ring",
		description = "Swap Teleport for Dueling Ring",
		position = 26
	)
	default boolean getDuelingRing()
	{
		return false;
	}

	@ConfigItem(
		keyName = "duelingRingMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 27
	)
	default DuelingRingMode getDuelingRingMode()
	{
		return DuelingRingMode.DUEL_ARENA;
	}

	@ConfigItem(
		keyName = "sduelingRingMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 28
	)
	default DuelingRingMode getSDuelingRingMode()
	{
		return DuelingRingMode.CASTLE_WARS;
	}

	@ConfigItem(
		keyName = "swapGlory",
		name = "Swap Glory",
		description = "Swap Teleport for Amulet of Glory",
		position = 29
	)
	default boolean getGlory()
	{
		return false;
	}

	@ConfigItem(
		keyName = "gloryMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 30
	)
	default GloryMode getGloryMode()
	{
		return GloryMode.EDGEVILLE;
	}

	@ConfigItem(
		keyName = "sgloryMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 31
	)
	default GloryMode getSGloryMode()
	{
		return GloryMode.KARAMJA;
	}

	@ConfigItem(
		keyName = "swapDigsite",
		name = "Swap Digsite",
		description = "Swap Teleport for Digsite Pendant",
		position = 32
	)
	default boolean getDigsite()
	{
		return false;
	}

	@ConfigItem(
		keyName = "digsiteMode",
		name = "Mode",
		description = "Left Click Teleport Option",
		position = 33
	)
	default DigsiteMode getDigsiteMode()
	{
		return DigsiteMode.FOSSIL_ISLAND;
	}

	@ConfigItem(
		keyName = "sdigsiteMode",
		name = "Shift Mode",
		description = "Shift + Left Click Teleport Option",
		position = 34
	)
	default DigsiteMode getSDigsiteMode()
	{
		return DigsiteMode.LITHKREN_DUNGEON;
	}

	@ConfigItem(
		keyName = "swapMaxCape",
		name = "Swap Max Cape",
		description = "Swaps Options for Max Cape",
		position = 35
	)
	default boolean getMaxCape()
	{
		return false;
	}

	@ConfigItem(
		keyName = "maxCapeMode",
		name = "Mode",
		description = "Left Click Option for Max Cape",
		position = 36
	)
	default MaxCapeMode getMaxCapeMode()
	{
		return MaxCapeMode.CRAFTING_GUILD;
	}

	@ConfigItem(
		keyName = "smaxCapeMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Max Cape",
		position = 37
	)
	default MaxCapeMode getSMaxCapeMode()
	{
		return MaxCapeMode.CRAFTING_GUILD;
	}

	@ConfigItem(
		keyName = "ringOfWealth",
		name = "Swap Ring Of Wealth",
		description = "Swaps Options for Ring of Wealth",
		position = 38
	)
	default boolean getRingOfWealth()
	{
		return false;
	}

	@ConfigItem(
		keyName = "ringOfWealthMode",
		name = "Mode",
		description = "Left Click Option for Ring of Wealth",
		position = 39
	)
	default WealthRingMode getRingOfWealthMode()
	{
		return WealthRingMode.GRAND_EXCHANGE;
	}

	@ConfigItem(
		keyName = "sringOfWealthMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Ring of Wealth",
		position = 40
	)
	default WealthRingMode getSRingOfWealthMode()
	{
		return WealthRingMode.REMOVE;
	}

	@ConfigItem(
		keyName = "pharaohSceptre",
		name = "Swap Pharaoh's Sceptre",
		description = "Swaps Options for Pharaoh's Sceptre",
		position = 41
	)
	default boolean getPharaohSceptre()
	{
		return false;
	}

	@ConfigItem(
		keyName = "pharaohSceptreMode",
		name = "Mode",
		description = "Left Click Option for Pharaoh's Sceptre",
		position = 42
	)
	default PharaohSceptreMode getPharaohSceptrehMode()
	{
		return PharaohSceptreMode.JALSAVRAH;
	}

	@ConfigItem(
		keyName = "spharaohSceptreMode",
		name = "Shift Mode",
		description = "Shift + Left Click Option for Pharaoh's Sceptre",
		position = 43
	)
	default PharaohSceptreMode getSPharaohSceptreMode()
	{
		return PharaohSceptreMode.JALDRAOCHT;
	}
}
