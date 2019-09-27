package net.runelite.client.plugins.util;

public enum DuelingRingMode
{
	REMOVE("Remove"),
	DUEL_ARENA("Duel Arena"),
	CASTLE_WARS("Castle Wars"),
	CLAN_WARS("Clan Wars");

	private final String name;

	DuelingRingMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
