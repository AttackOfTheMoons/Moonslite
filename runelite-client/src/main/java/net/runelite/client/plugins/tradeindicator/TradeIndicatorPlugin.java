package net.runelite.client.plugins.tradeindicator;

import net.runelite.api.Player;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import com.google.inject.Provides;


import javax.inject.Inject;



import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.Client;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;



@PluginDescriptor(
        name = "Trade Indicator",
        description = "Indicates trading player's inventory space",
        tags = {"overlay", "highlight"},
        loadWhenOutdated = true,
        enabledByDefault = false
)

public class TradeIndicatorPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private TradeIndicatorConfig config;

    @Inject
    private TradeIndicatorOverlay overlay;

    @Inject
    private Client client;

    @Provides
    TradeIndicatorConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(TradeIndicatorConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
    }

    public static boolean slotsAlert = false;

    @Subscribe
    public void onGameTick(GameTick tick) {
        final Player LOCAL = client.getLocalPlayer();
        Widget tradeWidget = client.getWidget(WidgetInfo.FIRST_TRADING_WITH_SLOTS);
        overlay.setAlert(false);
        if (tradeWidget!=null) {
            String text = tradeWidget.getText();
            if (text.length() > 24 && (text.charAt(text.length() - 24) == '2' && text.charAt(text.length() - 23) == '5')) {
                overlay.setAlert(true);
            }
        }


        /* failed first attempt

        if ((tradeWidget != null && !tradeWidget.getText().equals("")) && (LOCAL != null && LOCAL.getName().isEmpty())) {
           String tradeString = tradeWidget.getText().split(LOCAL.getName() + " has ")[1].split(" ")[0];
           int slots = tradeString.equals("no") ? 0 : Integer.parseInt(tradeString);
           if (slots == config.slotNumber()) {
                slotsAlert = true;
           } else {
                slotsAlert = false;
           }

        } else {
            slotsAlert = false;
        }*/
    }

}
