# Description
ColorBukkit is a colorpicker, primarily meant to be used as an API for paper plugins. 
It can also be used as a colorpicker by itself

# Instructions
## How to use
Have the plugin in the plugins repository, then you're up and ready.

<b>PAPER IS REQUIRED FOR THIS TO WORK </b>
### The colorbuckit command
Write `colorbuckit` and you will get a colorpicker pop up
## The API
Get a chatcolor and print it to console. The user will get a book gui popup letting you pick a color
```java
ColorBukkitAPI.getColorFromUser(user, (color) - > {
        Bukkit.getLogger().log(Level.INFO, color.getName());
})
```
Alternativly chose an initial hue for the user to start with. The hue values are inbetween 0 and 1,
if you're using the 360 hue scale, then you need to do `(float)hue/360`.
```java
ColorBukkitAPI.getColorFromUser(user, 0.5f, (color) - > {
        Bukkit.getLogger().log(Level.INFO, color.getName());
```
That's everything; it does not get simplier than that.
