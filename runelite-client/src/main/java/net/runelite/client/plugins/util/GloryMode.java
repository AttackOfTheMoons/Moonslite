package net.runelite.client.plugins.util;

public enum GloryMode
{
	REMOVE("Remove"),
	EDGEVILLE("Edgeville"),
	KARAMJA("Karamja"),
	DRAYNOR_VILLAGE("Draynor Village"),
	AL_KHARID("Al Kharid");

	private final String name;

	GloryMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
