package controller.command;

import java.util.HashMap;
import java.util.Map;

import controller.command.impl.*;


public class CommandContainer {
private static final Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();

		commands.put("login", new LoginCommand());
		commands.put("logout", new LogOutCommand());
		commands.put("create_user", new CreateUserCommand());
		commands.put("show_user_info", new ShowUserInfoCommand());
		commands.put("set_user_block", new SetUserBlockCommand());
		commands.put("show_user_list", new ShowUserListCommand());
		commands.put("show_master_list", new ShowMasterListCommand());
		commands.put("show_service_list", new ShowServiceListCommand());
		commands.put("create_service", new CreateServiceCommand());	
		commands.put("show_masters_of_service", new ShowMasterOfServiceCommand());
		commands.put("delete_service_from_master", new DeleteServiceFromMasterCommand());
		commands.put("update_user", new UpdateUserCommand());
		commands.put("show_master_schedule", new ShowMasterScheduleCommand());
		commands.put("create_appointment", new CreateAppointmentCommand());
		commands.put("show_appointment_info", new ShowAppointmentInfoCommand());
		commands.put("set_complete_appointment", new SetCompleteAppointmentCommand());
		commands.put("set_pay_appointment", new SetPayAppointmentCommand());
		commands.put("set_time_appointment", new SetTimeAppointmentCommand());
		commands.put("delete_appointment", new DeleteAppointmentCommand());
		commands.put("leave_feedback", new LeaveFeedbackCommand());
		commands.put("show_appointments_list", new ShowAppointmentsListCommand());
		commands.put("change_locale", new ChangeLocaleCommand());
	}

	private CommandContainer() {
	}

	public static Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
