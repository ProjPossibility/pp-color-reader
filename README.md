# pp-color-reader
Overview

Team ColorRead is developing a Java application that will "read" colors for the color blind. We envision a color blind person taking a picture of an item in question with their cell phone, and have the app "read" the color. The color-blind person could then differentiate between various colors.

On the interface showing the picture in question, the user would mouseclick a portion of the picture. The application will read the colors of the clicked pixel and the pixels around it, and calculate the average color in RGB values. The application then converts the RGB color into HSB values and uses a database to figure out the name of the color described by that set of HSB values. The application will then output the name of the color on the GUI, both by text and text-to-speech.

We've also developed a simple and very flexible database system, which is documented later on this page.

Future Plans

Implement on J2ME, also possibly create a desktop assistance version.

Screen Shots

Not yet available.
