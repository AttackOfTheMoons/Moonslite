package net.runelite.client.plugins.util;

public enum GamesNecklaceMode
{
	REMOVE("Remove"),
	BURTHORPE("Burthorpe"),
	BARBARIAN_OUTPOST("Barbarian Outpost"),
	CORPOREAL_BEAST("Corporeal Beast"),
	TEARS_OF_GUTHIX("Tears of Guthix");

	private final String name;

	GamesNecklaceMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
