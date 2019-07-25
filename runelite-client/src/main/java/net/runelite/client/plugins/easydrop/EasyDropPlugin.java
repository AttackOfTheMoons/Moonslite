package net.runelite.client.plugins.easydrop;

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
import net.runelite.client.plugins.util.Swapper;
import net.runelite.client.util.Text;

import javax.inject.Inject;


@PluginDescriptor(
	name = "EasyDrop",
	description = "Left click use/drop",
	tags = {"drop", "easy"},
	enabledByDefault = false
)

@Slf4j
public class EasyDropPlugin extends Plugin
{

	private Swapper swapper = new Swapper();
	private MenuEntry[] entries;

	@Inject
	private Client client;

	@Inject
	private EasyDropConfig config;

	@Provides
	EasyDropConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EasyDropConfig.class);
	}

	@Override
	public void startUp()
	{
		log.debug("EasyDrop Started.");
	}

	@Override
	public void shutDown()
	{
		log.debug("EasyDrop Stopped.");
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

		Widget widgetBankTitleBar = client.getWidget(WidgetInfo.BANK_TITLE_BAR);

		swapper.setEntries(client.getMenuEntries());

		if (!(widgetBankTitleBar == null) && !widgetBankTitleBar.isHidden())
		{

			return;
		}

		if (config.getUse())
		{
			for (String item : config.getNameUse().split(","))
			{
				item = item.trim();
				if (!item.equals("") && target.equalsIgnoreCase(item))
				{
					swapper.markForSwap("Use", option, target);
				}
			}
		}

		if (config.getDrop())
		{
			for (String item : config.getNameDrop().split(","))
			{
				item = item.trim();
				if (!item.equals("") && target.equalsIgnoreCase(item))
				{
					swapper.markForSwap("Drop", option, target);
				}
			}
		}

		swapper.startSwap();
		client.setMenuEntries(swapper.getEntries());
	}

}