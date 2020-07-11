package scott.harwood.sleep.resetter;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

public class ResetterPlaceholderExpansion extends PlaceholderExpansion {
	private final SleepResetter plugin;

	public ResetterPlaceholderExpansion(final SleepResetter plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getIdentifier() {
		return "sleep";
	}

	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(final Player player, final String identifier) {
		if(player == null)
			return null;

		if("days".equalsIgnoreCase(identifier))
			return String.valueOf(convertToDays(player.getStatistic(Statistic.TIME_SINCE_REST)));

		return null;
	}

	private int convertToDays(long timeSinceRest){
		return NumberConversions.floor(NumberConversions.toDouble(timeSinceRest / 24000L) + 1.0D);
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public boolean canRegister() {
		return true;
	}
}
