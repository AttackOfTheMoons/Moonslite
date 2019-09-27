package net.runelite.client.plugins.runningindicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

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

	@ConfigItem(
		keyName = "chatBoxMarker",
		name = "Chat Box Marker",
		description = "Highlights the chatbox if you haven't sent a trade",
		position = 4
	)
	default boolean getChatBoxMarker()
	{
		return false;
	}

	@ConfigItem(
		keyName = "chatBoxColorSent",
		name = "Sent Trade Color",
		description = "Color for the chat box marker when trade has been sent",
		position = 5
	)
	default Color getChatBoxColorSent()
	{
		return Color.GREEN;
	}

	@ConfigItem(
		keyName = "chatBoxColor",
		name = "Trade Not Sent Color",
		description = "This will be the default color",
		position = 6
	)
	default Color getChatBoxColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		keyName = "disableSpamTrades",
		name = "Disable Spam Trades",
		description = "Stops multiple trades from being sent",
		position = 7
	)
	default boolean getDisableSpamTrades()
	{
		return false;
	}

	@ConfigItem(
		keyName = "bindingBreak",
		name = "Notify On Binding Necklace Broken",
		description = "Overlay will display when binding necklace breaks",
		position = 8
	)
	default boolean getBindingBreak()
	{
		return false;
	}

	@ConfigItem(
		keyName = "bindingBreakColor",
		name = "Text Color",
		description = "",
		position = 9
	)
	default Color getBindingBreakColor()
	{
		return Color.ORANGE;
	}
}
