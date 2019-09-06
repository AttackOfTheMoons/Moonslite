package net.runelite.client.plugins.easyoption;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;
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
import net.runelite.client.plugins.util.Swapper;
import net.runelite.client.util.Text;

@PluginDescriptor(
	name = "EasyOption",
	description = "Customize menu entries",
	tags = {"easy", "moonlite", "drop", "use"},
	enabledByDefault = false
)

@Slf4j
public class EasyOptionPlugin extends Plugin
{

	private Swapper swapper = new Swapper();
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
		final String target = Text.removeTags(event.getTarget()).toLowerCase();

		swapper.setEntries(client.getMenuEntries());

		if (config.getRemoveExamine() && swapper.getEntries().length > 0)
		{
			for (int i = swapper.getEntries().length - 1; i >= 0; i--)
			{
				if (swapper.getEntries()[i].getOption().equals("Examine"))
				{
					swapper.removeIndex(i);
					i--;
				}
			}
		}

		if (config.getRemoveOptions() && !config.getRemovedOptions().equals(""))
		{
			for (String removed : config.getRemovedOptions().split(","))
			{
				removed = removed.trim().toLowerCase();
				if (option.contains("(") && option.split(" \\(")[0].equals(removed))
				{
					delete(event.getIdentifier());
				}
				else if (option.contains("->"))
				{
					String trimmed = option.split("->")[1].trim().toLowerCase();
					if (trimmed.length() >= removed.length() && trimmed.substring(0, removed.length()).equals(removed))
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

		if (config.getUse() && !config.getNameUse().equals(""))
		{
			for (String item : config.getNameUse().split(","))
			{
				item = item.trim().toLowerCase();
				if (!item.equals("") && target.equals(item))
				{
					swapper.markForSwap("Use", option, target);
				}
			}
		}

		if (config.getDrop() && !config.getNameDrop().equals(""))
		{
			ArrayList<String> dropList = new ArrayList<>(Arrays.asList(config.getNameDrop().split(",")));
			if (config.getUse() && !config.getNameUse().equals(""))
			{
				ArrayList<String> useList = new ArrayList<>(Arrays.asList(config.getNameUse().split(",")));
				dropList.removeAll(useList);
			}
			for (String item : dropList)
			{
				item = item.trim().toLowerCase();
				if (!item.equals("") && target.equals(item))
				{
					swapper.markForSwap("Drop", option, target);
				}
			}
		}

		swapper.startSwap();
		client.setMenuEntries(swapper.getEntries());
	}

	private void delete(int option)
	{
		for (int i = swapper.getEntries().length - 1; i >= 0; i--)
		{
			if (swapper.getEntries()[i].getIdentifier() == option)
			{
				swapper.removeIndex(i);
				i--;
			}
		}
	}

}