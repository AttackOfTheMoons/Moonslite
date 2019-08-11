package net.runelite.client.plugins.runningindicators;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.Client;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import net.runelite.api.ItemID;

@PluginDescriptor(
	name = "Running Indicators",
	description = "Indicators for Essence Running",
	tags = {"overlay", "highlight", "moonlite"},
	loadWhenOutdated = true,
	enabledByDefault = false
)

public class RunningIndicatorsPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RunningIndicatorsOverlay overlay;

	@Inject
	private Client client;

	private int id = -1;

	private boolean tradeSent = false;

	@Inject
	RunningIndicatorsConfig config;

	@Provides
	RunningIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return (RunningIndicatorsConfig) configManager.getConfig(RunningIndicatorsConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (config.getTradeBinding())
		{
			Widget tradeWidget = client.getWidget(WidgetInfo.FIRST_TRADING_WITH_SLOTS);
			overlay.setBindingAlert(false);
			if (tradeWidget != null)
			{
				String text = tradeWidget.getText();
				if (text.length() > 24 && (text.charAt(text.length() - 24) == '2' && text.charAt(text.length() - 23) == '5'))
				{
					overlay.setBindingAlert(true);
				}
			}
		}
		if (config.getRingOfDuelingMarker())
		{
			overlay.wearingRingOfDueling = wearingRingOfDueling();
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (config.getDisableSpamTrades())
		{
			if (event.getMessage().equals("Sending trade offer..."))
			{
				tradeSent = true;
			}
			else if (event.getMessage().equals("Accepted trade.") || event.getMessage().equals("Other player declined trade."))
			{
				tradeSent = false;
			}
		}
	}

	@Subscribe
	public void onWidgetHiddenChanged(WidgetHiddenChanged event)
	{
		if (event.getWidget().getId() == 21954591)
		{
			tradeSent = false;
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		//using numbers instead of widgetid definition because its not working for some reason
		if (config.getDisableSpamTrades() && (tradeSent || client.getWidget(335, 9) != null))
		{
			MenuEntry[] entries = client.getMenuEntries();
			List<MenuEntry> nonTrades = new ArrayList<>();
			for (MenuEntry entry : entries)
			{
				if (!entry.getOption().equals("Accept trade"))
				{
					nonTrades.add(entry);
				}
			}
			client.setMenuEntries(nonTrades.toArray(new MenuEntry[0]));
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		Item[] items = event.getItemContainer().getItems();
		if (event.getItemContainer() != client.getItemContainer(InventoryID.EQUIPMENT))
		{
			return;
		}
		this.id = items[12].getId();
	}


	public boolean wearingRingOfDueling()
	{
		return (id == ItemID.RING_OF_DUELING8 || id == ItemID.RING_OF_DUELING7 || id == ItemID.RING_OF_DUELING6 || id == ItemID.RING_OF_DUELING5 || id == ItemID.RING_OF_DUELING4 || id == ItemID.RING_OF_DUELING3 || id == ItemID.RING_OF_DUELING2 || id == ItemID.RING_OF_DUELING1);
	}

}
