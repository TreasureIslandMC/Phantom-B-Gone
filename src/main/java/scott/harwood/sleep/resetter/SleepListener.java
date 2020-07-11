package scott.harwood.sleep.resetter;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SleepListener implements Listener {
	private final Plugin plugin;
	private final Long bedDelay;
	private final String sleepMessage;

	public SleepListener(Plugin plugin, long bedDelay, String sleepMessage) {
		this.plugin = plugin;
		this.bedDelay = bedDelay;
		this.sleepMessage = sleepMessage;
	}

	@EventHandler
	public void playerBedEnterEvent(@NotNull PlayerBedEnterEvent event) {
		final Player player = event.getPlayer();
		player.setStatistic(Statistic.TIME_SINCE_REST, 0);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player,sleepMessage)));
			}
		}.runTaskLater(plugin,bedDelay);
	}
}
