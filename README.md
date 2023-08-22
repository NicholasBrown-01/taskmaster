# taskmaster

## Code Fellows Lab Android Basics

### Lab26 Beginning TaskMaster

This lab contains 3 Activities or Pages. The first being a homepage with two buttons on 
the bottom that allow the user to navigate to the Add a Task page, where they will be able to 
type in custom inputs...or to the All Tasks page, where they will be able to see those added tasks in 
the future.

### Lab27 Adding Data to TaskMaster

This lab changed the Homepage to be of 3 Task buttons that when pressed will lead the user
to the Task Details page with the Title of the selected task being at the top of the page
with a Lorem Ipsum description. The lab also adds a settings page in which the User can 
input a User Name and it will be saved (Visually depicted by a toast notification) and then 
the username updated and displayed back on the Homepage.

### Lab28 RecyclerView to TaskMaster

This lab was a refactor of the Homepage that removes our Task Details buttons and is instead
replaced with a RecyclerView of all available tasks. Like the last lab, when a user selects a task
it should then be directed and displayed on the following page with the selected task at the top
of the page. Settings page remains operational.


### Lab29 Saving Data with Room to TaskMaster

This lab was a refactor of the Homepage, AddTasks, and TaskDetail pages. A database and ENUM were
added and now the user can add a task, assign a status category, and see it get dynamically added
to the homepage recycleviewer. When viewing a task, it will now display with the proper matching 
description and status as well as having the total tasks displayed at the bottom.

### Lab31 Espresso Testing
This lab was to add in Espresso Testing to ensure a username and task could be updated and viewed
during the current session.
