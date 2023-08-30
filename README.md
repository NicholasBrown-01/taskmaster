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

### Lab32 AWS Cloud Data

This lab was a database overhaul by removing SharedPrefs and creating & connecting AWS services.
The app now dynamically updates the AWS database and is verified by Dynamo Tables on the dashboard.
All previous app functionality still works to include RecyclerView and Adding Tasks along with
updating username.

### Lab33 AWS Many to One

This lab was to create a many to one relationship within the AWS model. This came in the form of 
adding 3 Teams and then being able to add tasks within those teams. Finally, users should be able to 
set their teams in their setting page.

### Lab34 Android Publishing

This lab was about taking our Taskmaster App and publishing it to the Google Play Store. Screenshot 
is provided in assignment submission. All steps were correct with lecture.

### Lab36 Amplify & Cognito

This lab involved adding in authentication and consistency to our Taskmaster app. We created Login, Signup, 
and Logout features that can be tracked via TAGS and users seen added to our AWS database via Cognito.

### Lab37 S3 Uploads

This lab was to get S3 working alongside our App for the purpose of a user being able to select an
image for a task, and that image display along with the task after being selected from the recyclerview.
