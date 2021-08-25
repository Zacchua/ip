package duke.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * class to represent events, a type of task with a eventDate field
 */
public class Event extends Task {

	private String eventString;
	private LocalDate eventDate = null;
	private LocalTime eventTime = null;


	public Event(String title, String eventString) {
		super(title);
		this.parseDate(eventString);
	}

	public Event(String title, Boolean done, String eventDate) {
		super(title, done);
		this.parseDate(eventDate);
	}

	private String formatTime(String time) {
		if (time.length() > 4) {
			return time;
		}
		String newTime = time.substring(0, 2) + ":" + time.substring(2, 4);
		return newTime;
	}

	private void parseDate(String event) {
		try {
			String[] eventArray = event.trim().split(" ");
			LocalDate eventDate = LocalDate.parse(eventArray[0].trim());
			this.eventDate = eventDate;
			LocalTime eventTime = LocalTime.parse(formatTime(eventArray[1].trim()));
			this.eventTime = eventTime;
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		} catch (DateTimeParseException e) {
			this.eventString = event;
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
		String dateString = this.formatMonth(this.eventDate.getMonthValue()) + " " +
			this.eventDate.getDayOfMonth() + " " + this.eventDate.getYear() + " ";
		String timeString = this.eventTime == null ? "" : " " + this.eventTime.toString();
		return dateString + timeString;
	}

	/**
	 * Returns a string representation of the Event for displaying
	 *
	 * @return string representation of the Event for displaying
	 */
	@Override
	public String toString() {
		if (this.done) {
			return this.eventDate != null
				? "[E][X] " + this.title + "(at: " + this.dateTimeString() + ")"
				: "[E][X] " + this.title + "(at: " + this.eventString + ")";
		} else {
			return this.eventDate != null
				? "[E][ ] " + this.title + "(at: " + this.dateTimeString() + ")"
				: "[E][ ] " + this.title + "(at: " + this.eventString + ")";
		}
	}

	/**
	 * Returns a string representation of the Event for saving
	 *
	 * @return string representation of the Event for saving
	 */
	@Override
	public String saveString() {
		if (this.done) {
			return this.eventDate != null
				? "E : 1 : " + this.title + " : " + this.eventDate.toString() + (this.eventTime == null ? "" : " " + this.eventTime.toString())
				: "E : 1 : " + this.title + " : " + this.eventString;
		} else {
			return this.eventDate != null
				? "E : 0 : " + this.title + " : " + this.eventDate.toString() + (this.eventTime == null ? "" : " " + this.eventTime.toString())
				: "E : 0 : " + this.title + " : " + this.eventString;
		}
	}
}