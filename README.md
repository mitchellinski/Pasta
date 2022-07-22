# Pasta e Basta

----

## Introduction

Our group has chosen the **Reservation System** problem statement. 
We have split our implementation in different packages under `src.main.java.com.pastaebasta.eist`:

- `client` - Client side of the implementation: can be executed by running `main()` in `StartClientApplication.java`
- `server` - The server side of the implementation: can be started by running the `main()` 
method in `ReservationApplication.java`
- `shared` - Shared services, which would be present in both `client` and `server`.
- `testing` - Mostly debugging tools, data seeders or generic test helpers, which would not be present
in a real-world scenario, but due to the nature of the problem statement, had to be included.

## Problem Solution
To solve the problem statement, we have decided to use:
- [Spring Boot](https://spring.io/projects/spring-boot) as requested by the problem statement
- [JavaFX](https://openjfx.io/) as our user interface framework
- [MongoDB](mongodb.com/) as our database
- [Project Lombok](https://projectlombok.org/) for less boilerplate and cleaner code
- [Google Calendar API](https://developers.google.com/calendar/api) for creating events
- [Google Maps API](https://developers.google.com/maps/) for retrieving real world data

### Building the project
We require you to use `JDK 17` or newer! If you're using IntelliJ, we personally
recommend you create a single `configuration` using a `Compound`, which starts the server and client simultaneously.

**WARNING:** Sometimes the server may take a while to start, meaning the client may throw connection errors.
To avoid these, you just have to wait a little longer. This should not affect the project, otherwise.

### Important: Google Calendar API
If you wish to use the program, you will need to use a Google account to use the event scheduling feature.
Once you try to make a reservation, a browser window should open, asking you to give us permission
to create events on your calendar. **Please make sure to allow these**. 

- If you receive a warning saying the application has not yet been verified, simply click `Advanced` and
trust the authors.

### Debugging
For debugging or testing the application, you may log-in with: 
- Username: `user`
- Password `user`

You may alternatively create your own account. The passwords are stored in our database with [Argon2](https://github.com/P-H-C/phc-winner-argon2), 
but since this is not part of the _Requirements_, this part of the application has **NOT** been
extensively tested or maintained. 

- ⚠ As always, you should follow proper password creation etiquette, and, 
you should be extra mindful of the passwords you choose:
**do not reuse passwords you use for other applications**!

## Problem Statement Reservation System
1. The Problem

   Restaurant visitors want to be able to reserve their table in a restaurant in advance so that
   they have a fixed place when they arrive.


2. Scenarios

<details>
<summary>Click to expand</summary>

   Hans lives in Munich and knows that all the restaurants are really crowded over the weekend
   and getting a reservation spontaneously is almost impossible. He wants to visit an Italian
   restaurant on Saturday evening together with three friends and opens the Reservation
   system. He searches for Italian restaurants and sees different choices on the map. He also
   sees ratings as well as price categories. He chooses the Nero Pizza & Grill in Rumfordstraße
   34 to see more details. He likes the pictures and reviews of the restaurant and clicks on the
   reservation button to see whether the restaurant has free tables on Saturday evening. The
   system shows a timetable for the upcoming seven days where unavailable times are greyed
   out. Hans chooses Saturday 17:00 and sees a layout of all the tables in the restaurant. Not
   yet reserved tables are selectable. Reserved tables are greyed out. Hans chooses the table
   next to the window for four people and confirms his choice. The system automatically
   creates a calendar event in Hans’s calendar. On Friday, Hans receives a reminder about
   the reservation and sees that he must confirm the table again, so the reservation does not
   get canceled. On Saturday, Hans receives a notification at 16:00. He opens the map to
   navigate to the restaurant.
</details>

3. Requirements
<details>
<summary>Click to expand</summary>

   The following functional requirements (FR) and nonfunctional requirements (NFR) must be
   addressed in the project.
   - **FR1: Search for restaurants:** The user can search for restaurants on a list and on a map
   that displays up to 50 restaurants.
   - **FR2: See restaurants details:** The user can see pictures, ratings and comments of the
   restaurant as well as opening times and a link to the website.
   - **FR3: Filter search results:** He can filter the results by the restaurant type, the prize
   category, by distance around a certain location, by the average rating and by free time slots
   for reservations for specified dates and number of visitors.
   - **FR4: Reserve table:** A user can see the times when he can reserve a table in the chosen
   restaurant. After clicking on the time, the user sees an overview of all tables in the restaurant.
   He can choose the exact table the free one in the overview and thus reserve the table for
   the specified number of visitors.
   - **FR5: Save calendar event:** When the user reserves a table, an event in the local calendar
   is created for the reservation.
   - **FR6: Confirm reservation:** A user is reminded about a reservation one day before the
   actual date of the reservation and must confirm it until latest 12 hours before the actual date.
   If the user does not confirm, his reservation is cancelled automatically.
   - **FR7: Cancel reservation:** A user can cancel his reservation at any time up to two twelve
   hours before the actual date of the reservation. After the confirmation (see FR5), the user
   cannot cancel the reservation anymore.
   - **NFR1: Usability:** The system should be intuitive to use, and the user interface should be
   easy to understand. Simple interactions should be completed in less than three clicks.
   Complex interactions should be completed in less than six clicks.
   - **NFR2: Conformance to guidelines:** The design of the system should conform to the typical
   usability guidelines such as Nielsen’s usability heuristics.
   - **NFR3: Server system:** A server subsystem with a couple of services must be used in the
   system.
   Additional constraints:
     - The version control system must be git.
     - Source Code Documentation must be in HTML format.
     - The server system must use the Spring Boot framework.
</details>

4. Target Environment

   The system should run on all desktop operation systems (Windows, macOS, Unix), either
   as Java or as browser-based application which communicates with the Spring Boot server
   application.


5. Deliverables

   - Requirements Analysis Document (RAD)
   - System Design Document (SDD)
   - Source code under version control including source code documentation


6. Client Acceptance Criteria

   The system must demonstrate at least the following functionality: It shows a list of
   restaurants where a visitor can reserve tables. The user can choose a restaurant and
   reserve a table on a specified timeslot for the specified number of persons. The application
   communicates with the server system and conforms to the usability requirements.