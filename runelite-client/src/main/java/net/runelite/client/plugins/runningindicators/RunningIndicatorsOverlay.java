package net.runelite.client.plugins.runningindicators;

import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import javax.inject.Inject;
import java.awt.*;
import net.runelite.client.ui.overlay.OverlayUtil;

public class RunningIndicatorsOverlay extends Overlay
{
	@Inject
	private Client client;

	@Inject
	private RunningIndicatorsPlugin plugin;

	@Inject
	private RunningIndicatorsConfig config;

	@Setter
	private int bindingTimeLeft = 0;


	@Inject
	private RunningIndicatorsOverlay(Client client, RunningIndicatorsPlugin plugin, RunningIndicatorsConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
	}

	public Dimension render(Graphics2D graphics)
	{
		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		for (WidgetItem items : inventory.getWidgetItems())
		{
			if (items.getId() == ItemID.BINDING_NECKLACE && plugin.getBindingAlert())
			{
				Rectangle bounds = items.getCanvasBounds();
				graphics.setColor(config.getBindingColor());
				graphics.draw(bounds);
			}
		}

		if (config.getChatBoxMarker())
		{
			Widget chatBox = client.getWidget(WidgetInfo.CHATBOX);
			if (chatBox != null)
			{
				Rectangle bounds = chatBox.getBounds();
				graphics.setColor(plugin.getSentTrades() ? config.getChatBoxColorSent() : config.getChatBoxColor());
				graphics.setStroke(new BasicStroke(4));
				graphics.draw(bounds);
			}
		}

		Widget bank = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);

		if (bank != null && bank.getChildren() != null)
		{
			for (Widget items : bank.getChildren())
			{
				if (items != null)
				{
					if (items.getItemId() == ItemID.RING_OF_DUELING8 && !plugin.getWearingRingOfDueling())
					{
						Rectangle bounds = items.getBounds();
						graphics.setStroke(new BasicStroke(1));
						graphics.setColor(config.getRingOfDuelingColor());
						graphics.draw(bounds);
					}
				}
			}
		}

		if (bindingTimeLeft > 0)
		{
			graphics.setFont(FontManager.getRunescapeFont());
			final String MESSAGE = "Your Binding Necklace Has Disintegrated.";
			int width = graphics.getFontMetrics().stringWidth(MESSAGE);
			int height = graphics.getFontMetrics().getHeight();
			net.runelite.api.Point point;
			if (client.isResized())
			{
				Dimension dimension = client.getRealDimensions();
				point = new net.runelite.api.Point((int) ((dimension.getWidth() - width) / 2), (int) ((dimension.getHeight() - height)/2.0 + (bindingTimeLeft / 60.0 - 1) * 50.0));
			}
			else
			{
				final Widget viewport = client.getWidget(WidgetInfo.FIXED_VIEWPORT);
				net.runelite.api.Point topLeft = viewport.getCanvasLocation();
				point = new net.runelite.api.Point(topLeft.getX() + (viewport.getWidth() - width) / 2, (int) (topLeft.getY() + (viewport.getHeight() - height) / 2.0 + (bindingTimeLeft / 60.0 - 1) * 50.0));
			}
			OverlayUtil.renderTextLocation(graphics, point, MESSAGE, config.getBindingBreakColor());
		}
		bindingTimeLeft--;


		return null;
	}
}
