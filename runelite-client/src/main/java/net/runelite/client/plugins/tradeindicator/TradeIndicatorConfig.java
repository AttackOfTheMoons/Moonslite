package net.runelite.client.plugins.tradeindicator;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tradeindicator")
public interface TradeIndicatorConfig extends Config {

    @ConfigItem(
            keyName = "slotNumber",
            name = "Slots Availible",
            description = "Configures How Many Slots Free Should Cause A Notification",
            position = 1
    )
    default int slotNumber()
    {
        return 25;
    }

}
