package scott.harwood.sleep.resetter;
import java.util.List;
import java.util.Random;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.NumberConversions;

public class WarningInitial extends BukkitRunnable {
	private final Random random = new Random();
	private final List<String> earlyNights;
	private final List<String> lateNights;
	private final Plugin plugin;
	private final long resetTime;

	public WarningInitial(Plugin plugin, List<String> earlyNights, List<String> lateNights, long resetTime) {
		this.earlyNights = earlyNights;
		this.lateNights = lateNights;
		this.resetTime = resetTime;
		this.plugin = plugin;
	}

	public void run() {
		List<World> worlds = this.plugin.getServer().getWorlds();
		long time = (worlds.get(0)).getTime();
		if (time < 12520L + this.resetTime && time > 12520L - this.resetTime) {

			for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
				long timeAwake =  player.getStatistic(Statistic.TIME_SINCE_REST);
				int daysAwake = NumberConversions.floor(NumberConversions.toDouble(timeAwake / 24000L) + 1.0D);
				String displayMsg;
				if (daysAwake < 3) {
					displayMsg = PlaceholderAPI.setPlaceholders(player,this.earlyNights.get(daysAwake));
				} else {
					displayMsg = PlaceholderAPI.setPlaceholders(player,lateNights.get(random.nextInt(this.lateNights.size())));
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', displayMsg));
			}
		}

	}
}