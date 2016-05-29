## About FamilyCart

Shopping list with notifications shared between family members

## Features and details

- single shared list of items "To buy"

  - swipe right to mark as "Bought"
  - swipe left to mark as "Wont buy": these items wont be recommended (recommendations - optional)
  - floating action button to add new item

- shared list of "Bought" items

- shared list of "Wont buy" items

  - swipe right to return to "To buy" list
  - swipe left to delete

- long tap (maybe some special area) and drag to rearrange

- long tap to enable contextual action mode and allow to manipulate multiple items (mark or delete)

- sort by:

  - name
  - date/time (last change)
  - auto (typical buying order) - optional

- details screen allows to:

  - enter name, quantity, extended comment
  - take picture (optional)

- uses Google Identity to identify family members

- add members one by one or choose existing G+ circle (e.g. "Family")

- uses Google Geofences to show notification if user came to typical shopping place but did not opened shopping list after several minutes (no notification if there are no items to buy)

- widget to display number of items left to buy

## Features maybe implemented in future

- "Favorite/Starred/Pinned" items list
- details screen allows to:

  - search for recent and favorite items while entering name

## General list of tasks

- navigation drawer
- signup/in activity
- content provider
- cloud endpoint
- sync adapter
- family activity
- "To buy" list activity
- details/edit/new item activity
- "Wont buy" list activity
- "Bought" list activity
- check RTL support
- check accessibility
- cart items amount widget
- geofence notification
- sign and prepare to upload to Play Market

## To Draw

- [x] navigation drawer
- [ ] bottom navigation
- [ ] signup/in layout
- [ ] family layout
- [x] items layout
- [x] details layout

## TODO

- [x] add item edit abbility
- [ ]
- [x] add items to list top instead of bottom
- [x] set and use custom order
- [x] change custom order while swiping (independent order for all lists)
- [ ] make reorder handles work not only for adjacent items
- [x] show reorder handles only in To Buy list
- [ ]
- [x] create geofances
- [x] add geofence notification
- [ ] change content provider to store geofence and places information
- [ ] add setting to disable notificatuons
- [ ] add PlacesFragment to show and add places to notify
- [ ]
- [ ] tweak item and family layouts, maybe remove CardView, fill screen width on small screens
- [ ] extract dimensions
