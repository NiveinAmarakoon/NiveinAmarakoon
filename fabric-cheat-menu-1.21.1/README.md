# Fabric Cheat Menu Mod (Minecraft 1.21.1)

This folder contains a Fabric mod skeleton for Minecraft 1.21.1 that adds a cheat menu to the inventory screen. The mod adds a button on the inventory screen that opens a searchable item cheat menu. Players can search for item names and press a "Give" button to insert the selected item into their inventory (client-side). This is intended for creative/singleplayer usage.

## Features
- Inventory screen button labeled "Cheat Menu".
- Search box to filter items by name.
- Clickable buttons to give items to the player.

## Notes
- This is a client-only mod (`ClientModInitializer`).
- The give action inserts items directly into the player's inventory on the client; server validation may reject items on multiplayer servers.

## Build/Run (example)
1. Install Java 21.
2. Run `./gradlew runClient` inside this folder.
