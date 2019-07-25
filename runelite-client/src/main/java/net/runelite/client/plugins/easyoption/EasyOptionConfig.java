package net.runelite.client.plugins.easyoption;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("easyscape")
public interface EasyOptionConfig extends Config
{

	@ConfigItem(
		keyName = "removeExamine",
		name = "Remove Examine",
		description = "Removes Examine from the list of options.",
		position = 0
	)

	default boolean getRemoveExamine()
	{
		return true;
	}

	@ConfigItem(
		keyName = "removeOptions",
		name = "Remove Options",
		description = "Removes interaction with the listed options.",
		position = 1
	)

	default boolean getRemoveOptions()
	{
		return true;
	}


	@ConfigItem(
		keyName = "removedOption",
		name = "Options",
		description = "Option listed here will have all interaction be removed.",
		position = 2
	)

	default String getRemovedOptions()
	{
		return "";
	}
}
