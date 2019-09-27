/*
 * Copyright (c) 2018, DennisDeV <https://github.com/DevDennis>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.antidrag;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.FocusChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Anti Drag",
	description = "Prevent dragging an item for a specified delay",
	tags = {"antidrag", "delay", "inventory", "items", "moonlite"},
	enabledByDefault = false,
	loadWhenOutdated = true
)
public class AntiDragPlugin extends Plugin implements KeyListener
{
	private static final int DEFAULT_DELAY = 5;

	@Inject
	private Client client;

	@Inject
	private AntiDragConfig config;

	@Inject
	private KeyManager keyManager;

	@Provides
	AntiDragConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AntiDragConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(this);
		if (config.alwaysOn())
		{
			client.setInventoryDragDelay(config.dragDelay());
		}
		else
		{
			client.setInventoryDragDelay(DEFAULT_DELAY);
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		client.setInventoryDragDelay(DEFAULT_DELAY);
		keyManager.unregisterKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SHIFT || config.alwaysOn())
		{
			client.setInventoryDragDelay(config.dragDelay());
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if ((e.getKeyCode() == KeyEvent.VK_SHIFT) && !config.alwaysOn())
		{
			client.setInventoryDragDelay(DEFAULT_DELAY);
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged focusChanged)
	{
		if ((!focusChanged.isFocused()) && !config.alwaysOn())
		{
			client.setInventoryDragDelay(DEFAULT_DELAY);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (config.alwaysOn())
		{
			client.setInventoryDragDelay(config.dragDelay());
		}
		else
		{
			client.setInventoryDragDelay(DEFAULT_DELAY);
		}
	}
}
