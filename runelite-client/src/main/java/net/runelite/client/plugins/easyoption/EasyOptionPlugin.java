package net.runelite.client.plugins.easyoption;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;
import org.apache.commons.lang3.ArrayUtils;

import javax.inject.Inject;

@PluginDescriptor(
	name = "EasyOption",
	description = "EasyOption.",
	tags = {"Easy"},
	enabledByDefault = false
)

@Slf4j
public class EasyOptionPlugin extends Plugin
{

	private MenuEntry[] entries;

	@Inject
	private Client client;

	@Inject
	private EasyOptionConfig config;

	@Provides
	EasyOptionConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EasyOptionConfig.class);
	}

	@Override
	public void startUp()
	{
		log.debug("EasyOption Started.");
	}

	@Override
	public void shutDown()
	{
		log.debug("EasyOption Stopped.");
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{

		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		Widget loginScreenOne = client.getWidget(WidgetInfo.LOGIN_CLICK_TO_PLAY_SCREEN);
		Widget loginScreenTwo = client.getWidget(WidgetInfo.LOGIN_CLICK_TO_PLAY_SCREEN_MESSAGE_OF_THE_DAY);

		if (loginScreenOne != null || loginScreenTwo != null)
		{
			return;
		}

		final String option = Text.removeTags(event.getOption()).toLowerCase();

		entries = client.getMenuEntries();

		if (config.getRemoveExamine() && entries.length >= 0)
		{
			for (int i = entries.length - 1; i >= 0; i--)
			{
				if (entries[i].getOption().equals("Examine"))
				{
					entries = ArrayUtils.remove(entries, i);
					i--;
				}
			}
			client.setMenuEntries(entries);
		}

		if (config.getRemoveOptions() && !config.getRemovedOptions().equals(""))
		{
			for (String removed : config.getRemovedOptions().split(","))
			{
				removed = removed.trim();
				if (option.contains("(") && option.split(" \\(")[0].equalsIgnoreCase(removed))
				{
					delete(event.getIdentifier());
				}
				else if (option.contains("->"))
				{
					String trimmed = option.split("->")[1].trim();
					if (trimmed.length() >= removed.length() && trimmed.substring(0, removed.length()).equalsIgnoreCase(removed))
					{
						delete(event.getIdentifier());
						break;
					}
				}
				else if (option.length() >= removed.length() && option.substring(0, removed.length()).equalsIgnoreCase(removed))
				{
					delete(event.getIdentifier());
					break;
				}
			}
		}

	}

	private void delete(int option)
	{
		for (int i = entries.length - 1; i >= 0; i--)
		{
			if (entries[i].getIdentifier() == option)
			{
				entries = ArrayUtils.remove(entries, i);
				i--;
			}
		}
		client.setMenuEntries(entries);
	}

}