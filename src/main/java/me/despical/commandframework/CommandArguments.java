/*
 * Copyright (c) 2021 - Despical
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package me.despical.commandframework;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A utility class to use command arguments without external
 * Bukkit parameters and includes some useful methods to improve
 * user's code.
 *
 * @author Despical
 * @since 1.0.0
 */
public class CommandArguments {

	private final CommandSender commandSender;
	private final Command command;
	private final String label, arguments[];

	public CommandArguments(CommandSender commandSender, Command command, String label, String... arguments) {
		this.commandSender = commandSender;
		this.command = command;
		this.label = label;
		this.arguments = arguments;
	}

	/**
	 * Do not try to cast objects except subclasses of {@link CommandSender}
	 * otherwise {@link ClassCastException} will occur. Also casting for {@link Player}
	 * or {@link org.bukkit.command.ConsoleCommandSender} isn't needed.
	 *
	 * @return sender of command as Player or CommandSender
	 */
	@SuppressWarnings("unchecked")
	@NotNull
	public <T extends CommandSender> T getSender() {
		return (T) commandSender;
	}

	/**
	 * @return base command.
	 */
	@NotNull
	public Command getCommand() {
		return command;
	}

	/**
	 * @return label of the command.
	 */
	@NotNull
	public String getLabel() {
		return label;
	}

	/**
	 * @return arguments of the command.
	 */
	@NotNull
	public String[] getArguments() {
		return arguments;
	}

	// SOME GETTER METHODS FOR COMMON PRIMITIVE TYPES //

	/**
	 * @param i index
	 * @return indexed element or null if index out of bounds
	 */
	@NotNull
	public Optional<String> getArgument(int i) {
		return arguments.length > i && i >= 0 ? Optional.of(arguments[i])
			: Optional.empty();
	}

	/**
	 * @param i index
	 * @return Integer if indexed element is primitive type of int
	 * or empty if element is null.
	 */
	@NotNull
	public Optional<Integer> getArgumentAsInt(int i) {
		Optional<String> optional = this.getArgument(i);
		if (!optional.isPresent())
			return Optional.empty();

		String arg = optional.get();

		try {
			int parsed = Integer.parseInt(arg);
			return Optional.of(parsed);
		} catch (NumberFormatException ignored) {
			return Optional.empty();
		}
	}

	/**
	 * @param i index
	 * @return Double if indexed element is primitive type of double
	 * or empty if element is null.
	 */
	@NotNull
	public Optional<Double> getArgumentAsDouble(int i) {
		Optional<String> optional = this.getArgument(i);
		if (!optional.isPresent())
			return Optional.empty();

		String arg = optional.get();

		try {
			double parsed = Double.parseDouble(arg);
			return Optional.of(parsed);
		} catch (NumberFormatException ignored) {
			return Optional.empty();
		}
	}

	/**
	 * @param i index
	 * @return Float if indexed element is primitive type of float
	 * or 0 if element is null.
	 */
	@NotNull
	public Optional<Float> getArgumentAsFloat(int i) {
		Optional<String> optional = this.getArgument(i);
		if (!optional.isPresent())
			return Optional.empty();

		String arg = optional.get();

		try {
			float parsed = Float.parseFloat(arg);
			return Optional.of(parsed);
		} catch (NumberFormatException ignored) {
			return Optional.empty();
		}
	}

	/**
	 * @param i index
	 * @return Long if indexed element is primitive type of long
	 * or 0 if element is null.
	 */
	@NotNull
	public Optional<Long> getArgumentAsLong(int i) {
		Optional<String> optional = this.getArgument(i);
		if (!optional.isPresent())
			return Optional.empty();

		String arg = optional.get();

		try {
			long parsed = Long.parseLong(arg);
			return Optional.of(parsed);
		} catch (NumberFormatException ignored) {
			return Optional.empty();
		}
	}

	/**
	 * @param i index
	 * @return Boolean if indexed element is primitive type of boolean
	 * or 0 if element is null.
	 */
	@NotNull
	public Boolean getArgumentAsBoolean(int i) {
		final Optional<String> arg = this.getArgument(i);
		return arg.isPresent() && arg.get().equals("true");
	}

	// ---------------------------------------------- //

	/**
	 * @return true if command arguments are empty otherwise false
	 */
	public boolean isArgumentsEmpty() {
		return arguments.length == 0;
	}

	/**
	 * Sends message to sender without receiving command
	 * sender.
	 *
	 * @param message to send
	 */
	public void sendMessage(String message) {
		if (message == null) return;
		commandSender.sendMessage(message);
	}

	/**
	 * Checks if command sender is console.
	 *
	 * @return true if sender is console otherwise false
	 */
	public boolean isSenderConsole() {
		return !isSenderPlayer();
	}

	/**
	 * Checks if command sender is player.
	 *
	 * @return true if sender is player otherwise false
	 */
	public boolean isSenderPlayer() {
		return commandSender instanceof Player;
	}

	/**
	 * Checks if command sender has specified permission.
	 *
	 * @param permission to check
	 * @return true if sender has permission otherwise false
	 */
	public boolean hasPermission(String permission) {
		return commandSender.hasPermission(permission);
	}

	/**
	 * @return length of the arguments
	 */
	public int getArgumentsLength() {
		return arguments.length;
	}
}