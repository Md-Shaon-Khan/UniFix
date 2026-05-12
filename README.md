# UniFix — Campus Issue Management System

UniFix is a Java-based desktop application designed to streamline the process of reporting, tracking, and resolving campus-related problems. Students can raise issues directly to the relevant authority — whether it concerns the residential hall, academic department, campus security, or other administrative areas — and receive official responses through the system. The platform promotes accountability and transparency by allowing other students to view reported issues and express support, while all data is stored securely in a database.

---

## Overview

On most university campuses, students have no structured channel to formally report infrastructure or administrative problems. Complaints either go unheard or pass through informal routes with no accountability or follow-up mechanism. UniFix addresses this by providing a centralized platform where every issue is logged, assigned to the appropriate authority, and tracked until resolution. If a student is dissatisfied with the response received, they can follow up within the same system rather than starting over.

This project was developed as a group effort (Group 11) and includes a presentation deck outlining the system design, feature set, and demonstration.

---

## Features

**For Students**

- Register and log in to a personal account.
- Submit issues categorized by type — hall, department, security, or general campus concerns.
- Receive official responses from the relevant authority directly within the system.
- Follow up on a reported issue if the initial response is unsatisfactory.
- View issues reported by other students and indicate support for shared concerns.

**For Authorities**

- Access a dedicated dashboard listing all issues submitted to their department or jurisdiction.
- Respond to issues officially with status updates.
- Track the resolution status of each reported problem.

**System-Wide**

- All issues, responses, and user data are stored in a MySQL database via PHPMyAdmin.
- Issue transparency allows students to see what problems have been raised and whether they are being addressed.
- Unique features are built in to maintain data integrity and prevent misuse.

---

## Screenshots

The repository includes several screenshots demonstrating the application interface. These can be found in the root directory under files named `Screenshot 2025-08-04 ...`.

| View | File |
|---|---|
| Login / Registration | Screenshot 2025-08-04 131258.png |
| Student Dashboard | Screenshot 2025-08-04 131310.png |
| Issue Submission Form | Screenshot 2025-08-04 131345.png |
| Issue List View | Screenshot 2025-08-04 131410.png |
| Authority Response Panel | Screenshot 2025-08-04 131441.png |
| Follow-up Interface | Screenshot 2025-08-04 131505.png |
| Support / View Other Issues | Screenshot 2025-08-04 131524.png |

Additional screenshots are included in the repository for a more complete walkthrough.

---

## Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| IDE | NetBeans / IntelliJ IDEA |
| Database | MySQL (via PHPMyAdmin) |
| Database Connectivity | JDBC |
| External Libraries | Included in `lib/lib/` directory |
| Presentation | Microsoft PowerPoint (`Group-11.pptx`) |

---

## Project Structure

```
UniFix/
├── UniFix/              # Main application source and UI forms
├── IssueTracker/        # Issue tracking module and related logic
├── lib/
│   └── lib/             # External JAR dependencies (e.g., MySQL Connector/J)
├── Group-11.pptx        # Project presentation (design, features, demo)
└── Screenshot *.png     # Application screenshots
```

---

## Prerequisites

- Java Development Kit (JDK) 8 or above
- NetBeans IDE or any Java-compatible IDE
- XAMPP or any local environment with MySQL and PHPMyAdmin running
- MySQL JDBC Connector (already included in `lib/lib/` — verify before running)

---

## Getting Started

**1. Clone the repository**

```bash
git clone https://github.com/Md-Shaon-Khan/UniFix.git
cd UniFix
```

**2. Set up the database**

- Start XAMPP and make sure both Apache and MySQL services are active.
- Open PHPMyAdmin at `http://localhost/phpmyadmin`.
- Create a new database (e.g., `unifix_db`).
- Import the SQL schema file if one is provided, or manually create the required tables based on the application logic (users, issues, responses, support).

**3. Configure the database connection**

Locate the database connection file in the source code and update it with your local credentials:

```java
String url = "jdbc:mysql://localhost:3306/unifix_db";
String username = "root";
String password = "";  // Set your MySQL password if applicable
```

**4. Verify the JDBC library**

Confirm that the MySQL Connector/J `.jar` is present inside `lib/lib/`. If missing, download it from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/) and add it to the project's classpath.

**5. Open and run the project**

- Open the project in NetBeans.
- Build the project (`Shift + F11`).
- Run the application (`F6`).

---

## User Roles

**Student**
- Can register, log in, submit issues, follow up, and view/support other students' issues.

**Authority**
- Can log in to a dedicated panel, view issues directed at their department, and post official responses.

---

## Database Schema (Reference)

The system requires at minimum the following tables. Column definitions may vary based on the actual implementation:

**users** — `user_id`, `name`, `email`, `password`, `role` (student / authority), `department`

**issues** — `issue_id`, `student_id`, `category`, `description`, `status`, `submitted_at`

**responses** — `response_id`, `issue_id`, `authority_id`, `message`, `responded_at`

**followups** — `followup_id`, `issue_id`, `student_id`, `message`, `submitted_at`

**support** — `support_id`, `issue_id`, `student_id`

---

## Known Limitations

- The application is desktop-only and requires local installation on each machine.
- There is currently no email or in-app notification system to alert users of new responses.
- Issue categories are predefined and cannot be customized through the UI.
- The system does not yet support file or image attachments within issue submissions.

---

## Future Improvements

- Develop a web-based version for access from any device without installation.
- Add real-time notifications when an issue receives a response or status update.
- Implement an admin panel for system-wide monitoring and user management.
- Allow students to attach supporting images or documents when submitting an issue.
- Introduce an issue priority system so critical problems can be escalated.

---

## Project Presentation

The file `Group-11.pptx` in the root of this repository contains the full project presentation, covering the problem statement, system design, feature walkthrough, and demonstration used during the project submission.

---

## Authors

This project was developed by Group 11.

**Md. Shaon Khan**
GitHub: [Md-Shaon-Khan](https://github.com/Md-Shaon-Khan)

---

## License

This project is open for educational reference and personal use. If you adapt or build upon any part of this work, credit to the original authors is appreciated.
