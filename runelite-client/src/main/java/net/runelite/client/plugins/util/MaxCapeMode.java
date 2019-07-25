package net.runelite.client.plugins.util;

public enum MaxCapeMode
{
	REMOVE("REMOVE"),
	WARRIORS_GUILD("Warriors' Guild"),
	FISHING_TELEPORTS("Fishing Teleports"),
	CRAFTING_GUILD("Crafting Guild"),
	TELE_TO_POH("Tele to POH"),
	POH_PORTALS("POH Portals"),
	OTHER_TELEPORTS("Other Teleports"),
	SPELLBOOK("Spellbook");

	private final String name;

	MaxCapeMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
