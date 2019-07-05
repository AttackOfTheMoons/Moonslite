package net.runelite.client.plugins.tradeindicator;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class TradeIndicatorOverlay extends Overlay {


    private final Client client;
    private final TradeIndicatorConfig config;

    @Inject
    private TradeIndicatorOverlay (Client client, TradeIndicatorConfig config) {
        this.client = client;
        this.config = config;
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
    }

    public Dimension render(Graphics2D graphics) {
        graphics.setColor(Color.GRAY);
        if (TradeIndicatorPlugin.slotsAlert) {
            graphics.setColor(Color.GREEN);
        }
        graphics.fillRect(730,58,20,20);
        return null;
    }


}
