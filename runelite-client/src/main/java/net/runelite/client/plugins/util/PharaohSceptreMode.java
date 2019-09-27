package net.runelite.client.plugins.util;

public enum PharaohSceptreMode
{
	REMOVE("Remove"),
	JALSAVRAH("Jalsavrah"),
	JALEUSTROPHOS("Jaleustrophos"),
	JALDRAOCHT("Jaldraocht");

	private final String name;

	PharaohSceptreMode(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
