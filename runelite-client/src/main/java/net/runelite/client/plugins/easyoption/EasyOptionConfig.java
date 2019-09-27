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

	@ConfigItem(
		keyName = "Use",
		name = "Left Click Use",
		description = "Items will have their left click option set to use",
		position = 3
	)
	default boolean getUse()
	{
		return false;
	}

	@ConfigItem(
		keyName = "NameUse",
		name = "Items",
		description = "",
		position = 4
	)
	default String getNameUse()
	{
		return "";
	}

	@ConfigItem(
		keyName = "Drop",
		name = "Left Click Drop",
		description = "Items will have their left click option set to drop",
		position = 5
	)
	default boolean getDrop()
	{
		return false;
	}

	@ConfigItem(
		keyName = "NameDrop",
		name = "Items",
		description = "If an item appears on both lists, it will default to use",
		position = 6
	)
	default String getNameDrop()
	{
		return "";
	}
}
