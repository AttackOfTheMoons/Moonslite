package net.runelite.client.plugins.util;

public enum WealthRingMode
{
	REMOVE("Remove"),
	MISCELLANIA("Miscellania"),
	GRAND_EXCHANGE("Grand Exchange"),
	FALADOR("Falador"),
	DONDAKAN("Dondakan");

	private final String name;

	WealthRingMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}