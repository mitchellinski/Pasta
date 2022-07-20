package com.pastaebasta.eist.client.helper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * EventSchedulingService
 * Acts as a wrapper for the Google Calendar API
 *
 * Requests authorization from user, then creates events in their calendar.
 */
public class EventSchedulingService {
	private static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	// If modifying these scopes, delete your previously saved tokens/ folder.
	private static final String APPLICATION_NAME = "Pasta e Basta Reservation Application";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	public static Calendar calendar;

	public EventSchedulingService() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			.setApplicationName(APPLICATION_NAME)
			.build();
	}

	/**
	 * Creates an authorized Credential object.
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = EventSchedulingService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		//returns an authorized Credential object.
		return credential;
	}

	public String parseRFC3399(LocalDateTime localDateTime) {

		return localDateTime.atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(RFC3339_FORMAT));
	}

	public Event createEvent(String name, String location, String description, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd) throws IOException {
		Event event = new Event()
			.setSummary(name)
			.setLocation(location)
			.setDescription(description);

		DateTime startDateTime = new DateTime(parseRFC3399(localDateTimeStart));
		EventDateTime start = new EventDateTime()
			.setTimeZone("Europe/Berlin")
			.setDateTime(startDateTime);

		event.setStart(start);
		DateTime endDateTime = new DateTime(parseRFC3399(localDateTimeEnd));
		EventDateTime end = new EventDateTime()
			.setTimeZone("Europe/Berlin")
			.setDateTime(endDateTime);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
			new EventReminder().setMethod("email").setMinutes(24 * 60),
			new EventReminder().setMethod("popup").setMinutes(10),
		};
		Event.Reminders reminders = new Event.Reminders()
			.setUseDefault(false)
			.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);
		String calendarId = "primary";
		return calendar.events().insert(calendarId, event).execute();
	}

	public void getUpcomingEvents() throws IOException {
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = calendar.events().list("primary")
				.setMaxResults(10)
				.setTimeMin(now)
				.setOrderBy("startTime")
				.setSingleEvents(true)
				.execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), start);
			}
		}
	}
}
