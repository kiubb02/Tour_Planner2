# Tour_Planner
Form a team of two students to develop an application based on the GUI frameworks C# / WPF or
Java / JavaFX. The user creates (bike-, hike-, running- or vacation-) tours in advance and manages
the logs and statistical data of accomplished tours.

## Mandatory Technology
* C# / Java as desktop application
* GUI-framework WPF (for C#) or JavaFX (for Java) or another Markup-Language-based UI
Framework
* SQL (no OR-mapper!)
* HTTP for communication
* JSON.NET / Jackson for JSON serialization & deserialization
* Database Engine PostgreSQL with ADO.Net (for C#) or JDBC (for Java)
* Logging with log4j (Java) or log4net (C#) or another .NET Microsoft.Extensions-Solution.
* A report-generation library of your choice
* NUnit / JUnit

## Features
* the user can create new tours (no user management, login, registration... everybody sees all
tours)
* every tour consists of name, tour description, from, to, transport type, tour distance,
estimated time, route information (an image with the tour map)
- the image, the distance, and the time should be retrieved by a REST request using the
MapQuest Directions and Static Map APIs
(https://developer.mapquest.com/documentation/directions-api/,
https://developer.mapquest.com/documentation/open/static-map-api/v5/)
* tours are managed in a list, and can be created, modified, deleted (CRUD)
* for every tour the user can create new tour logs of the accomplished tour statistics
- multiple tour logs are assigned to one tour
- a tour-log consists of date/time, comment, difficulty, total time, and rating taken on
the tour
* tour logs are managed in a list, and can be created, modified, deleted (CRUD)
* validated user-input
* full-text search in tour- and tour-log data
* automatically computed tour attributes
- popularity (derived from number of logs)
- child-friendliness (derived from recorded difficulty values, total times and distance)
* import and export of tour data (file format of your choice)
* the user can generate two types of reports
- a tour-report which contains all information of a single tour and all its associated tour
logs
- a summarize-report for statistical analysis, which for each tour provides the the
average time, -distance and rating over all associated tour-logs
* add a unique feature
