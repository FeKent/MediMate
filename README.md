# MediMate
A CRUD app displays a user's medication and alerts them when a prescription needs to be refilled.

# Description
This app allows you to create, read, update, and display data about a medication. This includes the name, dose, pill count, and refill date. Currently has three main screens: Landing, Add/Edit, and Settings. There are two other planned screens: Calendar and Medication.

---
## Landing Screen

Includes:
- A Centre Aligned Top App Bar, with Calendar and Setting icons on either side
- A "textbox" with plant images on either side and text in the middle that reads, "Welcome `Username`!"
  - Username is set in settings saved to a repository using DataStore
- Two separate tables that display medication & dosage, and dates to order refills of each medication
  - Both have vertical scroll to ensure the data can fit on the screen.
 
![image](https://github.com/user-attachments/assets/5bcc0ef7-80f0-4bed-ae59-ecdf5e7ee53c)![image](https://github.com/user-attachments/assets/08e5a930-59f0-4a51-aba5-9965fbd0ef2a)

---
## Add/Edit Screen

Includes:
- `Textfields`, with appropriate labels, `imeAction`s, keyboard actions, and options
- Use of state management to save user-entered data
  - Also uses Dao functions to add and update data in the app's Room Database
- Logic that calculates when the last of the entered medication will be used
  - Using `LocalDate` functions
- Uses a nullable parameter to change the screen from Add to Edit Medication, which has the `Textfields` prefilled with the data to be edited 
 
![image](https://github.com/user-attachments/assets/2293ef58-a544-422b-8bcd-da6baf8523f2)![image](https://github.com/user-attachments/assets/73ecff31-e52b-4dac-938f-b6f579680e4e)

---
## Settings Screen

- Allows users to set and store their username, to be displayed on the Landing Screen
  - Data persists throughout the entire lifecycle of the app
- Allows users to change the theme of the app between light and dark mode
  - Uses the `Switch` composable, with the `thumbContent` set to an icon of a pill
  - Theme changes are handled through the ThemeViewModel, the state of which is collected in the `setContent` function of the `MainActivity`
- Will allow users to toggle notifications on / off

![image](https://github.com/user-attachments/assets/4b069205-ab83-4c9b-8d04-1a72774b8dc8)![image](https://github.com/user-attachments/assets/403abccc-0a2a-4934-a9a7-2bbe7b155c29)
























