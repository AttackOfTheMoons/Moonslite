package net.runelite.client.plugins.easyswap;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ClanMember;
import net.runelite.api.ClanMemberManager;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import static net.runelite.api.MenuAction.WALK;
import net.runelite.api.MenuEntry;
import static net.runelite.api.ObjectID.PORTAL_4525;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
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
import org.apache.commons.lang3.StringUtils;

@PluginDescriptor(
	name = "EasySwap",
	description = "Swaps entries / running plugins",
	tags = {"EasySwap", "easy", "moonlite"},
	enabledByDefault = false,
	loadWhenOutdated = true
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

	private ClanMemberManager clanMemberManager;
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
		clanMemberManager = client.getClanMemberManager();
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

		if (target.contains("ardougne cloak") && config.getSwapArdougneCape())
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

		if (config.getSwapPyramidPlunder())
		{
			if (target.equalsIgnoreCase("guardian mummy") && !shiftModifier)
			{
				swapper.markForSwap("Start-minigame", option, target);
			}
		}

		if (config.getSwapConCape())
		{
			if ((target.equalsIgnoreCase("Construct. cape") || target.equalsIgnoreCase("Construct. cape(t)")) && (option.equalsIgnoreCase("remove") || (config.getTeleportInventory() && option.equals("wear"))))
			{
				swapper.markForSwap("Tele to POH", option, target);
			}
		}

		if (config.getSwapCraftCape())
		{
			if (config.cancelTrades() && (target.equals("crafting cape") || target.equals("crafting cape(t)")) && (client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER) != null && client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER).getText().equals("Waiting for other player...")))
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
			else if ((target.equalsIgnoreCase("Crafting cape") || target.equalsIgnoreCase("Crafting cape(t)")) && (option.equalsIgnoreCase("remove") || (config.getTeleportInventory() && option.equals("wear"))))
			{
				swapper.markForSwap("Teleport", option, target);
			}
		}

		if (config.getSwapMythCape())
		{
			if (target.equalsIgnoreCase("Mythical cape") && (option.equals("remove") || (config.getTeleportInventory() && option.equals("wear"))))
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

		if (config.getSwapTalisman())
		{
			if (target.equals("earth talisman") && option.equals("offer"))
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

		if (config.disableMagicImbue() || config.disableUseEarthRune())
		{
			Widget second = client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER);
			if (second != null && !second.isHidden())
			{
				MenuEntry cancel = null;
				MenuEntry imbue = null;
				MenuEntry useEarthRune = null;
				for (MenuEntry x : swapper.getEntries())
				{
					if (x.getOption().equals("Cancel"))
					{
						cancel = x;
					}
					else if (x.getOption().equals("Cast") && x.getTarget().equals("<col=00ff00>Magic Imbue</col>"))
					{
						imbue = x;
					}
					else if (x.getOption().equals("Use") && x.getTarget().contains("Earth rune"))
					{
						useEarthRune = x;
					}
					if ((imbue != null || useEarthRune != null) && cancel != null)
					{
						swapper.setEntries(new MenuEntry[]{cancel});
					}
				}
			}
		}

		if (shiftToggle && config.tradesOnly() && option.equalsIgnoreCase("follow"))
		{
			List<MenuEntry> tradeFix = new ArrayList<>();
			MenuEntry[] menuEntries = swapper.getEntries();
			final ClanMember[] clanMembersArr = clanMemberManager.getMembers();
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
						if (m.getTarget().contains(x.getName()))
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
				if (widgetBankTitleBar == null || widgetBankTitleBar.isHidden())
				{
					if (config.cancelTrades() && (client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER) != null && client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER).getText().equals("Waiting for other player...")))
					{
						MenuEntry cancel = null;
						for (MenuEntry x : swapper.getEntries())
						{
							if (x.getOption().equals("Cancel"))
							{
								cancel = x;
							}
						}
						if (cancel != null)
						{
							swapper.setEntries(new MenuEntry[]{cancel});
						}
					}
					swapper.markForSwap("Empty", option, target);

				}
				else if (!target.contains("withdraw"))
				{
					if (config.getFillOnly())
					{
						MenuEntry fill = null;
						MenuEntry cancel = null;
						for (MenuEntry x : swapper.getEntries())
						{
							if (x.getOption().equals("Cancel"))
							{
								cancel = x;
								cancel.setForceLeftClick(true);
							}
							if (x.getOption().equals("Fill"))
							{
								fill = x;
								fill.setForceLeftClick(true);
							}
						}
						if (fill != null && cancel != null)
						{
							swapper.setEntries(new MenuEntry[]{cancel, fill});//entries go backwards in index
							toolTipManager.clear();
							toolTipManager.addFront(new Tooltip("Fill <col=ff9040>" + StringUtils.capitalize(target)));
						}
						else if (cancel != null)
						{
							swapper.setEntries(new MenuEntry[]{cancel});
						}
					}
					else
					{
						for (int i = 0; i < swapper.getEntries().length; i++)
						{
							if (swapper.getEntries()[i].getOption().contains("Deposit"))
							{
								swapper.removeIndex(i);
								i--;
							}
							else
							{
								swapper.getEntries()[i].setForceLeftClick(true);
							}
						}
						swapper.markForSwap("Fill", option, target);
						String newOption = swapper.getEntries()[swapper.getEntries().length - 1].getOption();
						if (newOption.equals("Fill") || newOption.equals("Empty"))
						{
							toolTipManager.clear();
							toolTipManager.addFront(new Tooltip(swapper.getEntries()[swapper.getEntries().length - 1].getOption() + " <col=ff9040>" + StringUtils.capitalize(target)));
						}
					}
				}
			}
		}

		if (config.edgevilleBankers())
		{
			if (target.equals("bank booth"))
			{
				List<MenuEntry> entries = new ArrayList<>(Arrays.asList(swapper.getEntries()));

				//Bank Option for the most Southern Banker in Edgeville Bank
				MenuEntry SouthBanker = new MenuEntry();
				SouthBanker.setOption("Bank");
				SouthBanker.setTarget("<col=ffff00>Banker");
				SouthBanker.setIdentifier(9033);
				SouthBanker.setType(11);
				SouthBanker.setParam1(0);
				SouthBanker.setParam0(0);

				//Bank option for the banker north of that one ^
				//new MenuEntry("Bank", "<col=ffff>Bank booth", 10355, 4, 55, 51, false);
				//new MenuEntry("Bank", "<col=ffff00>Banker", 9034, 11, 0, 0, false);
				MenuEntry NorthBanker = new MenuEntry();
				NorthBanker.setOption("Bank");
				NorthBanker.setTarget("<col=ffff00>Banker");
				NorthBanker.setIdentifier(9034);
				NorthBanker.setType(11);
				NorthBanker.setParam1(0);
				NorthBanker.setParam0(0);
				if (entries.contains(NorthBanker))
				{
					int last = entries.size() - 1;
					int bankIndex = entries.indexOf(NorthBanker);
					MenuEntry placeholder = entries.get(last);
					entries.set(last, NorthBanker);
					entries.set(bankIndex, placeholder);
				}
				else if (entries.contains(SouthBanker))
				{
					int last = entries.size() - 1;
					int bankIndex = entries.indexOf(SouthBanker);
					MenuEntry placeholder = entries.get(last);
					entries.set(last, SouthBanker);
					entries.set(bankIndex, placeholder);
				}
				swapper.setEntries(entries.toArray(new MenuEntry[entries.size()]));
			}
		}

		if (config.getGamesNecklace())
		{
			if (target.contains("games necklace"))
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
			if (target.contains("ring of dueling"))
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
			if (config.cancelTrades() && (target.contains("amulet of glory") || target.equals("amulet of eternal glory")) && (client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER) != null && client.getWidget(WidgetInfo.SECOND_TRADING_WITH_TITLE_CONTAINER).getText().equals("Waiting for other player...")))
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
			else if (target.contains("amulet of glory") || target.equals("amulet of eternal glory"))
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
			if (target.contains("digsite pendant"))
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
		if (config.getMaxCape())
		{
			if (target.contains("max cape"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getMaxCapeMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getSMaxCapeMode().toString(), option, target);
				}
			}
		}
		if (config.getRingOfWealth())
		{
			if (target.contains("ring of wealth"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getRingOfWealthMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getSRingOfWealthMode().toString(), option, target);
				}
			}
		}
		if (config.getPharaohSceptre())
		{
			if (target.contains("pharaoh's sceptre"))
			{
				if (shiftModifier)
				{
					swapper.markForSwap(config.getPharaohSceptrehMode().toString(), option, target);
				}
				else
				{
					swapper.markForSwap(config.getSPharaohSceptreMode().toString(), option, target);
				}
			}

		}
		swapper.startSwap();
		client.setMenuEntries(swapper.getEntries());
	}

	private boolean isEssencePouch(String target)
	{
		return (target.equals("small pouch") || target.equals("medium pouch") || target.equals("large pouch") || target.equals("giant pouch"));
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