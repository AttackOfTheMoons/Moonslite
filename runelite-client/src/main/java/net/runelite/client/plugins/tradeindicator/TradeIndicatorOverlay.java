package net.runelite.client.plugins.tradeindicator;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import javax.inject.Inject;
import java.awt.*;

public class TradeIndicatorOverlay extends Overlay
{


	private final Client client;

	private boolean alert;

	@Inject
	private TradeIndicatorOverlay(Client client)
	{
		this.client = client;
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
		//
	}

	public Dimension render(Graphics2D graphics)
	{
		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		for (WidgetItem items : inventory.getWidgetItems())
		{
			if (items.getId() == 5521 && alert)
			{
				Rectangle bounds = items.getCanvasBounds();
				graphics.setColor(Color.GREEN);
				graphics.draw(bounds);
			}
		}


		return null;
	}

	public void setAlert(boolean flag)
	{
		alert = flag;
	}


}
