import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * class for deadlines, a type of task, with an added deadline field
 *
 */
public class Deadline extends Task {

	private String deadline;
	private LocalDate deadlineDate = null;
	private LocalTime deadlineTime = null;

	Deadline(String title, String deadline) {
		super(title);
		this.parseDate(deadline);
	}

	private String formatTime(String time) {
		if (time.length() > 4) {
			return time;
		}
		String newTime = time.substring(0, 2) + ":" + time.substring(2, 4);
		return newTime;
	}

	private void parseDate(String deadline) {
		try {
			String[] deadlineArray = deadline.trim().split(" ");
			LocalDate deadlineDate = LocalDate.parse(deadlineArray[0].trim());
			this.deadlineDate = deadlineDate;
			LocalTime deadlineTime = LocalTime.parse(formatTime(deadlineArray[1].trim()));
			this.deadlineTime = deadlineTime;
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		} catch (DateTimeParseException e) {
			this.deadline = deadline;
		}
	}

	private String formatMonth(int month) {
		switch (month) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		default:
			return "";
		}
	}

	private String dateTimeString() {
		String dateString = this.formatMonth(this.deadlineDate.getMonthValue()) + " " +
			this.deadlineDate.getDayOfMonth() + " " + this.deadlineDate.getYear() + " ";
		String timeString = this.deadlineTime == null ? "" : " " + this.deadlineTime.toString();
		return dateString + timeString;
	}

	@Override
	public String toString() {
		if (this.done) {
			return this.deadlineDate != null
				? "[D][X] " + this.title + "(by: " + this.dateTimeString() + ")"
				: "[D][X] " + this.title + "(by: " + this.deadline + ")";
		} else {
			return this.deadlineDate != null
				? "[D][ ] " + this.title + "(by: " + this.dateTimeString() + ")"
				: "[D][ ] " + this.title + "(by: " + this.deadline + ")";
		}
	}
}
