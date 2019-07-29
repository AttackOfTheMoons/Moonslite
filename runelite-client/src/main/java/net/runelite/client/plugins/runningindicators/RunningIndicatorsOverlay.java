package net.runelite.client.plugins.runningindicators;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import javax.inject.Inject;
import java.awt.*;

public class RunningIndicatorsOverlay extends Overlay
{

	private final Client client;

	private boolean bindingAlert;

	boolean wearingRingOfDueling;

	@Inject
	RunningIndicatorsConfig config;

	@Inject
	private RunningIndicatorsOverlay(Client client)
	{
		this.client = client;
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
		bindingAlert = false;
		wearingRingOfDueling = false;
	}

	public Dimension render(Graphics2D graphics)
	{
		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		for (WidgetItem items : inventory.getWidgetItems())
		{
			if (items.getId() == ItemID.BINDING_NECKLACE && bindingAlert)
			{
				Rectangle bounds = items.getCanvasBounds();
				graphics.setColor(config.getBindingColor());
				graphics.draw(bounds);
			}
		}

		Widget bank = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);

		if (bank != null)
		{
			for (Widget items : bank.getChildren())
			{
				if (items.getItemId() == ItemID.RING_OF_DUELING8 && !wearingRingOfDueling)
				{
					Rectangle bounds = items.getBounds();
					graphics.setColor(config.getRingOfDuelingColor());
					graphics.draw(bounds);
				}
			}
		}
		return null;
	}

	public void setBindingAlert(boolean flag)
	{
		bindingAlert = flag;
	}

}
