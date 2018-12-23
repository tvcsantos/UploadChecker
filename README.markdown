About
=====
`Upload Checker` is a program written in java with a java swing interface
which automatically checks an encode's MediaInfo output for DXVA and Awesome 
compliance.

Compliance is indicated by outputing on the java swing the results
`Not Good Enough`, `Good`, `Discouraged`

Version
=====
1.6 to 1.7

* fixed ref frames problem (not working properly)
* fixed Windows 7 open dialog icons missing
* accept only mkv files
* updated to MediaInfo v0.7.21

1.5 to 1.6

* fixed linux problem, covers not working.

1.4a to 1.5

* added option for checking original audio via themoviedb and imdb
* added search movie function
* added filter option at settings for movie names
* minor bug fixes

1.4 to 1.4a

* added check for frame rate
* assume valid vbv when not found

1.3 to 1.4

* create torrent files

1.2 to 1.3

* option to see mediainfo output

1.1 to 1.2

* minor bug fixes

1.0 to 1.1

* minor bug fixes

Usage
=====
Just double click on the jar file to open the program and have fun :P

Developed By
============
* Tiago Santos - <tvcsantos@gmail.com>

Build
============
Dependencies to my repositories:
* https://github.com/tvcsantos/javautils/tree/v1.1.0
* https://github.com/tvcsantos/javafwutils/tree/v1.0
* https://github.com/tvcsantos/javamediautils/tree/v1.0

Also you must install the extra jar `Azureus4.2.0.4.jar` in your 
local maven repository in order to build this project properly

License
=======
    Upload Chekcer - Simple Java Swing application to check encodes
    for DXVA and Awesome compliancy based on their MediaInfo output.
    Copyright (C) 2009  Tiago Santos

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
