package net.runelite.client.plugins.util;

public enum DigsiteMode
{
	REMOVE("Remove"),
	DIGSITE("Digsite"),
	FOSSIL_ISLAND("Fossil Island"),
	LITHKREN_DUNGEON("Lithkren Dungeon");

	private final String name;

	DigsiteMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}