package net.golem.player;

import net.golem.network.NetworkSession;

import java.util.UUID;

public class Player {

	private String username, displayName;

	private NetworkSession session;

	private UUID uuid;

	private boolean connected, loggedIn = false;

	public Player(String username, NetworkSession session, UUID uuid) {
		this.username = username;
		this.session = session;
		this.uuid = uuid;
	}

	public String getName() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public NetworkSession getSession() {
		return session;
	}

	public UUID getUUID() {
		return uuid;
	}

	public boolean isConnected() {
		return connected;
	}

	protected void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	protected void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isOnline() {
		return this.isConnected() && this.isLoggedIn();
	}
}