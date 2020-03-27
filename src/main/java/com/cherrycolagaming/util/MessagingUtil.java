package com.cherrycolagaming.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Rory on 6/22/2017.
 */
public class MessagingUtil {
	
	private final JavaPlugin plugin;
	private       String     prefix = "", finalPrefixFormatting = "", finalColor = "", finalFormat = "", firstColor = "";
	
	public MessagingUtil(JavaPlugin plugin) {
		this.plugin = plugin;
		this.reload();
	}
	
	public static String format(String arg) {
		return StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', arg));
	}
	
	public void reload() {
		this.updatePrefix();
		this.updatePrefixFormatting();
	}
	
	public void updatePrefix() {
		this.prefix = this.placeholders(this.plugin.getConfig().getString("prefix"));
		this.updatePrefixFormatting();
	}
	
	private void updatePrefixFormatting() {
		this.finalColor = "";
		this.finalFormat = "";
		this.firstColor = "";
		if (this.prefix.length() > 1) {
			for (int index = this.prefix.length(); index > 1; index--) {
				String bit = this.prefix.substring(index - 2, index);
				if (bit.startsWith("§")) {
					int chNum = (int) bit.toLowerCase().charAt(1);
					if ((97 <= chNum && chNum <= 102) || (48 <= chNum && chNum <= 57) || chNum == 114) {
						if (finalColor.equals("")) {
							finalColor = bit;
						}
						firstColor = bit;
					}
					if (107 <= chNum && chNum <= 112) {
						if (finalFormat.equals("")) {
							finalFormat += bit;
						}
					}
					
				}
			}
		}
		this.finalPrefixFormatting = this.finalColor + this.finalFormat;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getPrefixFormatting() {
		return this.finalPrefixFormatting;
	}
	
	public String getFinalColor() {
		return this.finalColor;
	}
	
	public String getFinalFormat() {
		return this.finalFormat;
	}
	
	public String getFirstColor() {
		return firstColor;
	}
	
	public void sendMessage(CommandSender sender, String msg) {
		sender.sendMessage(this.placeholders(msg));
	}
	
	public void sendMessageAtPath(CommandSender sender, String path) {
		sender.sendMessage(this.placeholders(this.plugin.getConfig().getString(path)));
	}
	
	public void broadcastMessage(String msg) {
		Bukkit.broadcastMessage(this.placeholders(msg));
	}
	
	public void broadcastMessageAtPath(String path) {
		Bukkit.broadcastMessage(this.placeholders(this.plugin.getConfig().getString(path)));
	}
	
	public String placeholders(String arg) {
		return StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', arg.replace("{PREFIX}", this.prefix)));
	}
	
	public String getProgressBar(double progress, double maxProgress, int progressBarCount, String completedColor, String missingColor, String progressBarChar) {
		int completedProgress = (int) Math.floor((progress / maxProgress) * progressBarCount);
		completedProgress = completedProgress > progressBarCount ? progressBarCount : completedProgress;
		int uncompletedProgress = progressBarCount - completedProgress;
		
		String progressBar = completedColor;
		for (int i = 0; i < completedProgress; i++) {
			progressBar += progressBarChar;
		}
		
		if (uncompletedProgress > 0) {
			progressBar += missingColor;
			for (int i = 0; i < uncompletedProgress; i++) {
				progressBar += progressBarChar;
			}
		}
		return MessagingUtil.format(progressBar);
	}
}
