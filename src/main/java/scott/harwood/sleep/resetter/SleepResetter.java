package scott.harwood.sleep.resetter;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SleepResetter extends JavaPlugin {
	private final FileConfiguration config = this.getConfig();

	@Override
	public void onEnable() {
		saveDefaultConfig();

		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
			new ResetterPlaceholderExpansion(this).register();
		}

		getCommand("sleep-reload").setExecutor(new ReloadCommand());

		final String sleepMessage = this.config.getString("sleep-message", "The phantoms are gone.");
		this.getServer().getPluginManager().registerEvents(new SleepListener(this, this.config.getLong("bed-delay", 20L), sleepMessage), this);

		List<String> nights = config.getStringList("warning-messages");
		List<String> lateNights = this.config.getStringList("late-nights");
		long resetTime = this.config.getLong("reset-time");
		WarningInitial warningInitial = new WarningInitial(this,nights,lateNights,resetTime);
		warningInitial.runTaskTimer(this,1L,resetTime);
	}

	/**
	 * Simple config reload command
	 */
	public class ReloadCommand implements CommandExecutor {
		@Override
		public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
			reloadConfig();
			getLogger().info("Config reloaded by "+ sender.getName());
			return true;
		}
	}
}
