package duke.task;

import java.util.ArrayList;
import duke.exception.DukeTaskDetailsException;
import duke.exception.DukeIndexInputException;

public class TaskList {

	private ArrayList<Task> tasks;

	public TaskList(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	public Task addTask(String[] taskArray) throws DukeTaskDetailsException {
		Task task;
		if (taskArray.length < 2) {
			throw new DukeTaskDetailsException("Please provide task details");
		}
		if (taskArray[0].equals("todo")) {
			task = new Todo(taskArray[1]);
		} else if (taskArray[0].equals("deadline")) {
			String[] deadlineDetails = taskArray[1].split("/by");
			if (deadlineDetails.length != 2) {
				throw new DukeTaskDetailsException("Please provide both task title and deadline, separated by \"/by\"");
			}
			task = new Deadline(deadlineDetails[0], deadlineDetails[1]);
		} else {
			String[] eventDetails = taskArray[1].split("/at");
			if (eventDetails.length != 2) {
				throw new DukeTaskDetailsException("Please provide both event title and date, separated by \"/at\"");
			}
			task = new Event(eventDetails[0], eventDetails[1]);
		}
		this.tasks.add(task);
		return task;
	}

	public Task indexCommand(String[] taskArray) throws DukeIndexInputException {
		if (taskArray.length < 2) {
			throw new DukeIndexInputException("Please enter index of the task");
		}
		try {
        Task task;
			int index = Integer.parseInt(taskArray[1]);
			if (taskArray[0].equals("done")) {
				  this.tasks.get(index - 1).markAsDone();
          task = this.tasks.get(index - 1); 
			} else {
				task = this.tasks.remove(index - 1);
			}
			return task;
		} catch (NumberFormatException e){
			throw new DukeIndexInputException("Please enter index of the task");
		} catch (IndexOutOfBoundsException e) {
			throw new DukeIndexInputException("Sorry! Unable to find task number " + taskArray[1]);
		}
	}

	public ArrayList<Task> getTasks() {
		return this.tasks;
	}

	public int size() {
		return this.tasks.size();
	}

	public Task get(int index) {
		return this.tasks.get(index);
	}
}