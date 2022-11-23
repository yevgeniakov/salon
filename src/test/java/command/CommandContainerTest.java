package command;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Test;

import controller.command.Command;
import controller.command.CommandContainer;

public class CommandContainerTest {
	@Test
	public void testValidCommand() {
		Command command = CommandContainer.getCommand("show_appointment_info");	
		assertThat(command, instanceOf(Command.class));
		command = CommandContainer.getCommand("show_master_schedule");	
		assertThat(command, instanceOf(Command.class));
		command = CommandContainer.getCommand("update_user");	
		assertThat(command, instanceOf(Command.class));
		command = CommandContainer.getCommand("show_user_list");	
		assertThat(command, instanceOf(Command.class));
		command = CommandContainer.getCommand("login");	
		assertThat(command, instanceOf(Command.class));
	}
	@Test
	public void testInValidCommand() {
		Command command = CommandContainer.getCommand("show_appointment");	
		assertNull(command);
		command = CommandContainer.getCommand("show_appoin");	
		assertNull(command);
		command = CommandContainer.getCommand("loginn");	
		assertNull(command);
		command = CommandContainer.getCommand("");	
		assertNull(command);
	}
}
