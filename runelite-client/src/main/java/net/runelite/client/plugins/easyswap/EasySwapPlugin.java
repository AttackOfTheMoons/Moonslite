package net.runelite.client.plugins.easyswap;

import com.google.inject.Provides;
import javax.swing.ToolTipManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetMenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.util.Swapper;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.Text;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.runelite.api.MenuAction.WALK;
import static net.runelite.api.ObjectID.PORTAL_4525;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

@PluginDescriptor(
	name = "EasySwap",
	description = "Swaps entries / running plugins",
	tags = {"EasySwap", "easy"},
	enabledByDefault = false
)

@Slf4j
public class EasySwapPlugin extends Plugin
{

	private static final int PURO_PURO_REGION_ID = 10307;

	private Swapper swapper = new Swapper();
	private boolean inHouse = false;
	@Inject
	private ShiftClickInputListener inputListener;
	@Inject
	private KeyManager keyManager;
	@Setter
	private boolean shiftModifier = false;
	@Setter
	private boolean shiftToggle = false;
	@Inject
	private Client client;
	@Inject
	private TooltipManager toolTipManager;

	@Inject
	private EasySwapConfig config;

	@Provides
	EasySwapConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EasySwapConfig.class);
	}

	@Override
	public void startUp()
	{
		this.keyManager.registerKeyListener(this.inputListener);
		log.debug("EasySwap Started.");
	}

	@Override
	public void shutDown()
	{
		log.debug("EasySwap Stopped.");
		this.keyManager.unregisterKeyListener(this.inputListener);
	}

	@Subscribe
	public void onFocusChanged(FocusChanged event)
	{
		if (!event.isFocused())
		{
			this.shiftModifier = false;
			this.shiftToggle = false;
		}

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

		if (config.getSwapPuro() && isPuroPuro())
		{
			if (event.getType() == WALK.getId())
			{
				swapper.deprioritizeWalk();
			}
			else if (option.equalsIgnoreCase("examine"))
			{
				swapper.markForSwap("push-through", option, target);
			}
			else if (option.equalsIgnoreCase("use"))
			{
				swapper.markForSwap("escape", option, target);
			}
		}

		if (config.getSwapSmithing())
		{
			if (option.equalsIgnoreCase("Smith 1"))
			{
				swapper.markForSwap("Smith All", option, target);
			}
			else if (option.equalsIgnoreCase("Smith 1 Set"))
			{
				swapper.markForSwap("Smith All Sets", option, target);
			}
		}

		if (config.getSwapTanning() && option.equalsIgnoreCase("Tan 1"))
		{
			swapper.markForSwap("Tan All", option, target);
		}

		if (config.getSwapCrafting())
		{
			switch (option)
			{
				case "Make-1":
					swapper.markForSwap("Make-All", option, target);
					break;
				case "Craft 1":
					swapper.markForSwap("Craft All", option, target);
					break;
				default:
					break;
			}
		}

		if (config.getSwapSawmill() && target.equalsIgnoreCase("Sawmill operator"))
		{
			swapper.markForSwap("Buy-plank", option, target);
		}

		if (config.getSwapSawmillPlanks() && option.equalsIgnoreCase("Buy 1"))
		{
			swapper.markForSwap("Buy All", option, target);
		}

		if (option.equalsIgnoreCase("Clear-All") && target.equalsIgnoreCase("bank Filler"))
		{
			swapper.markForSwap("Clear", option, target);
		}

		if (target.toLowerCase().contains("ardougne cloak") && config.getSwapArdougneCape())
		{
			swapper.markForSwap("Kandarin Monastery", option, target);
			swapper.markForSwap("Monastery Teleport", option, target);
		}

		if (config.getSwapCake())
		{
			if (target.equalsIgnoreCase("Dwarven rock cake") && option.equalsIgnoreCase("Eat"))
			{
				swapper.markForSwap("Guzzle", option, target);
			}
		}

		if (config.getSwapConCape())
		{
			if ((target.equalsIgnoreCase("Construct. cape") || target.equalsIgnoreCase("Construct. cape(t)")) && option.equalsIgnoreCase("Remove"))
			{
				swapper.markForSwap("Tele to POH", option, target);
			}
		}

		if (config.getSwapCraftCape())
		{
			if (config.cancelTrades() && (client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER) != null && client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER).getText().equals("Waiting for other player...")))
			{
				MenuEntry[] entries = swapper.getEntries();
				for (MenuEntry m : entries)
				{
					if (m.getTarget().equals(""))
					{
						swapper.setEntries(new MenuEntry[]{m});
					}
				}
			}
			else if ((target.equalsIgnoreCase("Crafting cape") || target.equalsIgnoreCase("Crafting cape(t)")) && option.equalsIgnoreCase("remove"))
			{
				swapper.markForSwap("Teleport", option, target);
			}
		}

		if (config.getSwapMythCape())
		{
			if (target.equalsIgnoreCase("Mythical Cape") && option.equals("Remove"))
			{
				swapper.markForSwap("Teleport", option, target);
			}
		}

		if (config.getSwapNPC())
		{
			if (target.equalsIgnoreCase("npc contact"))
			{
				swapper.markForSwap("dark mage", option, target);
			}
		}

		if (config.getSwapEss())
		{
			if (target.equalsIgnoreCase("pure essence") && option.equalsIgnoreCase("Offer"))
			{
				swapper.markForSwap("Offer-All", option, target);
			}
		}

		if (config.disableCraftAltar())
		{
			if (target.equalsIgnoreCase("Altar"))
			{
				swapper.setEntries(new MenuEntry[]{});
			}
		}

		if (shiftToggle && config.tradesOnly() && option.equalsIgnoreCase("follow"))
		{
			List<MenuEntry> tradeFix = new ArrayList<>();
			MenuEntry[] menuEntries = swapper.getEntries();
			final ClanMember[] clanMembersArr = client.getClanMembers();
			if (clanMembersArr == null)
			{
				return;
			}
			if (clanMembersArr.length == 1)
			{
				for (MenuEntry m : menuEntries)
				{
					if (m.getOption().contains("Trade"))
					{
						tradeFix.add(m);
					}
				}
			}
			for (MenuEntry m : menuEntries)
			{
				if (m.getOption().contains("Trade") && m.getTarget() != null && m.getTarget() != "")
				{
					for (ClanMember x : clanMembersArr)
					{
						if (m.getTarget().contains(x.getUsername()))
						{
							tradeFix.add(m);
						}
					}
				}
			}
			if (tradeFix.size() == 0)
			{
				return;
			}
			swapper.setEntries(tradeFix.toArray(new MenuEntry[tradeFix.size()]));
		}


		if (config.getSwapEssencePouch())
		{
			if (isEssencePouch(target))
			{
				Widget widgetBankTitleBar = client.getWidget(WidgetInfo.BANK_TITLE_BAR);
				MenuEntry[] entries = swapper.getEntries();
				List<MenuEntry> pouchfix = new ArrayList<>();
				for (MenuEntry m : entries)
				{
					if (!m.getOption().contains("Deposit"))
					{
						pouchfix.add(m);
					}
				}
				MenuEntry[] pouchfixArr = pouchfix.toArray(new MenuEntry[0]);

				if ((widgetBankTitleBar == null || widgetBankTitleBar.getText().equals("")))
				{
					if (config.getSwapEssOutsideBank())
					{
						swapper.markForSwap("Empty", option, target);
					}

				}
				else
				{
					if (pouchfixArr != null)
					{
						for (MenuEntry m : pouchfixArr)
						{
							m.setForceLeftClick(true);
						}
					}
					swapper.setEntries(pouchfixArr);
					toolTipManager.clear();
					toolTipManager.addFront(new Tooltip(pouchfixArr[pouchfixArr.length - 1].getOption() + " <col=ff9040>" + StringUtils.capitalize(target)));
					//swapper.markForSwap("Fill", option, target);

				}
			}
		}

		if (config.getGamesNecklace())
		{
			if (target.toLowerCase().contains("games necklace"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getSGamesNecklaceMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getGamesNecklaceMode().toString(), option, target);
				}

			}
		}

		if (config.getDuelingRing())
		{
			if (target.toLowerCase().contains("ring of dueling"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getSDuelingRingMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getDuelingRingMode().toString(), option, target);
				}
			}
		}

		if (config.getGlory())
		{
			if (config.cancelTrades() && (client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER) != null && client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER).getText().equals("Waiting for other player...")))
			{
				MenuEntry[] entries = swapper.getEntries();
				for (MenuEntry m : entries)
				{
					if (m.getTarget().equals(""))
					{
						swapper.setEntries(new MenuEntry[]{m});
					}
				}
			}
			else if (target.toLowerCase().contains("amulet of glory") || target.toLowerCase().contains("amulet of eternal glory"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getSGloryMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getGloryMode().toString(), option, target);
				}
			}
		}
		if (config.getDigsite())
		{
			if (target.toLowerCase().contains("digsite pendant"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getSDigsiteMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getDigsiteMode().toString(), option, target);
				}
			}
		}

		swapper.startSwap();
		client.setMenuEntries(swapper.getEntries());
	}

	private boolean isEssencePouch(String target)
	{
		return (target.equalsIgnoreCase("Small Pouch") || target.equalsIgnoreCase("Medium Pouch") || target.equalsIgnoreCase("Large Pouch") || target.equalsIgnoreCase("Giant Pouch"));
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		final GameObject gameObject = event.getGameObject();
		if (PORTAL_4525 == gameObject.getId())
		{
			this.inHouse = true;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			this.inHouse = false;
		}
	}

	public boolean getShiftToggle()
	{
		return this.shiftToggle;
	}

	private boolean isHouse()
	{
		return this.inHouse;
	}

	private boolean isPuroPuro()
	{
		Player player = client.getLocalPlayer();

		if (player == null)
		{
			return false;
		}
		else
		{
			WorldPoint location = player.getWorldLocation();
			return location.getRegionID() == PURO_PURO_REGION_ID;
		}
	}

}