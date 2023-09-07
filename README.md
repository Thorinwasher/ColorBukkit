# Description
A library for plugins to request hex colors from users using an intuitive, book-based, in-game selection interface. This is solely meant to be used as an library for other plugins.

# Instructions
## How to use
Have the plugin in the plugins repository for your server, then you're up and ready.

<b>PAPER IS REQUIRED FOR THIS TO WORK </b>
## The API
Maven (note that before compilation, you have to install the plugin locally):
```xml
<dependency>
  <groupId>dev.thorinwasher.utils</groupId>
  <artifactId>colorbukkit</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
Get a chatcolor and print it to console. The user will get a book gui popup letting you pick a color
```java
ColorBukkitAPI.getColorFromUser(user, (color) - > {
        Bukkit.getLogger().log(Level.INFO, color.getName());
});
```
Alternativly chose an initial hue for the user to start with. The hue values are inbetween 0 and 1,
if you're using the 360 hue scale, then you need to do `(float)hue/360`.
```java
ColorBukkitAPI.getColorFromUser(user, 0.5f, (color) - > {
        Bukkit.getLogger().log(Level.INFO, color.getName());
});
```
That's everything; it does not get simplier than that.
