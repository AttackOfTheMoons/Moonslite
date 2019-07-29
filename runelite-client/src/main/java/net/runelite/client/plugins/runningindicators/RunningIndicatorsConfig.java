package net.runelite.client.plugins.runningindicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigGroup;

@ConfigGroup("runningindicators")
public interface RunningIndicatorsConfig extends Config
{
	@ConfigItem(
		keyName = "tradeBindingNecklace",
		name = "Trade Binding Necklace",
		description = "Highlights binding necklace when the runecrafter has 25 inventory slots",
		position = 0
	)
	default boolean getTradeBinding()
	{
		return false;
	}

	@ConfigItem(
		keyName = "bindingNeckColor",
		name = "Binding Necklace Color",
		description = "Color for the binding necklace marker",
		position = 1
	)
	default Color getBindingColor()
	{
		return Color.GREEN;
	}

	@ConfigItem(
		keyName = "ringOfDuelingMarker",
		name = "Mark Ring of Dueling",
		description = "Highlights Ring of Dueling in your bank if not wearing one",
		position = 2
	)
	default boolean getRingOfDuelingMarker()
	{
		return false;
	}

	@ConfigItem(
		keyName = "bindingNeckColor",
		name = "Ring of Dueling Color",
		description = "Color for the ring of dueling marker",
		position = 3
	)
	default Color getRingOfDuelingColor()
	{
		return Color.GREEN;
	}
}
