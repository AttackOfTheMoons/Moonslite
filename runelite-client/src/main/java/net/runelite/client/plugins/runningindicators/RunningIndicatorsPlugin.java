package net.runelite.client.plugins.runningindicators;

import com.google.inject.Provides;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Running Indicators",
	description = "Indicators for Essence Running",
	tags = {"overlay", "highlight", "moonlite", "thank u, next"},
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

	private boolean wearingRingOfDueling = false;

	private boolean bindingAlert = false;

	private int volume = 35;

	private Clip thankyounext;

	private Map<Actor, Integer> crafterMap = new HashMap<>();

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
		if (GetAudioClip() != null)
		{
			thankyounext = GetAudioClip();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		if (thankyounext != null && thankyounext.isRunning())
		{
			thankyounext.stop();
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		bindingAlert = false;
		if (config.getCraftingTimer())
		{
			for (Map.Entry<Actor, Integer> entry : crafterMap.entrySet())
			{
				Actor key = entry.getKey();
				Integer value = entry.getValue() - 1;
				key.setOverheadText("Crafting: " + value);
				crafterMap.put(key, value);
				if (value <= 0)
				{
					crafterMap.remove(key);
				}
			}
		}
		if (config.getTradeBinding())
		{
			Widget tradeWidget = client.getWidget(WidgetInfo.FIRST_TRADING_WITH_SLOTS);
			if (tradeWidget != null)
			{
				String text = tradeWidget.getText();
				if (text.length() > 24 && (text.charAt(text.length() - 24) == '2' && text.charAt(text.length() - 23) == '5'))
				{
					bindingAlert = true;
				}
			}
		}
		wearingRingOfDueling();
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (config.getCraftingTimer())
		{
			Actor actor = event.getActor();
			if (actor == null)
			{
				return;
			}
			if (actor.getAnimation() == 791)
			{
				crafterMap.put(actor, 5);
				actor.setOverheadText("Crafting: 5");
			}
			if (actor.getAnimation() == -1 && crafterMap.containsKey(actor))
			{
				actor.setOverheadText(null);
				crafterMap.remove(actor);
			}
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getMessage().equals("Sending trade offer..."))
		{
			tradeSent = true;
		}
		else if (event.getMessage().equals("Accepted trade.") || event.getMessage().equals("Other player declined trade.") || event.getMessage().equals("Other player is busy at the moment.") || event.getMessage().equals("Other player doesn't enough inventory space for this trade."))
		{
			if (config.getThankYouNext() && event.getMessage().equals("Accepted trade."))
			{
				if (thankyounext != null)
				{
					if (thankyounext.isRunning())
					{
						thankyounext.stop();
					}
					thankyounext.setFramePosition(0);
					thankyounext.start();
				}
			}
			tradeSent = false;
		}
		else if (event.getMessage().equals("Your Binding necklace has disintegrated.") && config.getBindingBreak())
		{
			overlay.setBindingTimeLeft(60);
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
		if (config.getDisableSpamTrades() && (tradeSent || client.getWidget(WidgetInfo.FIRST_TRADING_WITH_SLOTS) != null))
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
		if (items.length < 13)
		{
			return;
		}
		this.id = items[12].getId();
		tradeSent = false;
	}

	public boolean getSentTrades()
	{
		return tradeSent;
	}

	public boolean getWearingRingOfDueling()
	{
		return wearingRingOfDueling;
	}

	public boolean getBindingAlert()
	{
		return bindingAlert;
	}

	private void wearingRingOfDueling()
	{
		if (config.getRingOfDuelingMarker())
		{
			wearingRingOfDueling = (id == ItemID.RING_OF_DUELING8 || id == ItemID.RING_OF_DUELING7 || id == ItemID.RING_OF_DUELING6 || id == ItemID.RING_OF_DUELING5 || id == ItemID.RING_OF_DUELING4 || id == ItemID.RING_OF_DUELING3 || id == ItemID.RING_OF_DUELING2 || id == ItemID.RING_OF_DUELING1);
		}
		else
		{
			wearingRingOfDueling = true;
		}
	}

	private Clip GetAudioClip()
	{
		final String PATH = "net/runelite/client/plugins/runningindicators/thankyounext.wav";
		final int VOLUME = volume;
		InputStream audioFile = this.getClass().getClassLoader().getResourceAsStream(PATH);
		audioFile = new BufferedInputStream(audioFile);

		try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile))
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
			float gainValue = (((float) VOLUME) * 40f / 100f) - 35f;
			gainControl.setValue(gainValue);

			return audioClip;
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
		{
			System.out.println("Error opening audiostream from " + audioFile + "\n" + e);
			return null;
		}
	}
}
